package com.jzy.manager.util;

import com.jzy.model.entity.User;
import org.apache.commons.lang3.StringUtils;

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
    private static List<String> USER_ROLES = User.ROLES;

    /**
     * 用户默认头像
     */
    private static final String USER_ICON_DEFAULT = User.USER_ICON_DEFAULT;

    private UserUtils() {
    }


    public static boolean isValidUserWorkId(String userWorkId) {
        return userWorkId == null || userWorkId.length() <= 32;
    }

    public static boolean isValidUserIdCard(String userIdCard) {
        return StringUtils.isEmpty(userIdCard) || MyStringUtils.IdCardUtil.isValidatedAllIdCard(userIdCard);
    }

    public static boolean isValidUserName(String userName) {
        return MyStringUtils.isUserName(userName);
    }

    public static boolean isValidUserPassword(String userPassword) {
        return MyStringUtils.isPassword(userPassword);
    }

    public static boolean isValidUserRealName(String userRealName) {
        return !StringUtils.isEmpty(userRealName) && userRealName.length() <= 50;
    }

    public static boolean isValidUserRole(String userRole) {
        return USER_ROLES.contains(userRole);
    }

    public static boolean isValidUserIcon(String userIcon) {
        return StringUtils.isEmpty(userIcon) || (FileUtils.isImage(userIcon) && userIcon.length() <= 100);
    }

    public static boolean isValidUserEmail(String userEmail) {
        return StringUtils.isEmpty(userEmail) || (MyStringUtils.isEmail(userEmail) && userEmail.length() <= 100);
    }

    public static boolean isValidUserPhone(String userPhone) {
        return StringUtils.isEmpty(userPhone) || MyStringUtils.isPhone(userPhone);
    }

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
