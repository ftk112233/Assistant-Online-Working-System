package com.jzy.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @ClassName RoleAndPermissionSearchCondition
 * @Author JinZhiyun
 * @Description 用户查询条件的封装
 * @Date 2019/11/21 16:18
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class RoleAndPermissionSearchCondition extends SearchCondition{
    private static final long serialVersionUID = 4338081881622506749L;

    /**
     * 角色
     */
    private String role;

    /**
     * 权限
     */
    private String perm;

    /**
     * 描述
     */
    private String remark;
}
