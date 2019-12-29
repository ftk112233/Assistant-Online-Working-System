package com.jzy.model.vo;

import com.jzy.model.RoleEnum;
import com.jzy.model.entity.RoleAndPermission;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RoleCheckbox
 * @Author JinZhiyun
 * @Description 角色权限添加时前台传入的checkbox数据的封装，role是否='on'即是否选中
 * @Date 2019/11/21 18:41
 * @Version 1.0
 **/
@Data
public class RoleCheckbox {
    private static final String ON="on";

    /**
     * 管理员
     */
    private String administrator;

    /**
     * 学管
     */
    private String assistantManager;

    /**
     * 助教长
     */
    private String assistantMaster;

    /**
     * 助教
     */
    private String assistant;

    /**
     * 教师
     */
    private String teacher;

    /**
     * 游客
     */
    private String guest;

    /**
     * administrator的checkbox是否被选中，下同理
     *
     * @return
     */
    public boolean isAdministratorOn(){
        return ON.equals(administrator);
    }

    public boolean isAssistantManagerOn(){
        return ON.equals(assistantManager);
    }

    public boolean isAssistantMasterOn(){
        return ON.equals(assistantMaster);
    }

    public boolean isAssistantOn(){
        return ON.equals(assistant);
    }

    public boolean isTeacherOn(){
        return ON.equals(teacher);
    }

    public boolean isGuestOn(){
        return ON.equals(guest);
    }

    /**
     * 根据当前checkbox选中的角色，生成相应的RoleAndPermission的list。
     * 如选中(administrator, assistantManager)
     * 则返回[角色为管理员的roleAndPermission, 角色为学管的roleAndPermission]
     *
     * @param roleAndPermission 原来的roleAndPermission信息
     * @return 含选中角色的RoleAndPermission集合
     */
    public List<RoleAndPermission> getRoleAndPermissions(RoleAndPermission roleAndPermission){
        List<RoleAndPermission> roleAndPermissions = new ArrayList<>();

        if (isAdministratorOn()) {
            RoleAndPermission newRoleAndPermission = new RoleAndPermission();
            newRoleAndPermission.setRole(RoleEnum.ADMINISTRATOR.getRole());
            newRoleAndPermission.setPerm(roleAndPermission.getPerm());
            newRoleAndPermission.setRemark(roleAndPermission.getRemark());
            roleAndPermissions.add(newRoleAndPermission);
        }
        if (isAssistantManagerOn()) {
            RoleAndPermission newRoleAndPermission = new RoleAndPermission();
            newRoleAndPermission.setRole(RoleEnum.ASSISTANT_MANAGER.getRole());
            newRoleAndPermission.setPerm(roleAndPermission.getPerm());
            newRoleAndPermission.setRemark(roleAndPermission.getRemark());
            roleAndPermissions.add(newRoleAndPermission);
        }
        if (isAssistantMasterOn()) {
            RoleAndPermission newRoleAndPermission = new RoleAndPermission();
            newRoleAndPermission.setRole(RoleEnum.ASSISTANT_MASTER.getRole());
            newRoleAndPermission.setPerm(roleAndPermission.getPerm());
            newRoleAndPermission.setRemark(roleAndPermission.getRemark());
            roleAndPermissions.add(newRoleAndPermission);
        }
        if (isAssistantOn()) {
            RoleAndPermission newRoleAndPermission = new RoleAndPermission();
            newRoleAndPermission.setRole(RoleEnum.ASSISTANT.getRole());
            newRoleAndPermission.setPerm(roleAndPermission.getPerm());
            newRoleAndPermission.setRemark(roleAndPermission.getRemark());
            roleAndPermissions.add(newRoleAndPermission);
        }
        if (isTeacherOn()) {
            RoleAndPermission newRoleAndPermission = new RoleAndPermission();
            newRoleAndPermission.setRole(RoleEnum.TEACHER.getRole());
            newRoleAndPermission.setPerm(roleAndPermission.getPerm());
            newRoleAndPermission.setRemark(roleAndPermission.getRemark());
            roleAndPermissions.add(newRoleAndPermission);
        }
        if (isGuestOn()) {
            RoleAndPermission newRoleAndPermission = new RoleAndPermission();
            newRoleAndPermission.setRole(RoleEnum.GUEST.getRole());
            newRoleAndPermission.setPerm(roleAndPermission.getPerm());
            newRoleAndPermission.setRemark(roleAndPermission.getRemark());
            roleAndPermissions.add(newRoleAndPermission);
        }

        return roleAndPermissions;
    }
}
