package com.jzy.model.vo;

import lombok.Data;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName ClassIdSearchResult
 * @description 根据classId下拉input搜索结果的封装
 * @date 2019/12/16 15:38
 **/
@Data
public class ClassIdSearchResult {
    private String classId;

    /**
     * 除班级id外显示的班级的说明
     */
    private String classGeneralName;

    public ClassIdSearchResult(String classId, String classGeneralName) {
        this.classId = classId;
        this.classGeneralName = classGeneralName;
    }

    public ClassIdSearchResult() {
    }
}
