package com.jzy.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName Assistant
 * @description 助教实体类
 * @date 2019/11/13 12:26
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Assistant extends BaseEntity {
    private static final long serialVersionUID = -1476804081658606114L;

    /**
     * 助教工号
     */
    private String assistWorkId;

    /**
     * 助教姓名
     */
    private String assistantName;

    /**
     * 性别
     */
    private String assistantSex;

    /**
     * 助教所在部门
     */
    private String assistantDepart;

    /**
     * 助教所在校区
     */
    private String assistantCampus;

    /**
     * 助教手机号
     */
    private String assistantPhone;

    /**
     * 助教备注
     */
    private String assistantRemark;

    public Assistant() {
    }

    public Assistant(String assistWorkId) {
        this.assistWorkId = assistWorkId;
    }
}
