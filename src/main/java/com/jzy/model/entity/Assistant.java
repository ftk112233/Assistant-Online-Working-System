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
     * 助教工号，唯一，长度小于等于32可以为空
     */
    private String assistantWorkId;

    /**
     * 助教姓名，唯一，非空，不超过50个字符
     */
    private String assistantName;

    /**
     * 性别，可以为null
     */
    private String assistantSex;

    /**
     * 助教所在部门，可以为空，不超过20个字符
     */
    private String assistantDepart;

    /**
     * 助教所在校区，可以为空，不超过20个字符，且在所有枚举对象中{@link com.jzy.model.CampusEnum}
     */
    private String assistantCampus;

    /**
     * 助教手机号，空或者11位数字
     */
    private String assistantPhone;

    /**
     * 助教备注，空或者长度小于等于500
     */
    private String assistantRemark;

    public Assistant() {
    }

    public Assistant(String assistantWorkId) {
        this.assistantWorkId = assistantWorkId;
    }
}
