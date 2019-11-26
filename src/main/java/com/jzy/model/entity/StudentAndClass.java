package com.jzy.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName StudentAndClass
 * @description 学生和班级多对多关系的中间表的实体类映射
 * @date 2019/11/13 14:35
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class StudentAndClass extends BaseEntity {
    private static final long serialVersionUID = 6002248319339678810L;

    /**
     * 上课学生的主键id
     */
    private Long studentId;

    /**
     * 上课班级的主键id
     */
    private Long classId;

    /**
     * 学生报名进班时间，可以为空
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date registerTime;

    /**
     * 备注，可以为空，但长度不超过500
     */
    private String remark;
}
