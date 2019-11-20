package com.jzy.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ResultMap
 * @Author JinZhiyun
 * @Description 符合layui数据表格要求的返回JSON数据格式的传输对象
 * @Date 2019/4/15 16:53
 * @Version 1.0
 **/
@Data
public class ResultMap<T> implements Serializable {
    private static final long serialVersionUID = -2597989619687060507L;

    private String msg;
    private  T data;
    private  int code;
    private  int count;

    public ResultMap(int code, String msg, int count, T data) {
        this.msg = msg;
        this.data = data;
        this.code = code;
        this.count = count;
    }

    public ResultMap() {
    }
}