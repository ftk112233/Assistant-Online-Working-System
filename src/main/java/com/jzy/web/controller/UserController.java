package com.jzy.web.controller;

import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.SessionConstants;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.util.FileUtils;
import com.jzy.manager.util.ShiroUtils;
import com.jzy.manager.util.UserUtils;
import com.jzy.model.dto.EmailVerifyCode;
import com.jzy.model.entity.User;
import com.jzy.model.vo.EmailVerifyCodeSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UserController
 * @description 用户业务控制器
 * @date 2019/11/13 11:03
 **/
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {
    private final static Logger logger = LogManager.getLogger(UserController.class);

    /**
     * 跳转设置用户基本资料页面
     *
     * @return
     */
    @RequestMapping("/setInfo")
    public String setInfo() {
        return "user/set/setInfo";
    }

    /**
     * 跳转绑定/修改邮箱界面
     * if 已绑定
     * 跳转修改页面
     * else
     * 跳转设置页面
     *
     * @return
     */
    @RequestMapping("/setEmail")
    public String setEmail() {
        User user = userService.getSessionUserInfo();
        if (!StringUtils.isEmpty(user.getUserEmail())) {
            //已绑定
            return "user/set/modifyCurrentEmail";
        }
        return "user/set/addNewEmail";
    }

    /**
     * 跳转绑定/修改手机界面
     *
     * @return
     */
    @RequestMapping("/setPhone")
    public String setPhone() {
        return "user/set/modifyCurrentPhone";
    }

    /**
     * 跳转修改密码页面
     *
     * @return
     */
    @RequestMapping("/setPassword")
    public String setPassword() {
        return "user/set/setPassword";
    }

    /**
     * 上传头像
     *
     * @param file
     * @return
     */
    @RequestMapping("/uploadUserIcon")
    @ResponseBody
    public Map<String, Object> uploadUserIcon(@RequestParam(value = "file", required = false) MultipartFile file) throws InvalidParameterException {
        Map<String, Object> map2 = new HashMap<>(1);
        Map<String, Object> map = new HashMap<>(3);

        String userIcon = userService.uploadUserIcon(file);

        //返回layui规定的文件上传模块JSON格式
        map.put("code", 0);
        map.put("msg", "");
        map2.put("src", userIcon);
        map.put("data", map2);
        return map;
    }


    /**
     * 用户自己设置自己的基本资料，注意这里不是用户管理中的更改
     *
     * @param user 用户更改后的信息
     * @return
     */
    @RequestMapping("/updateOwnInfo")
    @ResponseBody
    public Map<String, Object> updateInfoByCurrentUser(User user) throws InvalidParameterException {
        Map<String, Object> map = new HashMap<>(1);

        if (!UserUtils.isValidUserUpdateOwnInfo(user)) {
            logger.error("updateInfoByUser方法错误入参");
            throw new InvalidParameterException("updateInfoByUser方法错误入参");
        }

        map.put("data", userService.updateOwnInfo(user));

        return map;
    }

    /**
     * 用户登录后自己更改自己的密码，这里不是找回密码
     *
     * @param userOldPassword 老密码
     * @param userNewPassword 新密码
     * @return
     */
    @RequestMapping("/updateOwnPassword")
    @ResponseBody
    public Map<String, Object> updatePasswordByCurrentUser(@RequestParam("oldPassword") String userOldPassword, @RequestParam("newPassword") String userNewPassword) throws InvalidParameterException {
        Map<String, Object> map = new HashMap<>(1);

        if (!UserUtils.isValidUserPassword(userNewPassword)) {
            String msg = "updatePasswordByCurrentUser方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        User userInfoSession = userService.getSessionUserInfo();
        String correctOldPasswordEncrypted = userService.getUserById(userInfoSession.getId()).getUserPassword();
        String inputOldPasswordEncrypted = ShiroUtils.encryptUserPassword(userOldPassword, userInfoSession.getUserSalt());
        if (!inputOldPasswordEncrypted.equals(correctOldPasswordEncrypted)) {
            //原始密码不匹配
            map.put("data", "oldPasswordWrong");
        } else {
            userService.updatePasswordById(userInfoSession.getId(), userInfoSession.getUserSalt(), userNewPassword);
            map.put("data", Constants.SUCCESS);
        }

        return map;
    }

    /**
     * 通过字节流向前端返回显示用户头像
     *
     * @param request
     * @param response
     * @param user     含用户头像信息
     */
    @RequestMapping("/showIcon")
    public void showIcon(HttpServletRequest request, HttpServletResponse response, User user) {
        //生成验证码
        FileInputStream fis = null;
        OutputStream os = null;

        String fileName;
        if (user != null) {
            fileName = user.getUserIcon();
            if (StringUtils.isEmpty(fileName) || !FileUtils.isImage(fileName)) {
                //为空，或不合法的格式，直接给默认头像
                fileName = User.USER_ICON_DEFAULT;
            }
        } else {
            fileName = User.USER_ICON_DEFAULT;
        }

        try {
            fis = new FileInputStream(filePathProperties.getUploadUserIconPath() + fileName);
            os = response.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((count = fis.read(buffer)) != -1) {
                os.write(buffer, 0, count);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fis.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户自己新增自己的安全邮箱（一开始未绑定），注意这里不是用户管理中的更改
     *
     * @param newEmail 用户更改后的邮箱
     * @return
     */
    @RequestMapping("/addNewEmail")
    @ResponseBody
    public Map<String, Object> addNewEmail(@RequestParam(value = "emailVerifyCode", required = false) String emailVerifyCode, @RequestParam("newEmail") String newEmail) throws InvalidParameterException {
        Map<String, Object> map = new HashMap<>(1);

        if (!UserUtils.isValidUserEmail(newEmail)) {
            String msg = "addNewEmail方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }
        User userInfoSession = userService.getSessionUserInfo();
        //前端服务端双重校验确保安全
        if (userInfoSession.getUserEmail() != null && userInfoSession.getUserEmail().equals(newEmail)) {
            String msg = "addNewEmail方法错误入参, 未对原有邮箱进行修改";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        } else {
            if (StringUtils.isEmpty(newEmail)) {
                String msg = "addNewEmail方法错误入参, 新邮箱为空";
                logger.error(msg);
                throw new InvalidParameterException(msg);
            }
        }

        //auth=true，即已经经过了服务端验证
        ShiroUtils.getSession().setAttribute(SessionConstants.USER_EMAIL_SESSION_KEY, new EmailVerifyCodeSession(newEmail, true));
        if (!userService.ifValidEmailVerifyCode(new EmailVerifyCode(newEmail, emailVerifyCode))) {
            map.put("data", "verifyCodeWrong");
        } else {
            if (userService.getUserByEmail(newEmail) != null) {
                map.put("data", "newEmailExist");
            } else {
                userService.updateEmailById(userInfoSession.getId(), newEmail);
                map.put("data", Constants.SUCCESS);
            }
        }

        return map;
    }

    /**
     * 用户自己修改自己的安全邮箱（一开始未绑定），注意这里不是用户管理中的更改
     *
     * @param userOldEmail 用户原始邮箱
     * @param userNewEmail 用户新邮箱
     * @return
     */
    @RequestMapping("/modifyCurrentEmail")
    @ResponseBody
    public Map<String, Object> modifyCurrentEmail(@RequestParam("oldEmail") String userOldEmail, @RequestParam("newEmail") String userNewEmail) throws InvalidParameterException {
        Map<String, Object> map = new HashMap<>(1);

        if (!UserUtils.isValidUserEmail(userNewEmail)) {
            String msg = "modifyCurrentEmail方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }
        User userInfoSession = userService.getSessionUserInfo();
        //前端服务端双重校验确保安全
        if (userInfoSession.getUserEmail() != null && userInfoSession.getUserEmail().equals(userNewEmail)) {
            String msg = "modifyCurrentEmail方法错误入参, 未对原有邮箱进行修改";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        } else {
            if (StringUtils.isEmpty(userNewEmail)) {
                String msg = "modifyCurrentEmail方法错误入参, 新邮箱为空";
                logger.error(msg);
                throw new InvalidParameterException(msg);
            }
        }


        if (userService.getUserByEmail(userNewEmail) != null) {
            map.put("data", "newEmailExist");
        } else {
            userService.updateEmailById(userInfoSession.getId(), userNewEmail);
            map.put("data", Constants.SUCCESS);
        }

        return map;
    }

    /**
     * 用户自己修改自己的安全手机，注意这里不是用户管理中的更改
     *
     * @param userNewPhone 修改后的手机
     * @return
     */
    @RequestMapping("/modifyCurrentPhone")
    @ResponseBody
    public Map<String, Object> modifyCurrentPhone(@RequestParam("newPhone") String userNewPhone) throws InvalidParameterException {
        Map<String, Object> map = new HashMap<>(1);

        if (!UserUtils.isValidUserPhone(userNewPhone)) {
            String msg = "modifyCurrentPhone方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }
        User userInfoSession = userService.getSessionUserInfo();

        //前端服务端双重校验确保安全 TODO 如有发送短信验证码接口需要另行处理
        if (userInfoSession.getUserPhone() == null || userInfoSession.getUserPhone().equals(userNewPhone)) {
            //未修改手机
            logger.info("modifyCurrentPhone方法错误入参, 未对原有手机进行修改");
        } else {
            if (userService.getUserByPhone(userNewPhone) != null) {
                map.put("data", "newPhoneExist");
                return map;
            }
        }

        userService.updatePhoneById(userInfoSession.getId(), userNewPhone);
        map.put("data", Constants.SUCCESS);
        return map;
    }

}
