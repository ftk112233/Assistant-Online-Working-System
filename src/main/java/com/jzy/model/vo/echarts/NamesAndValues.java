package com.jzy.model.vo.echarts;

import com.jzy.model.dto.echarts.DefaultGroupedObjectTotal;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName NamesAndValues
 * @description 封装传给前台可视化分析的json
 * @date 2019/12/4 14:52
 **/
@Data
public class NamesAndValues implements Serializable {
    private static final long serialVersionUID = -4042406556362455429L;

    /**
     * 属性名称
     */
    protected List<String> names = new ArrayList<>();

    /**
     * 数值
     */
    protected List<Long> values = new ArrayList<>();

    /**
     * 把(name, total)的对象，添加到NamesAndTotals对象中
     *
     * @param objectTotal (类别, 数量)对象
     */
    public void add(DefaultGroupedObjectTotal objectTotal) {
        if (objectTotal != null) {
            names.add(objectTotal.getName());
            values.add(objectTotal.getValue());
        }
    }

    /**
     * 把(name, total)的对象列表，按对应泛型中自定义的排序规则添加到NamesAndTotals对象中。
     *
     * @param objectTotals 多个(类别, 数量)对象
     * @param <T>    DefaultGroupedObjectTotal及其子类
     */
    public <T extends DefaultGroupedObjectTotal> void addAll(List<T> objectTotals) {
        if (objectTotals != null) {
            //根据具体的排序的规则排序
            Collections.sort(objectTotals);
            for (T objectTotal : objectTotals) {
                add(objectTotal);
            }
        }
    }
}
