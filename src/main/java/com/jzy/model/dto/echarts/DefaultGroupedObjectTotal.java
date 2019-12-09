package com.jzy.model.dto.echarts;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author JinZhiyun
 * @ClassName ObjectTotalGroupByCommonName
 * @Description 封装类别对应名称及其下的人数传输对象
 * @Date 2019/7/16 8:42
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class DefaultGroupedObjectTotal extends AbstractObjectTotal implements Comparable<DefaultGroupedObjectTotal>{
    private static final long serialVersionUID = -8428242705961430212L;

    //该变量保存类别名称，对应该名称下的total数
    protected String name;

    @Override
    public int compareTo(DefaultGroupedObjectTotal o) {
        if (o != null) {
            return this.name.compareTo(o.getName());
        }
        return -1;
    }

    public DefaultGroupedObjectTotal() {
    }
}
