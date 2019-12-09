package com.jzy.manager.util;

import com.jzy.model.TypeEnum;

import java.util.Comparator;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName TypeComparator
 * @description 自定义的班型比较器, 好学<精进<志高<行远<壮志<凌云<星耀<专项<虚拟
 * @date 2019/12/4 20:49
 **/
public class TypeComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        return TypeEnum.getWeightByType(o1) - TypeEnum.getWeightByType(o2);
    }
}
