package com.jzy.model.dto.search;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JinZhiyun
 * @ClassName SearchCondition
 * @Description 查询的的条件
 * @Date 2019/7/25 9:12
 * @Version 1.0
 **/
@Data
public class SearchCondition implements Serializable {
    private static final long serialVersionUID = 8924463935049190517L;

    /**
     * 第一条件：类别
     */
    private String condition1;

    /**
     * 第二条件：升降序
     */
    private String condition2;
}
