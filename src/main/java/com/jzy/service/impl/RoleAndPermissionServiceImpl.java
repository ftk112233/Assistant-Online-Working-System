package com.jzy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.dao.RoleAndPermissionMapper;
import com.jzy.manager.constant.Constants;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.RoleAndPermissionSearchCondition;
import com.jzy.model.entity.RoleAndPermission;
import com.jzy.service.RoleAndPermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RoleAndPermissionServiceImpl
 * @Author JinZhiyun
 * @Description 角色权限业务实现
 * @Date 2019/11/21 15:08
 * @Version 1.0
 **/
@Service
public class RoleAndPermissionServiceImpl extends AbstractServiceImpl implements RoleAndPermissionService {
    @Autowired
    private RoleAndPermissionMapper roleAndPermissionMapper;

    @Override
    public List<String> listPermsByRole(String role) {
        return StringUtils.isEmpty(role) ?  new ArrayList<>() : roleAndPermissionMapper.listPermsByRole(role);
    }

    @Override
    public RoleAndPermission getByRoleAndPerm(String role, String perm) {
        return StringUtils.isEmpty(role) || StringUtils.isEmpty(perm) ? null : roleAndPermissionMapper.getByRoleAndPerm(role,perm);
    }

    @Override
    public PageInfo<RoleAndPermission> listRoleAndPerms(MyPage myPage, RoleAndPermissionSearchCondition condition) {
        PageHelper.startPage(myPage.getPageNum(), myPage.getPageSize());
        List<RoleAndPermission> roleAndPermissions = roleAndPermissionMapper.listRoleAndPerms(condition);
        return new PageInfo<>(roleAndPermissions);
    }

    @Override
    public String updateRoleAndPermissionInfo(RoleAndPermission roleAndPermission) {
        RoleAndPermission originalRoleAndPermission=getRoleAndPermById(roleAndPermission.getId());
        if (!roleAndPermission.getRole().equals(originalRoleAndPermission.getRole())
            || !roleAndPermission.getPerm().equals(originalRoleAndPermission.getPerm())) {
            //角色或权限修改过了，判断是否与已存在的记录冲突
            if (getByRoleAndPerm(roleAndPermission.getRole(), roleAndPermission.getPerm()) != null) {
                //修改后的角色和权限已存在
                return "roleAndPermRepeat";
            }
        }

        //执行更新
        roleAndPermissionMapper.updateRoleAndPermissionInfo(roleAndPermission);
        return Constants.SUCCESS;
    }

    @Override
    public RoleAndPermission getRoleAndPermById(Long id) {
        return id == null ? null : roleAndPermissionMapper.getRoleAndPermById(id);
    }

    @Override
    public String insertRoleAndPermission(RoleAndPermission roleAndPermission) {
        if (getByRoleAndPerm(roleAndPermission.getRole(), roleAndPermission.getPerm()) != null) {
            //角色和权限已存在
            return "roleAndPermRepeat";
        }

        roleAndPermissionMapper.insertRoleAndPermission(roleAndPermission);
        return Constants.SUCCESS;
    }

    @Override
    public void deleteOneRoleAndPermissionById(Long id) {
        roleAndPermissionMapper.deleteOneRoleAndPermissionById(id);
    }

    @Override
    public void deleteManyRoleAndPermissionsByIds(List<Long> ids) {
        for (Long id:ids){
            deleteOneRoleAndPermissionById(id);
        }
    }
}
