package com.jzy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.dao.RoleAndPermissionMapper;
import com.jzy.manager.constant.RedisConstants;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.RoleAndPermissionSearchCondition;
import com.jzy.model.entity.RoleAndPermission;
import com.jzy.service.RoleAndPermissionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final static Logger logger = LogManager.getLogger(RoleAndPermissionServiceImpl.class);

    /**
     * 表示(角色, 权限)组合冲突
     */
    private final static String ROLE_AND_PERM_REPEAT = "roleAndPermRepeat";

    @Autowired
    private RoleAndPermissionMapper roleAndPermissionMapper;

    @Override
    public boolean isRepeatedRoleAndPermission(RoleAndPermission roleAndPermission) {
        if (roleAndPermission == null) {
            throw new InvalidParameterException("输入对象不能为空");
        }
        return getByRoleAndPerm(roleAndPermission.getRole(), roleAndPermission.getPerm()) != null;
    }

    @Override
    public List<String> listPermsByRole(String role) {
        if (StringUtils.isEmpty(role)) {
            return new ArrayList<>();
        }

        String key = RedisConstants.ROLE_AND_PERMS_KEY;
        if (hashOps.hasKey(key, role)) {
            //缓存中有
            String permsJSON = (String) hashOps.get(key, role);
            return JSONArray.parseArray(permsJSON, String.class);
        } else {
            //缓存中无，从数据库查
            List<String> perms = roleAndPermissionMapper.listPermsByRole(role);
            //添加缓存
            hashOps.put(key, role, JSON.toJSONString(perms));
            return perms;
        }
    }

    @Override
    public RoleAndPermission getByRoleAndPerm(String role, String perm) {
        return StringUtils.isEmpty(role) || StringUtils.isEmpty(perm) ? null : roleAndPermissionMapper.getByRoleAndPerm(role, perm);
    }

    @Override
    public PageInfo<RoleAndPermission> listRoleAndPerms(MyPage myPage, RoleAndPermissionSearchCondition condition) {
        PageHelper.startPage(myPage.getPageNum(), myPage.getPageSize());
        List<RoleAndPermission> roleAndPermissions = roleAndPermissionMapper.listRoleAndPerms(condition);
        return new PageInfo<>(roleAndPermissions);
    }

    @Override
    public String updateRoleAndPermissionInfo(RoleAndPermission roleAndPermission) {
        if (roleAndPermission == null) {
            return FAILURE;
        }
        RoleAndPermission originalRoleAndPermission = getRoleAndPermById(roleAndPermission.getId());
        if (originalRoleAndPermission == null) {
            return FAILURE;
        }

        if (isModifiedAndRepeatedRoleAndPermission(originalRoleAndPermission, roleAndPermission)) {
            //角色或权限修改过了，判断是否与已存在的记录冲突
            return ROLE_AND_PERM_REPEAT;
        }

        if (roleAndPermission.equalsExceptBaseParams(originalRoleAndPermission)) {
            //未修改
            return UNCHANGED;
        }

        //执行更新
        roleAndPermissionMapper.updateRoleAndPermissionInfo(roleAndPermission);
        return SUCCESS;
    }

    /**
     * 判断当前要更新的角色权限是否修改过且重复。
     * 只有相较于原来的角色权限修改过且与数据库中重复才返回false
     *
     * @param originalRoleAndPermission 用来比较的原来的角色权限
     * @param newRoleAndPermission      要更新的角色权限
     * @return 角色权限的内容是否修改过且重复
     */
    private boolean isModifiedAndRepeatedRoleAndPermission(RoleAndPermission originalRoleAndPermission, RoleAndPermission newRoleAndPermission) {
        if (!newRoleAndPermission.getRole().equals(originalRoleAndPermission.getRole())
                || !newRoleAndPermission.getPerm().equals(originalRoleAndPermission.getPerm())) {
            //角色或权限修改过了，判断是否与已存在的记录冲突
            if (isRepeatedRoleAndPermission(newRoleAndPermission)) {
                //修改后的角色和权限已存在
                return true;
            }
        }
        return false;
    }

    @Override
    public RoleAndPermission getRoleAndPermById(Long id) {
        return id == null ? null : roleAndPermissionMapper.getRoleAndPermById(id);
    }

    @Override
    public String insertOneRoleAndPermission(RoleAndPermission roleAndPermission) {
        if (roleAndPermission == null) {
            return FAILURE;
        }
        if (isRepeatedRoleAndPermission(roleAndPermission)) {
            //角色和权限已存在
            return ROLE_AND_PERM_REPEAT;
        }

        roleAndPermissionMapper.insertOneRoleAndPermission(roleAndPermission);
        return SUCCESS;
    }

    @Override
    public long deleteOneRoleAndPermissionById(Long id) {
        if (id == null) {
            return 0;
        }
        return roleAndPermissionMapper.deleteOneRoleAndPermissionById(id);
    }

    @Override
    public long deleteManyRoleAndPermissionsByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return 0;
        }
        return roleAndPermissionMapper.deleteManyRoleAndPermissionsByIds(ids);
    }
}
