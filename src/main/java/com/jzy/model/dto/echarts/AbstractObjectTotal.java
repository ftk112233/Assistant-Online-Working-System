package com.jzy.model.dto.echarts;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JinZhiyun
 * @ClassName AbstractObjectTotal
 * @Description 封装总数的基类，该类的子类类往往用于前端可视化分析的数据封装等
 * @Date 2019/7/15 11:09
 * @Version 1.0
 **/
@Data
public abstract class AbstractObjectTotal implements Serializable {
    private static final long serialVersionUID = 7165493447907360695L;
    
    //当前条件下记录总数
    protected Long value;
}
