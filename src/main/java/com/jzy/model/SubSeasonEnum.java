package com.jzy.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName SubSeasonEnum
 * @description 分期枚举
 * @date 2019/12/10 11:21
 **/
public enum  SubSeasonEnum {
    FIRST("一期"), SECOND("二期"), THIRD("三期"), FORTH("四期"), FIFTH("五期"), SIXTH("六期");

    private String subSeason;

    public String getSubSeason() {
        return subSeason;
    }

    SubSeasonEnum(String subSeason) {
        this.subSeason=subSeason;
    }

    /**
     * 获取所有分期的列表
     *
     * @return
     */
    public static List<String> getSubSeasonsList() {
        List<String> list = new ArrayList<>();
        for (SubSeasonEnum subSeasonEnum : SubSeasonEnum.values()) {
            list.add(subSeasonEnum.getSubSeason());
        }
        return list;
    }

    /**
     * 判断当前输入季度分期字串是否存在
     *
     * @param subSeason 输入季度分期
     * @return
     */
    public static boolean hasSubSeason(String subSeason) {
        for (SubSeasonEnum subSeasonEnum : SubSeasonEnum.values()) {
            if (subSeasonEnum.getSubSeason().equals(subSeason)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断当前季度分期枚举是否与季度分期（字符串）相同
     *
     * @param subSeason 字符串类型的季度分期
     * @return
     */
    public boolean equals(String subSeason){
        if (this.getSubSeason().equals(subSeason)){
            return true;
        }
        return false;
    }
}
