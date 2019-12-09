package com.jzy.model.dto.echarts;

import com.jzy.model.SubjectEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName GroupedBySubjectObjectTotal
 * @description 学科和对应人数的封装
 * @date 2019/12/4 16:03
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class GroupedBySubjectObjectTotal extends DefaultGroupedObjectTotal {
    private static final long serialVersionUID = -2041344958313348012L;

    /**
     * 对name重写排序规则，语文<数学<英语<物理<化学<地理<生物<化学<历史<科学<联报
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(DefaultGroupedObjectTotal o) {
        if (o != null) {
            return SubjectEnum.getWeightBySubject(this.getName())-SubjectEnum.getWeightBySubject(o.getName());
        }
        return super.compareTo(o);
    }
}
