package com.jzy.dao;

import com.jzy.model.dto.search.RoleAndPermissionSearchCondition;
import com.jzy.model.entity.RoleAndPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName RoleAndPermissionMapper
 * @description 角色权限业务dao接口
 * @date 2019/11/13 16:18
 **/
public interface RoleAndPermissionMapper {
    /**
     * 根据角色查询权限
     *
     * @param role 角色
     * @return 指定角色的全部权限
     */
    List<String> listPermsByRole(@Param("role") String role);

    /**
     * 查询所有符合条件的角色权限信息
     *
     * @param condition 查询条件封装
     * @return 符合条件的角色权限对象
     */
    List<RoleAndPermission> listRoleAndPerms(RoleAndPermissionSearchCondition condition);

    /**
     * 根据角色和权限查询结果对象
     *
     * @param role 角色
     * @param perm 权限
     * @return 指定(角色, 权限)的角色权限对象
     */
    RoleAndPermission getByRoleAndPerm(@Param("role") String role, @Param("perm") String perm);

    /**
     * 根据id查询角色权限对象
     *
     * @param id 自增id
     * @return 对应角色权限对象
     */
    RoleAndPermission getRoleAndPermById(@Param("id") Long id);

    /**
     * 根据角色权限id更新角色权限的其他字段
     *
     * @param roleAndPermission 修改后的角色权限信息
     * @return 更新记录数
     */
    long updateRoleAndPermissionInfo(RoleAndPermission roleAndPermission);

    /**
     * 角色权限管理中的添加角色权限
     *
     * @param roleAndPermission 新添加角色权限的信息
     * @return 更新记录数
     */
    long insertOneRoleAndPermission(RoleAndPermission roleAndPermission);

    /**
     * 根据id删除一个角色权限
     *
     * @param id 角色权限id
     * @return 更新记录数
     */
    long deleteOneRoleAndPermissionById(@Param("id") Long id);

    /**
     * 根据id删除多个角色权限
     *
     * @param ids 角色权限id的列表
     * @return 更新记录数
     */
    long deleteManyRoleAndPermissionsByIds(List<Long> ids);
}
