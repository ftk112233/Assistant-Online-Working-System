package com.jzy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.dao.UserMapper;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.ExcelConstants;
import com.jzy.manager.constant.RedisConstants;
import com.jzy.manager.constant.SessionConstants;
import com.jzy.manager.exception.InvalidEmailException;
import com.jzy.manager.exception.InvalidFileInputException;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.util.*;
import com.jzy.model.RoleEnum;
import com.jzy.model.dto.*;
import com.jzy.model.entity.User;
import com.jzy.model.entity.UserMessage;
import com.jzy.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
    private final static Logger logger = LogManager.getLogger(UserServiceImpl.class);

    /**
     * 表示工号重复
     */
    private final static String WORK_ID_REPEAT = "workIdRepeat";

    /**
     * 表示身份证重复
     */
    private final static String ID_CARD_REPEAT = "idCardRepeat";

    /**
     * 表示用户名重复
     */
    private final static String NAME_REPEAT = "nameRepeat";

    /**
     * 表示邮箱重复
     */
    private final static String EMAIL_REPEAT = "emailRepeat";

    /**
     * 表示电话重复
     */
    private final static String PHONE_REPEAT = "phoneRepeat";

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean isRepeatedUserWorkId(User user) {
        if (user == null) {
            throw new InvalidParameterException("输入对象不能为空");
        }
        return getUserByWorkId(user.getUserWorkId()) != null;
    }

    @Override
    public boolean isRepeatedUserIdCard(User user) {
        if (user == null) {
            throw new InvalidParameterException("输入对象不能为空");
        }
        return getUserByIdCard(user.getUserIdCard()) != null;
    }

    @Override
    public boolean isRepeatedUserName(User user) {
        if (user == null) {
            throw new InvalidParameterException("输入对象不能为空");
        }
        return getUserByName(user.getUserName()) != null;
    }

    @Override
    public boolean isRepeatedUserEmail(User user) {
        if (user == null) {
            throw new InvalidParameterException("输入对象不能为空");
        }
        return getUserByEmail(user.getUserEmail()) != null;
    }

    @Override
    public boolean isRepeatedUserPhone(User user) {
        if (user == null) {
            throw new InvalidParameterException("输入对象不能为空");
        }
        return getUserByPhone(user.getUserPhone()) != null;
    }

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
        return (User) ShiroUtils.getSessionAttribute(SessionConstants.USER_INFO_SESSION_KEY);
    }

    @Override
    public User updateSessionUserInfo() {
        User originUser = getSessionUserInfo();
        if (originUser != null) {
            if (!RoleEnum.GUEST.equals(originUser.getUserRole())) {
                //如果不是用游客号登陆的，根据id更新一下session中信息
                User newUser = getUserById(originUser.getId());
                ShiroUtils.setSessionAttribute(SessionConstants.USER_INFO_SESSION_KEY, newUser);
                return newUser;
            }
        }
        return null;
    }

    @Override
    public EmailVerifyCode sendVerifyCodeToEmail(String userEmail) throws InvalidEmailException {
        if (!MyStringUtils.isEmail(userEmail)) {
            throw new InvalidEmailException("发送的用户邮箱不合法");
        }
        // 获取前端传入的参数
        String emailAddress = userEmail;
        //6位随机验证码
        String verifyCode = CodeUtils.randomCodes();
        String emailMsg = "收到来自新东方优能中学助教工作平台的验证码：\n" + verifyCode + "\n有效时间: " + EmailVerifyCode.getValidTimeMinutes() + "分钟";

        // 邮件发送处理
        SendEmailUtils.sendConcurrentEncryptedEmail(emailAddress, emailMsg);
        //设置redis缓存
        ValueOperations<String, Object> vOps = redisTemplate.opsForValue();
        final String baseKey = RedisConstants.USER_VERIFYCODE_EMAIL_KEY;
        String key = baseKey + ":" + userEmail;
        vOps.set(key, verifyCode);
        //5分钟有效时间
        redisTemplate.expire(key, EmailVerifyCode.getValidTimeMinutes(), TimeUnit.MINUTES);

        return new EmailVerifyCode(userEmail, verifyCode);
    }

    @Override
    public boolean isValidEmailVerifyCode(EmailVerifyCode emailVerifyCode) {
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
        redisOperation.expireKey(key);
        return true;
    }

    @Override
    public long updatePasswordByEmail(String userEmail, String userPassword) {
        if (StringUtils.isEmpty(userEmail) || StringUtils.isEmpty(userPassword)) {
            logger.error("由邮箱更改密码时，邮箱或密码入参为空");
            return 0;
        }
        User user = userMapper.getUserByEmail(userEmail);
        if (user == null) {
            logger.error("由邮箱更改密码时，查找不到指定邮箱的用户。疑似绕过了前端请求。");
            return 0;
        }
        //加密明文密码
        userPassword = ShiroUtils.encryptUserPassword(userPassword, user.getUserSalt());
        return userMapper.updatePasswordByEmail(userEmail, userPassword);
    }

    @Override
    public String uploadUserIcon(MultipartFile file) throws InvalidFileInputException {
        User user = userService.getSessionUserInfo();
        return uploadUserIcon(file, user.getId().toString());
    }

    @Override
    public String uploadUserIcon(MultipartFile file, String id) throws InvalidFileInputException {
        if (file == null || file.isEmpty()) {
            String msg = "上传文件为空";
            logger.error(msg);
            throw new InvalidFileInputException(msg);
        }


        String originalFilename = file.getOriginalFilename();
        String idStr;
        if (StringUtils.isEmpty(id)) {
            //新用户设uuid作为头像名后缀
            idStr = UUID.randomUUID().toString().replace("-", "_");
        } else {
            idStr = id;
        }
        String fileName = "user_icon_" + idStr + originalFilename.substring(originalFilename.lastIndexOf("."));
        String filePath = filePathProperties.getUploadUserIconPath();
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("id:" + id + "——用户头像上传失败");
        }
        return fileName;
    }

    @Override
    public String updateOwnInfo(User user) {
        User originalUser = getSessionUserInfo();
        if (user == null) {
            return FAILURE;
        }

        if (!StringUtils.isEmpty(user.getUserIdCard())) {
            //新身份证不为空
            if (isModifiedAndRepeatedUserIdCard(originalUser, user)) {
                //身份证修改过了，判断是否与已存在的身份证冲突
                return ID_CARD_REPEAT;
            }
        } else {
            user.setUserIdCard(null);
        }

        if (isModifiedAndRepeatedUserName(originalUser, user)) {
            return NAME_REPEAT;
        }

        /*
         * 用户上传的头像的处理
         */
        dealWithUpdatingUserIcon(originalUser, user);

        if (isUnchangedUserOwnInfo(originalUser, user)) {
            //判断输入对象的对应字段是否未做任何修改
            return UNCHANGED;
        }

        //执行更新
        userMapper.updateOwnInfo(user);
        return SUCCESS;
    }

    /**
     * 更新用户信息时对头像的处理。先删除，再重命名
     *
     * @param originalUser 原来的用户信息
     * @param newUser      更新后的用户信息
     */
    private void dealWithUpdatingUserIcon(User originalUser, User newUser) {
        if (!StringUtils.isEmpty(newUser.getUserIcon())) {
            //如果用户上传了新头像
            //删除原头像，如果有的话
            deleteUserIcon(originalUser);
            //重命名新的头像缓存的图片文件名
            renameInformationImage(newUser);
        } else {
            //仍设置原头像
            newUser.setUserIcon(originalUser.getUserIcon());
        }
    }

    /**
     * 对入参用户，删除它在硬盘上保存的头像图片文件（如果有且不是默认图片的话）。
     *
     * @param user 指定的用户
     */
    private void deleteUserIcon(User user) {
        if (!StringUtils.isEmpty(user.getUserIcon())) {
            if (!user.isDefaultUserIcon()) {
                //如果用户原来的头像不是默认头像，需要将原来的头像删除
                FileUtils.deleteFile(filePathProperties.getUploadUserIconPath() + user.getUserIcon());
            }
        }
    }

    /**
     * 对要更新的用户的头像，重命名新的头像缓存的图片文件名。
     *
     * @param user 指定要更新的用户
     * @return 重命名后的新图片文件名
     */
    private String renameInformationImage(User user) {
        //将上传的新头像文件重名为含日期时间的newUserIconName，该新文件名用来保存到数据库
        String newUserIconName = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date()) + "_" + user.getUserIcon();
        FileUtils.renameByName(filePathProperties.getUploadUserIconPath(), user.getUserIcon(), newUserIconName);
        user.setUserIcon(newUserIconName);

        return newUserIconName;
    }

    /**
     * 判断当前要更新的用户的身份证是否修改过且重复。
     * 只有相较于原来的用户修改过且与数据库中重复才返回false
     *
     * @param originalUser 用来比较的原来的用户
     * @param newUser      要更新的用户
     * @return 身份证是否修改过且重复
     */
    private boolean isModifiedAndRepeatedUserIdCard(User originalUser, User newUser) {
        if (!newUser.getUserIdCard().equals(originalUser.getUserIdCard())) {
            //身份证修改过了，判断是否与已存在的身份证冲突
            if (isRepeatedUserIdCard(newUser)) {
                //修改后的身份证已存在
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前要更新的用户的用户名是否修改过且重复。
     * 只有相较于原来的用户修改过且与数据库中重复才返回false
     *
     * @param originalUser 用来比较的原来的用户
     * @param newUser      要更新的用户
     * @return 用户名是否修改过且重复
     */
    private boolean isModifiedAndRepeatedUserName(User originalUser, User newUser) {
        if (!originalUser.getUserName().equals(newUser.getUserName())) {
            //如果用户名被修改过了
            if (isRepeatedUserName(newUser)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户更新自己资料时，相应字段是否有修改
     *
     * @param originalUser 原用户信息
     * @param newUser      修改后的用户信息
     * @return 是否有修改
     */
    private boolean isUnchangedUserOwnInfo(User originalUser, User newUser) {
        return StringUtils.equals(newUser.getUserName(), originalUser.getUserName()) && StringUtils.equals(newUser.getUserRealName(), originalUser.getUserRealName())
                && StringUtils.equals(newUser.getUserIdCard(), originalUser.getUserIdCard()) && StringUtils.equals(newUser.getUserIcon(), originalUser.getUserIcon());
    }

    @Override
    public long updateEmailById(Long id, String userEmail) {
        if (id == null) {
            return 0;
        }
        return userMapper.updateEmailById(id, userEmail);
    }

    @Override
    public long updatePhoneById(Long id, String userPhone) {
        if (id == null) {
            return 0;
        }
        return userMapper.updatePhoneById(id, userPhone);
    }

    @Override
    public long updatePasswordById(Long id, String salt, String userPasswordNotEncrypted) {
        if (id == null || StringUtils.isEmpty(salt) || StringUtils.isEmpty(userPasswordNotEncrypted)) {
            return 0;
        }
        String userPasswordEncrypted = ShiroUtils.encryptUserPassword(userPasswordNotEncrypted, salt);
        return userMapper.updatePasswordById(id, userPasswordEncrypted);
    }

    @Override
    public PageInfo<User> listUsers(MyPage myPage, UserSearchCondition condition) {
        PageHelper.startPage(myPage.getPageNum(), myPage.getPageSize());
        List<User> users = userMapper.listUsers(condition);
        return new PageInfo<>(users);
    }

    @Override
    public String updateUserInfo(User user) {
        if (user == null) {
            return FAILURE;
        }

        User originalUser = getUserById(user.getId());

        if (originalUser == null) {
            return FAILURE;
        }

        if (!StringUtils.isEmpty(user.getUserWorkId())) {
            //新工号不为空
            if (isModifiedAndRepeatedUserWorkId(originalUser, user)) {
                //工号修改过了，判断是否与已存在的工号冲突
                return WORK_ID_REPEAT;
            }

        } else {
            user.setUserWorkId(null);
        }

        if (!StringUtils.isEmpty(user.getUserIdCard())) {
            //新身份证不为空
            if (isModifiedAndRepeatedUserIdCard(originalUser, user)) {
                //身份证修改过了，判断是否与已存在的身份证冲突
                return ID_CARD_REPEAT;
            }
        } else {
            user.setUserIdCard(null);
        }

        if (isModifiedAndRepeatedUserName(originalUser, user)) {
            //用户名修改过了，判断是否与已存在的用户名冲突
            return NAME_REPEAT;
        }

        if (!StringUtils.isEmpty(user.getUserEmail())) {
            //新邮箱不为空
            if (isModifiedAndRepeatedUserEmail(originalUser, user)) {
                //邮箱修改过了，判断是否与已存在的邮箱冲突
                return EMAIL_REPEAT;
            }
        } else {
            user.setUserEmail(null);
        }

        if (!StringUtils.isEmpty(user.getUserPhone())) {
            //新手机不为空
            if (isModifiedAndRepeatedUserPhone(originalUser, user)) {
                //电话修改过了，判断是否与已存在的电话冲突
                return PHONE_REPEAT;
            }
        } else {
            user.setUserPhone(null);
        }

        /*
         * 用户上传的头像的处理
         */
        dealWithUpdatingUserIcon(originalUser, user);

        if (isUnchangedUserInfo(originalUser, user)) {
            //判断输入对象的对应字段是否未做任何修改
            return UNCHANGED;
        }

        userMapper.updateUserInfo(user);
        return SUCCESS;
    }

    /**
     * 判断当前要更新的用户的工号是否修改过且重复。
     * 只有相较于原来的用户修改过且与数据库中重复才返回false
     *
     * @param originalUser 用来比较的原来的用户
     * @param newUser      要更新的用户
     * @return 工号是否修改过且重复
     */
    private boolean isModifiedAndRepeatedUserWorkId(User originalUser, User newUser) {
        if (!originalUser.getUserWorkId().equals(newUser.getUserWorkId())) {
            //如果用户名被修改过了
            if (isRepeatedUserWorkId(newUser)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前要更新的用户的邮箱是否修改过且重复。
     * 只有相较于原来的用户修改过且与数据库中重复才返回false
     *
     * @param originalUser 用来比较的原来的用户
     * @param newUser      要更新的用户
     * @return 邮箱是否修改过且重复
     */
    private boolean isModifiedAndRepeatedUserEmail(User originalUser, User newUser) {
        if (!newUser.getUserEmail().equals(originalUser.getUserEmail())) {
            //邮箱修改过了，判断是否与已存在的邮箱冲突
            if (isRepeatedUserEmail(newUser)) {
                //修改后的邮箱已存在
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前要更新的用户的电话是否修改过且重复。
     * 只有相较于原来的用户修改过且与数据库中重复才返回false
     *
     * @param originalUser 用来比较的原来的用户
     * @param newUser      要更新的用户
     * @return 电话是否修改过且重复
     */
    private boolean isModifiedAndRepeatedUserPhone(User originalUser, User newUser) {
        if (!newUser.getUserPhone().equals(originalUser.getUserPhone())) {
            //电话修改过了，判断是否与已存在的电话冲突
            if (isRepeatedUserPhone(newUser)) {
                //修改后的电话已存在
                return true;
            }
        }
        return false;
    }


    /**
     * 判断用户更新自己资料时，相应字段是否有修改
     *
     * @param originalUser 原用户信息
     * @param newUser      修改后的用户信息
     * @return 是否有修改
     */
    private boolean isUnchangedUserInfo(User originalUser, User newUser) {
        return StringUtils.equals(newUser.getUserWorkId(), originalUser.getUserWorkId()) && StringUtils.equals(newUser.getUserName(), originalUser.getUserName()) && StringUtils.equals(newUser.getUserRealName(), originalUser.getUserRealName())
                && StringUtils.equals(newUser.getUserIdCard(), originalUser.getUserIdCard()) && StringUtils.equals(newUser.getUserRole(), originalUser.getUserRole()) && StringUtils.equals(newUser.getUserIcon(), originalUser.getUserIcon())
                && StringUtils.equals(newUser.getUserEmail(), originalUser.getUserEmail()) && StringUtils.equals(newUser.getUserPhone(), originalUser.getUserPhone())
                && StringUtils.equals(newUser.getUserRemark(), originalUser.getUserRemark());
    }

    @Override
    public UpdateResult insertOneUser(User user) {
        if (user == null) {
            return new UpdateResult(FAILURE);
        }
        if (!StringUtils.isEmpty(user.getUserWorkId())) {
            //新工号不为空
            if (isRepeatedUserWorkId(user)) {
                //添加的工号已存在
                return new UpdateResult(WORK_ID_REPEAT);
            }
        } else {
            user.setUserWorkId(null);
        }

        return insertUserWithUnrepeatedWorkId(user);
    }

    /**
     * 插入工号不重复的用户信息
     *
     * @param user 用户信息
     * @return 1. "idCardRepeat"：身份证重复
     * 2. "nameRepeat"：姓名重复
     * 3. "emailRepeat"：邮箱重复
     * 4. "phoneRepeat"：电话重复
     * 5."unchanged": 对比数据库原记录未做任何修改
     * 6."success": 更新成功
     */
    private UpdateResult insertUserWithUnrepeatedWorkId(User user) {
        if (user == null) {
            return new UpdateResult(FAILURE);
        }
        if (!StringUtils.isEmpty(user.getUserIdCard())) {
            //新身份证不为空
            if (isRepeatedUserIdCard(user)) {
                //添加的身份证已存在
                return new UpdateResult(ID_CARD_REPEAT);
            }
        } else {
            user.setUserIdCard(null);
        }


        if (isRepeatedUserName(user)) {
            //添加的用户名已存在
            return new UpdateResult(NAME_REPEAT);
        }

        if (!StringUtils.isEmpty(user.getUserEmail())) {
            //新邮箱不为空
            if (isRepeatedUserEmail(user)) {
                //添加的邮箱已存在
                return new UpdateResult(EMAIL_REPEAT);
            }
        } else {
            user.setUserEmail(null);
        }

        if (!StringUtils.isEmpty(user.getUserPhone())) {
            //新手机不为空
            if (isRepeatedUserPhone(user)) {
                //添加的电话已存在
                return new UpdateResult(PHONE_REPEAT);
            }
        } else {
            user.setUserPhone(null);
        }

        /*
         * 用户密码，盐，用户头像等设置
         */
        user.setDefaultUserPasswordAndSalt();

        user.setNewDefaultUserIcon();


        UpdateResult result = new UpdateResult(SUCCESS);
        //执行插入
        result.setInsertCount(userMapper.insertOneUser(user));

        //向新用户发送欢迎消息
        sendWelcomeMessageToUser(user);

        return result;
    }

    /**
     * 向新用户发送欢迎消息
     *
     * @param user 新用户
     */
    private void sendWelcomeMessageToUser(User user) {
        Long id = getUserByName(user.getUserName()).getId();

        UserMessage message = new UserMessage();
        message.setUserId(id);
        message.setUserFromId(Constants.ADMIN_USER_ID);
        message.setMessageTitle("欢迎使用AOWS-优能助教在线工作平台");
        StringBuilder messageContent = new StringBuilder();
        messageContent.append(user.getUserRole() + "-" + user.getUserRealName()).append("，欢迎使用AOWS-优能助教在线工作平台！")
                .append("<br>为了您的账号安全，请尽快修改默认密码。也推荐绑定安全邮箱便于找回密码以及安全验证！")
                .append("<br>常用功能：")
                .append("<br>1. 我的班级信息：左边菜单栏>信息管理>班级信息，点击按钮\"查询我的班级\"。")
                .append("<br>2. 做开班表格：完成1中的操作后，点击表格中的\"开班做表\"，跳转做表页面后，点击输出相应表格即可。")
                .append("<br>3. 学生上课信息：左边菜单栏>信息管理>学员信息>上课信息，可以查询指定学生的上课记录。")
                .append("<br>4. 登录方式之懒癌登录：懒癌登录方式的答案，可以在\"左边菜单栏>懒癌登录问题一览\"查询。你也可以将自己喜欢的问题添加到问题的随机池中！")
                .append("<br>5. 用户角色权限说明：管理员>学管>助教长>助教=教师>游客。可在：用户>个人信息>基本资料，查看自己的角色。通过\"懒癌登录\"的用户角色是游客~");
        message.setMessageContent(messageContent.toString());
        message.setWelcomePicture();
        message.setMessageTime(new Date());
        if (UserMessageUtils.isValidUserMessageUpdateInfo(message)) {
            userMessageService.insertOneUserMessage(message);
        }

    }

    /**
     * 根据用户id删除用户头像文件
     *
     * @param id 用户id
     */
    private void deleteUserIconById(Long id) {
        User user = getUserById(id);
        if (user != null) {
            /*
             * 删除用户头像文件
             */
            deleteUserIcon(user);
        }
    }


    @Override
    public long deleteOneUserById(Long id) {
        if (id == null) {
            return 0;
        }

        deleteUserIconById(id);

        return userMapper.deleteOneUserById(id);
    }

    @Override
    public long deleteManyUsersByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return 0;
        }

        for (Long id : ids) {
            deleteUserIconById(id);
        }
        return userMapper.deleteManyUsersByIds(ids);
    }

    @Override
    public DefaultFromExcelUpdateResult insertAndUpdateUsersFromExcel(List<User> users) {
        if (users == null) {
            String msg = "insertAndUpdateUsersFromExcel方法输入用户users为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }
        DefaultFromExcelUpdateResult result = new DefaultFromExcelUpdateResult(SUCCESS);
        String userRealNameKeyword = ExcelConstants.REAL_NAME_COLUMN;
        InvalidData invalidData = new InvalidData(userRealNameKeyword);
        for (User user : users) {
            if (UserUtils.isValidUserUpdateInfo(user)) {
                result.add(insertAndUpdateOneUserFromExcel(user));
            } else {
                String msg = "表格输入用户user不合法!";
                logger.error(msg);
                result.setResult(Constants.EXCEL_INVALID_DATA);
                invalidData.putValue(userRealNameKeyword, user.getUserRealName());
            }
        }
        result.setInvalidData(invalidData);

        return result;
    }

    /**
     * 根据从excel中读取到的user信息，更新插入一个。根据工号判断：
     * if 当前工号不存在
     * 执行插入
     * else
     * 根据工号更新
     *
     * @param user 要更新的用户信息
     * @return 更新结果
     * @throws InvalidParameterException 不合法的入参异常
     */
    private UpdateResult insertAndUpdateOneUserFromExcel(User user) throws InvalidParameterException {
        if (user == null) {
            String msg = "insertAndUpdateOneUserFromExcel方法输入用户user为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        UpdateResult result = new UpdateResult();

        if (!isRepeatedUserWorkId(user)) {
            /*
              插入。如果用户目前没有给工号，那么insertUserWithUnrepeatedWorkId方法会处理好导入逻辑，
              因为如果身份证等其他唯一字段有重复，insertUserWithUnrepeatedWorkId方法会直接return
             */
            result.add(insertUserWithUnrepeatedWorkId(user));
        }
        result.setResult(SUCCESS);
        return result;
    }

    @Override
    public String updateUserByWorkId(User user) {
        if (user == null) {
            return FAILURE;
        }
        User originalUser = getUserByWorkId(user.getUserWorkId());
        return updateUserByWorkId(originalUser, user);
    }

    @Override
    public String updateUserByWorkId(User originalUser, User newUser) {
        if (originalUser == null || newUser == null) {
            return FAILURE;
        }

        if (!StringUtils.isEmpty(newUser.getUserIdCard())) {
            //新身份证不为空
            if (isModifiedAndRepeatedUserIdCard(originalUser, newUser)) {
                //身份证修改过了，判断是否与已存在的身份证冲突
                return ID_CARD_REPEAT;
            }
        } else {
            newUser.setUserIdCard(null);
        }

        if (isModifiedAndRepeatedUserName(originalUser, newUser)) {
            //用户名修改过了，判断是否与已存在的用户名冲突
            return NAME_REPEAT;
        }

        if (!StringUtils.isEmpty(newUser.getUserEmail())) {
            //新邮箱不为空
            if (isModifiedAndRepeatedUserEmail(originalUser, newUser)) {
                //邮箱修改过了，判断是否与已存在的邮箱冲突
                return EMAIL_REPEAT;
            }
        } else {
            newUser.setUserEmail(null);
        }

        if (!StringUtils.isEmpty(newUser.getUserPhone())) {
            //新手机不为空
            if (isModifiedAndRepeatedUserPhone(originalUser, newUser)) {
                //电话修改过了，判断是否与已存在的电话冲突
                return PHONE_REPEAT;
            }
        } else {
            newUser.setUserPhone(null);
        }

        //TODO
//        if (StringUtils.equals(originalUser.getUserName(), originalUser.getUserName()) && StringUtils.equals(originalUser.getUserRealName(), originalUser.getUserRealName())
//                && StringUtils.equals(user.getUserIdCard(), originalUser.getUserIdCard()) && StringUtils.equals(user.getUserIcon(), originalUser.getUserIcon())){
//            //判断输入对象的对应字段是否未做任何修改
//            return UNCHANGED;
//        }

        userMapper.updateUserByWorkId(newUser);
        return SUCCESS;
    }

    @Override
    public String deleteUsersByCondition(UserSearchCondition condition) {
        if (condition == null) {
            return FAILURE;
        }
        User userSession = getSessionUserInfo();

        List<User> users = userMapper.listUsers(condition);
        List<Long> ids = new ArrayList<>();
        for (User user : users) {
            if (user.getId().equals(userSession.getId())) {
                //试图删除自己
                return "noPermissionsToDeleteYourself";
            }

            if (RoleEnum.ASSISTANT_MANAGER.equals(userSession.getUserRole())) {
                if (RoleEnum.ADMINISTRATOR.equals(user.getUserRole())) {
                    //如果学管尝试删除管理员，无权限
                    return "noPermissions";
                }
            }
            ids.add(user.getId());
        }

        deleteManyUsersByIds(ids);
        return SUCCESS;
    }

    @Override
    public PageInfo<UserSendTo> listUsersSendTo(MyPage myPage, UserSendToSearchCondition condition) {
        PageHelper.startPage(myPage.getPageNum(), myPage.getPageSize());
        List<UserSendTo> users = (StringUtils.isEmpty(condition.getCommonSearch()) && StringUtils.isEmpty(condition.getUserRole()) && StringUtils.isEmpty(condition.getCampus()))
                ? new ArrayList<>() : userMapper.listUsersSendTo(condition);
        return new PageInfo<>(users);
    }

}
