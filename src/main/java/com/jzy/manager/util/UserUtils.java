package com.jzy.manager.util;

import com.jzy.model.entity.User;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UserUtils
 * @description 用户的工具类 {@link com.jzy.model.entity.User}
 * 对用户的增删改入参对象字段的校验，服务端的校验应该与前端js保持一致，且必须严格于数据库列属性要求的标准
 * @date 2019/11/15 17:00
 **/
public class UserUtils {
    /**
     * 用户的所有角色
     */
    public static List<String> USER_ROLES = Arrays.asList("管理员", "学管", "助教长", "助教", "教师", "游客");

    /**
     * 用户默认头像
     */
    public static final String USER_ICON_DEFAULT = "user_icon_default.jpg";

    private UserUtils() {
    }


    /**
     * 用户的工号，即助教的工号，唯一，长度小于32可以为空
     *
     * @param userWorkId
     * @return
     */
    public static boolean isValidUserWorkId(String userWorkId) {
        return userWorkId == null || userWorkId.length() <= 32;
    }

    /**
     * 身份证，唯一，可以为空
     *
     * @param userIdCard
     * @return
     */
    public static boolean isValidUserIdCard(String userIdCard) {
        return StringUtils.isEmpty(userIdCard) || MyStringUtils.IdCardUtil.isValidatedAllIdCard(userIdCard);
    }

    /**
     * 用户名，唯一非空，可以自定义，6~20位(数字、字母、下划线)以字母开头
     *
     * @param userName
     * @return
     */
    public static boolean isValidUserName(String userName) {
        return MyStringUtils.isUserName(userName);
    }

    /**
     * 用户密码，非空，6~20个字符(数字、字母、下划线)
     *
     * @param userPassword
     * @return
     */
    public static boolean isValidUserPassword(String userPassword) {
        return MyStringUtils.isPassword(userPassword);
    }

    /**
     * 用户的真实姓名，非空，50个字符以内
     *
     * @param userRealName
     * @return
     */
    public static boolean isValidUserRealName(String userRealName) {
        return !StringUtils.isEmpty(userRealName) && userRealName.length() <= 50;
    }

    /**
     * 用户身份,"管理员","学管", "助教长", "助教", "教师", "游客"
     *
     * @param userRole
     * @return
     */
    public static boolean isValidUserRole(String userRole) {
        return USER_ROLES.contains(userRole);
    }

    /**
     * 用户的头像存储的地址路径，空或者长度小于100
     *
     * @param userIcon
     * @return
     */
    public static boolean isValidUserIcon(String userIcon) {
        return StringUtils.isEmpty(userIcon) || (FileUtils.isImage(userIcon) && userIcon.length() <= 100);
    }

    /**
     * 用户邮箱，唯一，空或者长度小于100
     *
     * @param userEmail
     * @return
     */
    public static boolean isValidUserEmail(String userEmail) {
        return StringUtils.isEmpty(userEmail) || (MyStringUtils.isEmail(userEmail) && userEmail.length() <= 100);
    }

    /**
     * 用户手机，唯一，空或者11位数字
     *
     * @param userPhone
     * @return
     */
    public static boolean isValidUserPhone(String userPhone) {
        return StringUtils.isEmpty(userPhone) || MyStringUtils.isPhone(userPhone);
    }

    /**
     * 用户备注，空或者长度小于500
     *
     * @param userRemark
     * @return
     */
    public static boolean isValidUserRemark(String userRemark) {
        return userRemark == null || userRemark.length() <= 500;
    }

    /**
     * user是否合法
     *
     * @param user 输入的user对象
     * @return
     */
    public static boolean isValidUserInfo(User user) {
        return user != null && isValidUserWorkId(user.getUserWorkId()) && isValidUserIdCard(user.getUserIdCard()) && isValidUserName(user.getUserName())
                && isValidUserPassword(user.getUserPassword()) && isValidUserRealName(user.getUserRealName()) && isValidUserRole(user.getUserRole())
                && isValidUserIcon(user.getUserIcon()) && isValidUserEmail(user.getUserEmail()) && isValidUserPhone(user.getUserPhone())
                && isValidUserRemark(user.getUserRemark());
    }

    /**
     * 用户自己更新基本资料操作的入参user校验
     *
     * @param user 输入的user对象
     * @return
     */
    public static boolean isValidUserUpdateOwnInfo(User user) {
        return user != null && isValidUserIdCard(user.getUserIdCard()) && isValidUserName(user.getUserName())
                && isValidUserRealName(user.getUserRealName()) && isValidUserIcon(user.getUserIcon());
    }

    /**
     * 用户管理中更新操作的入参user校验
     *
     * @param user 输入的user对象
     * @return
     */
    public static boolean isValidUserUpdateInfo(User user) {
        return user != null && isValidUserWorkId(user.getUserWorkId()) && isValidUserIdCard(user.getUserIdCard()) && isValidUserName(user.getUserName())
                && isValidUserRealName(user.getUserRealName()) && isValidUserRole(user.getUserRole())
                && isValidUserIcon(user.getUserIcon()) && isValidUserEmail(user.getUserEmail()) && isValidUserPhone(user.getUserPhone())
                && isValidUserRemark(user.getUserRemark());
    }

    /**
     * 用户管理中插入操作的入参user校验
     *
     * @param user 输入的user对象
     * @return
     */
    public static boolean isValidUserInsertInfo(User user) {
        return isValidUserUpdateInfo(user);
    }
}
