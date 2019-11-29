package com.jzy.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @ClassName CampusAndClassroom
 * @Author JinZhiyun
 * @Description 校区及教室实体类
 * @Date 2019/11/28 10:57
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class CampusAndClassroom extends BaseEntity {
    private static final long serialVersionUID = -5954767444219495513L;

    /**
     * 校区
     */
    private String campus;

    /**
     * 教室
     */
    private String classroom;

    /**
     * 备注
     */
    private String remark;

    public CampusAndClassroom(String campus, String classroom) {
        this.campus = campus;
        this.classroom = classroom;
    }

    public CampusAndClassroom() {
    }
}
