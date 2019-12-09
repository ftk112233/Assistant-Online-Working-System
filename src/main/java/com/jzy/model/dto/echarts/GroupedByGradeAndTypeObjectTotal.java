package com.jzy.model.dto.echarts;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName GroupedByGradeAndTypeObjectTotal
 * @description 年级和对应人数，以及该年级下各班型对应人数的封装
 * @date 2019/12/4 18:59
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class GroupedByGradeAndTypeObjectTotal extends GroupedByGradeObjectTotal{
    private static final long serialVersionUID = -4702031345559830825L;

    /**
     * <精进, xxx人>、<志高, xxx人>、......
     */
    private List<GroupedByTypeObjectTotal> groupedByTypeObjectTotals=new ArrayList<>();

    public GroupedByGradeAndTypeObjectTotal(DefaultGroupedObjectTotal objectTotal, List<GroupedByTypeObjectTotal> groupedByTypeObjectTotals) {
        if (objectTotal != null) {
            this.name=objectTotal.getName();
            this.value=objectTotal.getValue();
        }
        this.groupedByTypeObjectTotals=groupedByTypeObjectTotals;
    }
}
