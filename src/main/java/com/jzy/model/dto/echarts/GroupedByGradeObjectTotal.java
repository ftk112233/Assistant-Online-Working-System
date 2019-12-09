package com.jzy.model.dto.echarts;

import com.jzy.model.GradeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName GroupedByGradeObjectTotal
 * @description 年级和对应人数的封装
 * @date 2019/12/4 15:11
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class GroupedByGradeObjectTotal extends DefaultGroupedObjectTotal {
    private static final long serialVersionUID = 191925517244302233L;

    /**
     * 对name重写排序规则，小初衔接<初一<初二<中考<高一<高二<高考
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(DefaultGroupedObjectTotal o) {
        if (o != null) {
            return GradeEnum.getWeightByGrade(this.getName())-GradeEnum.getWeightByGrade(o.getName());
        }
        return super.compareTo(o);
    }
}
