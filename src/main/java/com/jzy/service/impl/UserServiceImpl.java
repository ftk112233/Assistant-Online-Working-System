package com.jzy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.dao.UserMapper;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.RedisConstants;
import com.jzy.manager.constant.SessionConstants;
import com.jzy.manager.util.*;
import com.jzy.model.dto.EmailVerifyCode;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.UserSearchCondition;
import com.jzy.model.entity.User;
import com.jzy.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UserServiceImpl
 * @description 用户业务实现
 * @date 2019/11/13 22:13
 **/
@Service
public class UserServiceImpl extends AbstractServiceImpl implements UserService {
    private final static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(Long id) {
        return id == null ? null : userMapper.getUserById(id);
    }

    @Override
    public List<User> listAllUsers() {
        return userMapper.listAllUsers();
    }

    @Override
    public User getUserByName(String userName) {
        return StringUtils.isEmpty(userName) ? null : userMapper.getUserByName(userName);
    }

    @Override
    public User getUserByWorkId(String userWorkId) {
        return StringUtils.isEmpty(userWorkId) ? null : userMapper.getUserByWorkId(userWorkId);
    }

    @Override
    public User getUserByIdCard(String userIdCard) {
        return StringUtils.isEmpty(userIdCard) ? null : userMapper.getUserByIdCard(userIdCard);
    }

    @Override
    public User getUserByEmail(String userEmail) {
        return StringUtils.isEmpty(userEmail) ? null : userMapper.getUserByEmail(userEmail);
    }

    @Override
    public User getUserByPhone(String userPhone) {
        return StringUtils.isEmpty(userPhone) ? null : userMapper.getUserByPhone(userPhone);
    }

    @Override
    public User getUserByNameOrEmailOrPhoneOrIdCard(String userName) {
        //格式符合邮箱
        if (MyStringUtils.isEmail(userName)) {
            return getUserByEmail(userName);
        }

        //格式符合手机号
        if (MyStringUtils.isPhone(userName)) {
            return getUserByPhone(userName);
        }

        //格式符合身份证
        if (MyStringUtils.IdCardUtil.isValidatedAllIdCard(userName)) {
            return getUserByIdCard(userName);
        }

        return getUserByName(userName);
    }

    @Override
    public User getSessionUserInfo() {
        return (User) ShiroUtils.getSession().getAttribute(SessionConstants.USER_INFO_SESSION_KEY);
    }

    @Override
    public User updateSessionUserInfo() {
        User originUser = getSessionUserInfo();
        if (originUser != null) {
            if (!UserUtils.USER_ROLES.get(5).equals(originUser.getUserRole())) {
                //如果不是用游客号登陆的，根据id更新一下session中信息
                User newUser = getUserById(originUser.getId());
                ShiroUtils.getSession().setAttribute(SessionConstants.USER_INFO_SESSION_KEY, newUser);
                return newUser;
            }
        }
        return null;
    }

