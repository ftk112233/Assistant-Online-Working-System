package com.jzy.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName CurrentClassSeason
 * @description 当前开课的年份-季度-分期
 * @date 2019/12/10 13:04
 **/
@Data
public class CurrentClassSeason implements Serializable {
    private static final long serialVersionUID = 5324664841478612380L;

    /**
     * 班级开设的年份，可以为空，2019版的班级编码中没有能标识年份的字段
     */
    private String classYear;

    /**
     * 班级开设的季度，如春季，暑假等，可以为空，长度不超过50
     */
    private String classSeason;

    /**
     * 班级开设的季度的分期，如（暑假）一期、二期等等
     */
    private String classSubSeason;

    public CurrentClassSeason() {
    }

    public CurrentClassSeason(String classYear, String classSeason, String classSubSeason) {
        this.classYear = classYear;
        this.classSeason = classSeason;
        this.classSubSeason = classSubSeason;
    }
}
