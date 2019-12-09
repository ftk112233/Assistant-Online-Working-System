package com.jzy.model.dto.echarts;

import com.jzy.model.TypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName GroupedByTypeObjectTotal
 * @description 班型和对应人数的封装
 * @date 2019/12/4 16:44
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class GroupedByTypeObjectTotal extends DefaultGroupedObjectTotal{
    private static final long serialVersionUID = 1298791405053259980L;

    /**
     * 对name重写排序规则，好学<精进<志高<行远<壮志<凌云<星耀<专项<虚拟
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(DefaultGroupedObjectTotal o) {
        if (o != null) {
            return TypeEnum.getWeightByType(this.getName())-TypeEnum.getWeightByType(o.getName());
        }
        return super.compareTo(o);
    }
}