    @Override
    public EmailVerifyCode sendVerifyCodeToEmail(String userEmail) throws Exception {
        // 获取前端传入的参数
        String emailAddress = userEmail;
        String verifyCode = CodeUtils.sixRandomCodes();
        String emailMsg = "收到来自新东方优能中学助教工作平台的验证码：\n" + verifyCode + "\n有效时间: " + EmailVerifyCode.getValidTimeMinutes() + "分钟";
        try {
            // 邮件发送处理
            SendEmailUtils.sendEncryptedEmail(emailAddress, emailMsg);
            //设置redis缓存
            ValueOperations<String, Object> vOps = redisTemplate.opsForValue();
            final String baseKey = RedisConstants.USER_VERIFYCODE_EMAIL_KEY;
            String key = baseKey + ":" + userEmail;
            vOps.set(key, verifyCode);
            redisTemplate.expire(key, EmailVerifyCode.getValidTimeMinutes(), TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new EmailVerifyCode(userEmail, verifyCode);
    }

    @Override
    public boolean ifValidEmailVerifyCode(EmailVerifyCode emailVerifyCode) {
        if (emailVerifyCode == null || StringUtils.isEmpty(emailVerifyCode.getCode()) || StringUtils.isEmpty(emailVerifyCode.getEmail())) {
            logger.error("服务端收到了非法请求，请引起警惕");
            return false;
        }
        ValueOperations<String, Object> vOps = redisTemplate.opsForValue();
        final String baseKey = RedisConstants.USER_VERIFYCODE_EMAIL_KEY;
        String key = baseKey + ":" + emailVerifyCode.getEmail();
        String codeInput = emailVerifyCode.getCode();
        String codeFromRedis = (String) vOps.get(key);
        if (!redisTemplate.hasKey(key) || !codeInput.equals(codeFromRedis)) { //如果当前key过期（即邮箱验证码失效），或输入错误
            return false;
        }

        //验证通过即让当前key过期
        redisTemplate.expire(key, 0, TimeUnit.MINUTES);
        return true;
    }

    @Override
    public void updatePasswordByEmail(String userEmail, String userPassword) {
        User user = userMapper.getUserByEmail(userEmail);
        userPassword = ShiroUtils.encryptUserPassword(userPassword, user.getUserSalt());
        userMapper.updatePasswordByEmail(userEmail, userPassword);
    }

    @Override
    public String uploadUserIcon(MultipartFile file) {
        User user = userService.getSessionUserInfo();
        if (file.isEmpty()) {
            String msg = "上传文件为空";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }


        String originalFilename = file.getOriginalFilename();
        String fileName = "user_icon_" + user.getId() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String filePath = filePathProperties.getUploadUserIconPath();
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            logger.error("id:" + user.getId() + "——用户头像上传失败");
        }
        return fileName;
    }

    @Override
    public String updateOwnInfo(User user) {
        User originalUser = getSessionUserInfo();
        if (originalUser == null) {
            logger.error("updateOwnInfo方法中获取不到用户session");
            return "unknownError";
        }

        if (!StringUtils.isEmpty(originalUser.getUserIdCard())) {
            if (!originalUser.getUserIdCard().equals(user.getUserIdCard())) {
                //如果身份证被修改过了
                if (getUserByIdCard(user.getUserIdCard()) != null) {
                    return "userIdCardExist";
                }
            }
        } else {
            user.setUserIdCard(null);
        }

        if (!originalUser.getUserName().equals(user.getUserName())) {
            //如果用户名被修改过了
            if (getUserByName(user.getUserName()) != null) {
                return "userNameExist";
            }
        }

        /**
         * 用户上传的头像的处理
         */
        if (!StringUtils.isEmpty(user.getUserIcon())) {
            //如果用户上传了新头像
            if (!originalUser.getUserIcon().equals(UserUtils.USER_ICON_DEFAULT)) {
                //如果用户原来的头像不是默认头像，需要将原来的头像删除
                FileUtils.deleteFile(filePathProperties.getUploadUserIconPath() + originalUser.getUserIcon());
            }
            //将上传的新头像文件重名为含日期时间的newUserIconName，该新文件名用来保存到数据库
            String newUserIconName = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date()) + "_" + user.getUserIcon();
            FileUtils.renameByName(filePathProperties.getUploadUserIconPath(), user.getUserIcon(), newUserIconName);
            user.setUserIcon(newUserIconName);
        } else {
            //仍设置原头像
            user.setUserIcon(originalUser.getUserIcon());
        }

        //执行更新
        userMapper.updateOwnInfo(user);
        return Constants.SUCCESS;
    }

    @Override
    public void updateEmailById(Long id, String userEmail) {
        userMapper.updateEmailById(id, userEmail);
    }

    @Override
    public void updatePhoneById(Long id, String userPhone) {
        userMapper.updatePhoneById(id, userPhone);
    }

