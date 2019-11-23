package com.jzy.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @ClassName RoleAndPermission
 * @Author JinZhiyun
 * @Description 角色和权限实体类
 * @Date 2019/11/21 13:19
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class RoleAndPermission extends BaseEntity{
    private static final long serialVersionUID = -6658549431147072767L;

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
