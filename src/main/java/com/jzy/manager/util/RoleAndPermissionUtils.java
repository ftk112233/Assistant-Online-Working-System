package com.jzy.manager.util;

import com.jzy.model.entity.RoleAndPermission;
import com.jzy.model.entity.User;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @ClassName RoleAndPermissionUtils
 * @Author JinZhiyun
 * @Description 角色权限的工具类 {@link com.jzy.model.entity.RoleAndPermission}
 * 增删改入参对象字段的校验，服务端的校验应该与前端js保持一致，且必须严格于数据库列属性要求的标准
 * @Date 2019/11/21 17:10
 * @Version 1.0
 **/
public class RoleAndPermissionUtils {
    /**
     * 所有角色
     */
    private static List<String> ROLES = User.ROLES;

    private RoleAndPermissionUtils() {
    }

    public static boolean isValidRole(String role) {
        return ROLES.contains(role);
    }

    public static boolean isValidPerm(String perm) {
        return !StringUtils.isEmpty(perm) && perm.length() <= 100;
    }

    public static boolean isValidRemark(String remark) {
        return remark == null || remark.length() <= 500;
    }

    /**
     * roleAndPermission是否合法
     *
     * @param roleAndPermission 输入的roleAndPermission对象
     * @return
     */
    public static boolean isValidRoleAndPermissionInfo(RoleAndPermission roleAndPermission) {
        return roleAndPermission != null && isValidRole(roleAndPermission.getRole()) && isValidPerm(roleAndPermission.getPerm())
                && isValidRemark(roleAndPermission.getRemark());
    }

    /**
     * 角色权限管理中更新操作的入参roleAndPermission校验
     *
     * @param roleAndPermission 输入的roleAndPermission对象
     * @return
     */
    public static boolean isValidRoleAndPermissionUpdateInfo(RoleAndPermission roleAndPermission) {
        return isValidRoleAndPermissionInfo(roleAndPermission);
    }

    /**
     * 角色权限管理中更插入操作的入参roleAndPermission校验
     *
     * @param roleAndPermission 插入的roleAndPermission对象
     * @return
     */
    public static boolean isValidRoleAndPermissionInsertInfo(RoleAndPermission roleAndPermission) {
        return isValidRoleAndPermissionInfo(roleAndPermission);
    }
}