    @Override
    public void updatePasswordById(Long id, String salt, String userPasswordNotEncrypted) {
        String userPasswordEncrypted = ShiroUtils.encryptUserPassword(userPasswordNotEncrypted, salt);
        userMapper.updatePasswordById(id, userPasswordEncrypted);
    }

    @Override
    public PageInfo<User> listUsers(MyPage myPage, UserSearchCondition condition) {
        PageHelper.startPage(myPage.getPageNum(), myPage.getPageSize());
        List<User> users = userMapper.listUsers(condition);
        return new PageInfo<>(users);
    }

    @Override
    public String updateUserInfo(User user) {
        User originalUser = getUserById(user.getId());

        if (!StringUtils.isEmpty(user.getUserWorkId())) {
            //新工号不为空
            if (!user.getUserWorkId().equals(originalUser.getUserWorkId())) {
                //工号修改过了，判断是否与已存在的工号冲突
                if (getUserByWorkId(user.getUserWorkId()) != null) {
                    //修改后的工号已存在
                    return "workIdRepeat";
                }
            }

        } else {
            user.setUserWorkId(null);
        }

        if (!StringUtils.isEmpty(user.getUserIdCard())) {
            //新身份证不为空
            if (!user.getUserIdCard().equals(originalUser.getUserIdCard())) {
                //身份证修改过了，判断是否与已存在的身份证冲突
                if (getUserByIdCard(user.getUserIdCard()) != null) {
                    //修改后的身份证已存在
                    return "idCardRepeat";
                }
            }
        } else {
            user.setUserIdCard(null);
        }

        if (!user.getUserName().equals(originalUser.getUserName())) {
            //用户名修改过了，判断是否与已存在的用户名冲突
            if (getUserByName(user.getUserName()) != null) {
                //修改后的用户名已存在
                return "nameRepeat";
            }
        }

        if (!StringUtils.isEmpty(user.getUserEmail())) {
            //新邮箱不为空
            if (!user.getUserEmail().equals(originalUser.getUserEmail())) {
                //邮箱修改过了，判断是否与已存在的邮箱冲突
                if (getUserByEmail(user.getUserEmail()) != null) {
                    //修改后的邮箱已存在
                    return "emailRepeat";
                }
            }
        } else {
            user.setUserEmail(null);
        }

        if (!StringUtils.isEmpty(user.getUserPhone())) {
            //新手机不为空
            if (!user.getUserPhone().equals(originalUser.getUserPhone())) {
                //电话修改过了，判断是否与已存在的电话冲突
                if (getUserByPhone(user.getUserPhone()) != null) {
                    //修改后的电话已存在
                    return "phoneRepeat";
                }
            }
        } else {
            user.setUserPhone(null);
        }

        userMapper.updateUserInfo(user);
        return Constants.SUCCESS;
    }

    @Override
    public String insertUser(User user) {
        if (!StringUtils.isEmpty(user.getUserWorkId())) {
            //新工号不为空
            if (getUserByWorkId(user.getUserWorkId()) != null) {
                //添加的工号已存在
                return "workIdRepeat";
            }
        } else {
            user.setUserWorkId(null);
        }

        return insertUserWithUnrepeateWorkId(user);
    }


    /**
     * 插入工号不重复的用户信息
     *
     * @param user
     * @return
     */
    private  String insertUserWithUnrepeateWorkId(User user) {
        if (!StringUtils.isEmpty(user.getUserIdCard())) {
            //新身份证不为空
            if (getUserByIdCard(user.getUserIdCard()) != null) {
                //添加的身份证已存在
                return "idCardRepeat";
            }
        } else {
            user.setUserIdCard(null);
        }


        if (getUserByName(user.getUserName()) != null) {
            //添加的用户名已存在
            return "nameRepeat";
        }

        if (!StringUtils.isEmpty(user.getUserEmail())) {
            //新邮箱不为空
            if (getUserByEmail(user.getUserEmail()) != null) {
                //添加的邮箱已存在
                return "emailRepeat";
            }
        } else {
            user.setUserEmail(null);
        }

        if (!StringUtils.isEmpty(user.getUserPhone())) {
            //新手机不为空
            if (getUserByPhone(user.getUserPhone()) != null) {
                //添加的电话已存在
                return "phoneRepeat";
            }
        } else {
            user.setUserPhone(null);
        }

        //执行插入
        userMapper.insertUser(user);
        return Constants.SUCCESS;
    }

