package com.jzy.model.vo;

import lombok.Data;

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
    private String role0;

    /**
     * 学管
     */
    private String role1;

    /**
     * 助教长
     */
    private String role2;

    /**
     * 助教
     */
    private String role3;

    /**
     * 教师
     */
    private String role4;

    /**
     * 游客
     */
    private String role5;

    public boolean isOnRole0(){
        return ON.equals(role0);
    }

    public boolean isOnRole1(){
        return ON.equals(role1);
    }

    public boolean isOnRole2(){
        return ON.equals(role2);
    }

    public boolean isOnRole3(){
        return ON.equals(role3);
    }

    public boolean isOnRole4(){
        return ON.equals(role4);
    }

    public boolean isOnRole5(){
        return ON.equals(role5);
    }
}
