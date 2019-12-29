package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.RoleAndPermissionSearchCondition;
import com.jzy.model.entity.RoleAndPermission;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName RoleAndPermissionService
 * @description 角色权限业务
 * @date 2019/11/14 23:27
 **/
public interface RoleAndPermissionService {
    /**
     * 根据角色查询权限
     *
     * @param role 角色
     * @return
     */
    List<String> listPermsByRole(String role);

    /**
     * 根据角色和权限查询结果对象
     *
     * @param role 角色
     * @param perm 权限
     * @return
     */
    RoleAndPermission getByRoleAndPerm(String role, String perm);

    /**
     * 返回符合条件的用户权限分页结果
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return 分页结果
     */
    PageInfo<RoleAndPermission> listRoleAndPerms(MyPage myPage, RoleAndPermissionSearchCondition condition);

    /**
     * 角色权限管理中修改角色权限信息由id修改
     *
     * @param roleAndPermission 修改过的角色权限信息信息
     * @return 1."failure"：错误入参等异常
     * 2."roleAndPermRepeat"：(角色, 权限)组合已存在
     * 3."unchanged": 对比数据库原记录未做任何修改
     * 4."success": 更新成功
     */
    String updateRoleAndPermissionInfo(RoleAndPermission roleAndPermission);

    /**
     * 根据id查询结果对象
     *
     * @param id 自增id
     * @return 对应角色权限对象
     */
    RoleAndPermission getRoleAndPermById(Long id);

    /**
     * 角色权限管理中的添加角色权限
     *
     * @param roleAndPermission 新添加角色权限的信息
     * @return 1."failure"：错误入参等异常
     * 2."roleAndPermRepeat"：(角色, 权限)组合已存在
     * 3."success": 更新成功
     */
    String insertRoleAndPermission(RoleAndPermission roleAndPermission);

    /**
     * 根据id删除一个角色权限
     *
     * @param id 角色权限id
     * @return 更新记录数
     */
    long deleteOneRoleAndPermissionById(Long id);

    /**
     * 根据id删除多个角色权限
     *
     * @param ids 角色权限id的列表
     * @return 更新记录数
     */
    long deleteManyRoleAndPermissionsByIds(List<Long> ids);
}
