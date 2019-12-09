package com.jzy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.dao.RoleAndPermissionMapper;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.RedisConstants;
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
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RoleAndPermissionMapper roleAndPermissionMapper;

    @Override
    public List<String> listPermsByRole(String role) {
        if (StringUtils.isEmpty(role)){
            return new ArrayList<>();
        }

        String key=RedisConstants.ROLE_AND_PERMS_KEY;
        if (hashOps.hasKey(key, role)) {
            //缓存中有
            String permsJSON= (String) hashOps.get(key, role);
            return JSONArray.parseArray(permsJSON, String.class);
        } else {
            //缓存中无，从数据库查
            List<String> perms= roleAndPermissionMapper.listPermsByRole(role);
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
        RoleAndPermission originalRoleAndPermission = getRoleAndPermById(roleAndPermission.getId());
        if (!roleAndPermission.getRole().equals(originalRoleAndPermission.getRole())
                || !roleAndPermission.getPerm().equals(originalRoleAndPermission.getPerm())) {
            //角色或权限修改过了，判断是否与已存在的记录冲突
            if (getByRoleAndPerm(roleAndPermission.getRole(), roleAndPermission.getPerm()) != null) {
                //修改后的角色和权限已存在
                return "roleAndPermRepeat";
            }
        }

        //清缓存
        String key=RedisConstants.ROLE_AND_PERMS_KEY;
        redisTemplate.expire(key, 0 ,TimeUnit.MINUTES);
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

        //清缓存
        String key=RedisConstants.ROLE_AND_PERMS_KEY;
        if (hashOps.hasKey(key, roleAndPermission.getRole())) {
            //缓存中有
            hashOps.delete(key, roleAndPermission.getRole());
        }
        roleAndPermissionMapper.insertRoleAndPermission(roleAndPermission);
        return Constants.SUCCESS;
    }

    @Override
    public long deleteOneRoleAndPermissionById(Long id) {
        if (id == null) {
            return 0;
        }

        //清缓存
        String key=RedisConstants.ROLE_AND_PERMS_KEY;
        redisTemplate.expire(key, 0 ,TimeUnit.MINUTES);
        return roleAndPermissionMapper.deleteOneRoleAndPermissionById(id);
    }

    @Override
    public long deleteManyRoleAndPermissionsByIds(List<Long> ids) {
        if (ids == null ||ids.size() == 0){
            return 0;
        }

        //清缓存
        String key=RedisConstants.ROLE_AND_PERMS_KEY;
        redisTemplate.expire(key, 0 ,TimeUnit.MINUTES);
        return roleAndPermissionMapper.deleteManyRoleAndPermissionsByIds(ids);
    }
}
