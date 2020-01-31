package com.jzy.web.controller;

import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.ModelConstants;
import com.jzy.manager.constant.RedisConstants;
import com.jzy.manager.constant.SessionConstants;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.exception.NoMoreQuestionsException;
import com.jzy.manager.exception.QuestionNotExistException;
import com.jzy.manager.util.CodeUtils;
import com.jzy.manager.util.CookieUtils;
import com.jzy.manager.util.MyStringUtils;
import com.jzy.manager.util.ShiroUtils;
import com.jzy.model.RoleEnum;
import com.jzy.model.dto.EmailVerifyCode;
import com.jzy.model.entity.Question;
import com.jzy.model.entity.User;
import com.jzy.model.vo.Announcement;
import com.jzy.model.vo.EmailVerifyCodeSession;
import com.jzy.model.vo.UserLoginInput;
import com.jzy.model.vo.UserLoginResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName AuthenticationController
 * @description 用户登录登出，验证码，错误页面导向等。
 * 这些请求应该在shiro中配置为匿名访问
 * @date 2019/11/14 22:04
 **/
@Controller
public class AuthenticationController extends AbstractController {
    private final static Logger logger = LogManager.getLogger(AuthenticationController.class);

    @RequestMapping("/comingSoon")
    public String comingSoon() {
        return "tips/comingSoon";
    }

    @RequestMapping("/400")
    public String error400() {
        return "tips/HTTP-400";
    }

    @RequestMapping("/404")
    public String error404() {
        return "tips/HTTP-404";
    }

    @RequestMapping("/500")
    public String error500() {
        return "tips/HTTP-500";
    }

    @RequestMapping("/formRepeatSubmit")
    public String formRepeatSubmit() {
        return "tips/formRepeatSubmit";
    }

    @RequestMapping("/noPermissions")
    public String noPermissions() {
        return "tips/noPermissions";
    }

    @RequestMapping("/")
    public String login0() {
        return "login";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "login";
    }

    @RequestMapping("/forget")
    public String forget() {
        return "forget";
    }

    @RequestMapping("/loginByEmailCode")
    public String loginByEmailCode() {
        return "loginByEmailCode";
    }

    /**
     * 跳转用问题登录页面，后台questionService接口相应方法产生随机问题。将问题内容存在当前session，且问题内容通过model返回前台
     *
     * @param model
     * @return
     */
    @RequestMapping("/guestLogin")
    public String guestLogin(Model model) {
        //获得随机问题
        Question question = questionService.getRandomQuestion();
        //把问题设到session
        ShiroUtils.setSessionAttribute(SessionConstants.LOGIN_QUESTION_SESSION_KEY, question.getContent());
        //把问题id的对应的问题内容放到model中
        model.addAttribute(ModelConstants.QUESTION_MODEL_KEY, question.getContent());
        return "loginByQuestion";
    }