    @Override
    public void deleteOneUserById(Long id) {
        userMapper.deleteOneUserById(id);
    }

    @Override
    public void deleteManyUsersByIds(List<Long> ids) {
        userMapper.deleteManyUsersByIds(ids);
    }

    @Override
    public String insertAndUpdateUsersFromExcel(List<User> users) throws Exception {
        for (User user : users) {
            if (UserUtils.isValidUserUpdateInfo(user)) {
                insertAndUpdateOneUserFromExcel(user);
            } else {
                String msg = "表格输入用户user不合法!";
                logger.error(msg);
                throw new InvalidParameterException(msg);
            }
        }
        return Constants.SUCCESS;
    }

    @Override
    public String insertAndUpdateOneUserFromExcel(User user) throws Exception {
        if (user == null) {
            String msg = "insertAndUpdateOneUserFromExcel方法输入用户user为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        User originalUser = getUserByWorkId(user.getUserWorkId());
//        if (originalUser != null) {
//            //工号已存在，更新
//            if (!UserUtils.USER_ROLES.get(3).equals(originalUser.getUserRole())){
//                //如果当前助教用户角色不是助教，设为null，这样在mapper中不会更新
//                user.setUserRole(null);
//            }
//            updateUserByWorkId(originalUser, user);
//        }

        if (originalUser == null) {
            //插入
            insertUserWithUnrepeateWorkId(user);
        }
        return Constants.SUCCESS;
    }

    @Override
    public String updateUserByWorkId(User user) {
        User originalUser = getUserByWorkId(user.getUserWorkId());
        return updateUserByWorkId(originalUser, user);
    }

    @Override
    public String updateUserByWorkId(User originalUser, User newUser) {
        if (!StringUtils.isEmpty(newUser.getUserIdCard())) {
            //新身份证不为空
            if (!newUser.getUserIdCard().equals(originalUser.getUserIdCard())) {
                //身份证修改过了，判断是否与已存在的身份证冲突
                if (getUserByIdCard(newUser.getUserIdCard()) != null) {
                    //修改后的身份证已存在
                    return "idCardRepeat";
                }
            }
        } else {
            newUser.setUserIdCard(null);
        }

        if (!newUser.getUserName().equals(originalUser.getUserName())) {
            //用户名修改过了，判断是否与已存在的用户名冲突
            if (getUserByName(newUser.getUserName()) != null) {
                //修改后的用户名已存在
                return "nameRepeat";
            }
        }

        if (!StringUtils.isEmpty(newUser.getUserEmail())) {
            //新邮箱不为空
            if (!newUser.getUserEmail().equals(originalUser.getUserEmail())) {
                //邮箱修改过了，判断是否与已存在的邮箱冲突
                if (getUserByEmail(newUser.getUserEmail()) != null) {
                    //修改后的邮箱已存在
                    return "emailRepeat";
                }
            }
        } else {
            newUser.setUserEmail(null);
        }

        if (!StringUtils.isEmpty(newUser.getUserPhone())) {
            //新手机不为空
            if (!newUser.getUserPhone().equals(originalUser.getUserPhone())) {
                //电话修改过了，判断是否与已存在的电话冲突
                if (getUserByPhone(newUser.getUserPhone()) != null) {
                    //修改后的电话已存在
                    return "phoneRepeat";
                }
            }
        } else {
            newUser.setUserPhone(null);
        }

        userMapper.updateUserByWorkId(newUser);
        return Constants.SUCCESS;
    }
}
