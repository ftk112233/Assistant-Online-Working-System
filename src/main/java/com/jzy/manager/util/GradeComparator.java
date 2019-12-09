package com.jzy.manager.util;

import com.jzy.model.GradeEnum;

import java.util.Comparator;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName GradeComparator
 * @description 自定义的年级比较器,小初衔接<初一<初二<中考<高一<高二<高考
 * @date 2019/12/4 20:49
 **/
public class GradeComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        return GradeEnum.getWeightByGrade(o1)-GradeEnum.getWeightByGrade(o2);
    }
}