    /**
     * 跳转主页。同时完成以下工作：
     * 1. 系统公告的读取
     * 2. 根据当前登录的用户有无未读消息来决定主页的“小铃铛”是否需要设置小红点
     * 3. CSRFToken的设置
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = userService.getSessionUserInfo();
        Announcement announcement = new Announcement();

        Announcement baseAnnouncement = (Announcement) hashOps.get(RedisConstants.ANNOUNCEMENT_SYSTEM_KEY, Constants.BASE_ANNOUNCEMENT.toString());
        if (baseAnnouncement != null) {
            if (baseAnnouncement.isPermanent()) {
                //如果是永久公告，读id=-2的公告即可
                announcement = baseAnnouncement;
            } else {
                if (!hashOps.hasKey(RedisConstants.ANNOUNCEMENT_SYSTEM_KEY, user.getId().toString())) {
                    //已读公告，即缓存无
                    announcement.setRead(true);
                } else {
                    announcement = (Announcement) hashOps.get(RedisConstants.ANNOUNCEMENT_SYSTEM_KEY, user.getId().toString());
                    //阅后即焚，则清除缓存
                    hashOps.delete(RedisConstants.ANNOUNCEMENT_SYSTEM_KEY, user.getId().toString());

                }
            }
        } else {
            //没有任何公告，read置为false，这样前台不会显示公告
            announcement.setRead(true);
        }
        model.addAttribute(ModelConstants.ANNOUNCEMENT_MODEL_KEY, announcement);

        //判断是否有新消息
        model.addAttribute(ModelConstants.UNREAD_USER_MESSAGE_COUNT_MODEL_KEY, userMessageService.countUserMessagesByUserIdAndRead(user.getId(), false));


        if (SecurityUtils.getSubject().isAuthenticated() || SecurityUtils.getSubject().isRemembered()) {
            //登录成功后设置CsrfToken
            CookieUtils.setCSRFTokenCookieAndSession(request, response);
        }
        return "index";
    }

    /**
     * 判断当前用户名尝试登录的主机ip是否与上次的登录ip相同，通过redis缓存中对比判断
     *
     * @param userName 用户名
     * @param request
     * @return
     */
    boolean isUsualIpAddress(String userName, HttpServletRequest request) {
        if (StringUtils.isEmpty(userName)) {
            return false;
        }
        String ipKey = RedisConstants.USER_LOGIN_IP_KEY;
        if (hashOps.hasKey(ipKey, userName)) {
            //如果缓存中有上次登录成功的ip
            String originalIp = (String) hashOps.get(ipKey, userName);
            String currentIp = ShiroUtils.getClientIpAddress(request);
            if (currentIp == null || !currentIp.equals(originalIp)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 测试当前要登录的用户名的ip是否可疑
     *
     * @param userName 要登录的用户名
     * @param request
     * @return
     */
    @RequestMapping("/testIp")
    @ResponseBody
    public Map<String, Object> testIp(@RequestParam(value = "userName", required = false) String userName, HttpServletRequest request) {
        Map<String, Object> map = new HashMap(1);
        if (!isUsualIpAddress(userName, request)) {
            //比较缓存中当前用户名上次登录成功的ip是否相同，判断ip是否可疑
            ShiroUtils.setSessionAttribute(SessionConstants.REQUIRE_SLIDER_AUTH_SESSION_KEY, Constants.YES);
            map.put("data", "suspicious");
            return map;
        }


        map.put("data", SUCCESS);
        return map;
    }

    /**
     * （用户名，密码）登录交互。shiro实现
     *
     * @param input 登录请求的入参封装--用户名&密码&是否记住密码
     * @return 1. 登录成功
     * 2. 用户名不存在
     * 3. 账号存在，但密码错误
     * 4. 账户被锁定
     */
    @RequestMapping("/loginTest")
    @ResponseBody
    public Map<String, Object> loginTest(UserLoginInput input, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>(1);
        UserLoginResult result = new UserLoginResult();

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        //图形验证码判断，改用滑块验证
//        String trueImgCode = (String) session.getAttribute(SessionConstants.KAPTCHA_SESSION_KEY);
//        if (!CodeUtils.equals(input.getImgCode(), trueImgCode)) {
//            result.setImgCodeWrong(true);
//            map.put("data", result);
//            return map;
//        }
        String requiredSliderAuth = (String) session.getAttribute(SessionConstants.REQUIRE_SLIDER_AUTH_SESSION_KEY);
        if (!isUsualIpAddress(input.getUserName(), request) && !Constants.YES.equals(requiredSliderAuth)) {
            //未经过testIp请求的可疑ip异常，可能是机器爆破等攻击方式
            logger.error("绕过滑块验证的可疑登录请求!");
            map.put("data", result);
            return map;
        }

        //redis用户登录错误次数缓存的键
        String key = UserLoginResult.getUserLoginFailKey(input.getUserName());

        UsernamePasswordToken token = new UsernamePasswordToken(input.getUserName(), input.getUserPassword());
        //rememberMe功能交给shrio
        if (Constants.ON.equals(input.getRememberMe())) {
            token.setRememberMe(true);
        }
        try {
            subject.login(token);
            User userSessionInfo = (User) subject.getPrincipal();
            //登录成功，设置用户信息到session
            session.setAttribute(SessionConstants.USER_INFO_SESSION_KEY, userSessionInfo);


            //返回json中设置标志success为true
            result.setSuccess(true);
            result.setUser(userSessionInfo);
            if (redisTemplate.hasKey(key)) {
                //如果有登录失败记录
                //登录成功，让当前登录失败次数的缓存过期
                redisOperation.expireKey(key);
            }

            //登录成功ip缓存
            hashOps.put(RedisConstants.USER_LOGIN_IP_KEY, input.getUserName(), ShiroUtils.getClientIpAddress(request));
            redisTemplate.expire(RedisConstants.USER_LOGIN_IP_KEY, RedisConstants.USER_LOGIN_IP_EXPIRE, TimeUnit.DAYS);

            session.removeAttribute(SessionConstants.REQUIRE_SLIDER_AUTH_SESSION_KEY);
        } catch (UnknownAccountException e) {
            //用户名不存在
            result.setUnknownAccount(true);
            map.put("data", result);
        } catch (IncorrectCredentialsException e) {
            /*
               账号存在，但密码错误
              密码输错次数的redis缓存设置与查询
              success标志默认false，可以不写
             */
            result.setSuccess(false);

            int wrongTimes = 1;
            if (!redisTemplate.hasKey(key)) {
                //如果当前用户名没有登录失败次数的缓存，设为第一次登录失败
                valueOps.set(key, "1");
            } else {
                /*
                  登录失败次数加一，这里没有使用increment方法
                  因为redisTemplate配置中value序列化使用了GenericJackson2JsonRedisSerializer，这会导致该方法报String转换错误
                 */
                wrongTimes = Integer.parseInt((String) valueOps.get(key));
                valueOps.set(key, wrongTimes + 1 + "");
            }
            result.setWrongTimes(wrongTimes);

            //设置当前用户登录错误的缓存有效期15分钟
            redisTemplate.expire(key, UserLoginResult.DEFAULT_BASE_DELAY_TIME, TimeUnit.MINUTES);

            result.setPasswordWrong(true);
            map.put("data", result);
        } catch (LockedAccountException | ExcessiveAttemptsException e) {
            //账户被锁定
            //success标志默认false，可以不写
            result.setSuccess(false);
            result.setLocked(true);
            result.setDefaultWrongTimes();
            //剩余锁定时间getExpire下取整，这里所以取+1
            result.setTimeRemaining(redisTemplate.getExpire(key, TimeUnit.MINUTES) + 1);
            map.put("data", result);
        } catch (AuthenticationException e) {
            //其他异常
            logger.error("未知的登录错误!");
            map.put("data", result);
        }

        map.put("data", result);
        return map;
    }

    /**
     * 发送验证码的ajax交互
     * 将目标邮箱等信息存在session
     *
     * @param userEmail 发送的目标邮箱
     * @return
     */
    @RequestMapping("/sendVerifyCodeToEmail")
    @ResponseBody
    public Map<String, Object> sendVerifyCodeToEmail(@RequestParam("userEmail") String userEmail) {
        Map<String, Object> map = new HashMap(1);
        ShiroUtils.setSessionAttribute(SessionConstants.USER_EMAIL_SESSION_KEY, new EmailVerifyCodeSession(userEmail, false));
        try {
            userService.sendVerifyCodeToEmail(userEmail);
            map.put("msg", SUCCESS);
        } catch (Exception e) {
            logger.error("邮箱验证码发送失败!");
            map.put("msg", FAILURE);
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 检测验证码是否正确的ajax交互
     *
     * @param emailVerifyCode 输入的验证码
     * @param user            用户的邮箱信息，用user对象封装
     * @return 1. 邮箱不存在
     * 2. 验证码错误
     * 3. 验证码正确
     */
    @RequestMapping("/emailVerifyCodeTest")
    @ResponseBody
    public Map<String, Object> emailVerifyCodeTest(@RequestParam(value = "emailVerifyCode", required = false) String emailVerifyCode, User user) {
        Map<String, Object> map = new HashMap(1);
        if (userService.getUserByEmail(user.getUserEmail()) == null) {
            map.put("data", "emailUnregistered");
        } else if (!userService.isValidEmailVerifyCode(new EmailVerifyCode(user.getUserEmail(), emailVerifyCode))) {
            map.put("data", "verifyCodeWrong");
        } else {
            //auth=true，即已经经过了服务端验证
            ShiroUtils.setSessionAttribute(SessionConstants.USER_EMAIL_SESSION_KEY, new EmailVerifyCodeSession(user.getUserEmail(), true));
            map.put("data", "verifyCodeCorrect");
        }
        return map;
    }

    /**
     * 重置密码的ajax交互
     *
     * @param user 入参用户密码信息
     * @return
     */
    @RequestMapping("/resetPassword")
    @ResponseBody
    public Map<String, Object> resetPassword(User user) {
        Map<String, Object> map = new HashMap(1);
        if (!MyStringUtils.isPassword(user.getUserPassword())) {
            String msg = "错误的用户密码入参!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }
        EmailVerifyCodeSession emailVerifyCodeSession = (EmailVerifyCodeSession) ShiroUtils.getSessionAttribute(SessionConstants.USER_EMAIL_SESSION_KEY);
        if (emailVerifyCodeSession == null) {
            String msg = "在session中的邮箱验证码auth对象EmailVerifyCodeSession为空";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }
        userService.updatePasswordByEmail(emailVerifyCodeSession.getUserEmail(), user.getUserPassword());
        map.put("data", SUCCESS);
        return map;
    }


    /**
     * 通过邮箱验证码登录
     *
     * @param emailVerifyCode 输入的验证码
     * @param user            用户的邮箱信息，用user对象封装
     * @return 1. 邮箱未被注册
     * 2. 邮箱验证码错误
     * 3. 登录成功
     */
    @RequestMapping("/loginTestByEmailCode")
    @ResponseBody
    public Map<String, Object> loginTestByEmailCode(@RequestParam(value = "emailVerifyCode", required = false) String emailVerifyCode, User user, HttpServletRequest request) {
        Map<String, Object> map = new HashMap(1);
        User userGetByEmail = userService.getUserByEmail(user.getUserEmail());
        if (userGetByEmail == null) {
            map.put("data", "emailUnregistered");
        } else if (!userService.isValidEmailVerifyCode(new EmailVerifyCode(user.getUserEmail(), emailVerifyCode))) {
            map.put("data", "verifyCodeWrong");
        } else {
            //auth=true，即已经经过了服务端验证
            ShiroUtils.setSessionAttribute(SessionConstants.USER_EMAIL_SESSION_KEY, new EmailVerifyCodeSession(user.getUserEmail(), true));

            //通过验证，用固定的明文密文组实现免密登录
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            session.setAttribute(SessionConstants.LOGIN_WITHOUT_PASSWORD_SESSION_KEY, SUCCESS);
            UsernamePasswordToken token = new UsernamePasswordToken(null, ShiroUtils.FINAL_PASSWORD_PLAINTEXT);
            try {
                subject.login(token);
                //登录成功，设置用户信息到session
                session.setAttribute(SessionConstants.USER_INFO_SESSION_KEY, userGetByEmail);

                map.put("data", "verifyCodeCorrect");
            } catch (AuthenticationException e) {
                //其他异常
                map.put("data", UNKNOWN_ERROR);
            }
            //移除免密登录成功标志
            session.removeAttribute(SessionConstants.LOGIN_WITHOUT_PASSWORD_SESSION_KEY);
        }
        return map;
    }

    /**
     * 通过问题登录页面换一个问题的ajax请求
     *
     * @return
     */
    @RequestMapping("/resetLoginQuestion")
    @ResponseBody
    public Map<String, Object> resetLoginQuestion() {
        Map<String, Object> map = new HashMap(1);
        String originQuestionContent = (String) ShiroUtils.getSessionAttribute(SessionConstants.LOGIN_QUESTION_SESSION_KEY);
        //获得新问题
        try {
            Question newQuestion = questionService.getDifferentRandomQuestion(originQuestionContent);

            if (newQuestion != null) {
                //把问题内容设到session
                ShiroUtils.setSessionAttribute(SessionConstants.LOGIN_QUESTION_SESSION_KEY, newQuestion.getContent());

                map.put("msg", "");
                map.put("question", newQuestion.getContent());
                return map;
            }
        } catch (NoMoreQuestionsException e) {
            e.printStackTrace();
        }

        map.put("msg", "noMoreQuestions");

        return map;
    }

    /**
     * 通过问题登录
     *
     * @param answer 用户输入的问题答案
     * @return 1. 回答错误
     * 2. 登录成功
     */
    @RequestMapping("/loginTestByQuestion")
    @ResponseBody
    public Map<String, Object> loginTestByQuestion(@RequestParam("answer") String answer, HttpServletRequest request) {
        Map<String, Object> map = new HashMap(1);

        String questionContent = (String) ShiroUtils.getSessionAttribute(SessionConstants.LOGIN_QUESTION_SESSION_KEY);

        try {
            if (!questionService.isCorrectAnswer(questionContent, answer)) {
                //问题回答错误
                map.put("data", FAILURE);
                return map;
            } else {
                //通过验证，用固定的明文密文组实现免密登录
                Subject subject = SecurityUtils.getSubject();
                Session session = subject.getSession();
                session.setAttribute(SessionConstants.LOGIN_WITHOUT_PASSWORD_SESSION_KEY, SUCCESS);
                UsernamePasswordToken token = new UsernamePasswordToken(null, ShiroUtils.FINAL_PASSWORD_PLAINTEXT);
                try {
                    subject.login(token);
                    //登录成功，设置用户信息到session。注意这里的用户应该是游客！
                    User guest = new User();
                    setGuestSessionInfo(guest);
                    session.setAttribute(SessionConstants.USER_INFO_SESSION_KEY, guest);
                    map.put("data", SUCCESS);
                } catch (AuthenticationException e) {
                    //其他异常
                    map.put("data", UNKNOWN_ERROR);
                }
                //移除免密登录成功标志
                session.removeAttribute(SessionConstants.LOGIN_WITHOUT_PASSWORD_SESSION_KEY);
                //移除问题
                session.removeAttribute(SessionConstants.LOGIN_QUESTION_SESSION_KEY);
            }
        } catch (QuestionNotExistException e) {
            e.printStackTrace();
            String msg = "isCorrectAnswer方法输入问题内容不存在!";
            logger.error(msg);
            map.put("data", UNKNOWN_ERROR);
        }
        return map;
    }

    /**
     * 设置登录成功后游客的session中user信息
     *
     * @param newGuest 登录成功后的新游客用户对象
     * @return 设置好信息后的游客用户对象
     */
    private User setGuestSessionInfo(User newGuest) {
        if (newGuest == null) {
            newGuest = new User();
        }
        newGuest.setId(Constants.GUEST_ID);
        newGuest.setUserWorkId(UUID.randomUUID().toString());
        newGuest.setUserName(UUID.randomUUID().toString());
        newGuest.setUserRealName(CodeUtils.randomCodes());
        newGuest.setUserRole(RoleEnum.GUEST.getRole());
        newGuest.setDefaultUserIcon();
        return newGuest;
    }

}
