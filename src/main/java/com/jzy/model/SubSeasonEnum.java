package com.jzy.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName SubSeasonEnum
 * @description 分期枚举
 * @date 2019/12/10 11:21
 **/
public enum  SubSeasonEnum {
    FIRST("一期", 1), SECOND("二期", 2), THIRD("三期", 3), FORTH("四期", 4), FIFTH("五期", 5), SIXTH("六期", 6);

    public static final Comparator<String> SUB_SEASON_COMPARATOR =new SubSeasonEnumComparator();
    /**
     * 自定义的季度分期比较器(一期<二期<三期.....)
     */
    private static class SubSeasonEnumComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return SubSeasonEnum.getTimeWeightBySubSeason(o1)-SubSeasonEnum.getTimeWeightBySubSeason(o2);
        }
    }

    private String subSeason;

    /**
     * 时间上排序的权重，一期<二期<三期.....
     */
    private int timeWeight;

    public String getSubSeason() {
        return subSeason;
    }

    public int getTimeWeight() {
        return timeWeight;
    }

    SubSeasonEnum(String subSeason, int timeWeight) {
        this.subSeason=subSeason;
        this.timeWeight=timeWeight;
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


    /**
     * 根据季度分期名称返回季度枚举
     *
     * @param subSeason 分期
     * @return
     */
    public static SubSeasonEnum getSubSeasonEnumBySubSeason(String subSeason){
        for (SubSeasonEnum seasonEnum : SubSeasonEnum.values()) {
            if (seasonEnum.getSubSeason().equals(subSeason)) {
                return seasonEnum;
            }
        }
        return null;
    }

    /**
     * 根据季度分期名称返回季度分期的时间权重
     *
     * @param subSeason 分期
     * @return
     */
    public static int getTimeWeightBySubSeason(String subSeason){
        SubSeasonEnum seasonEnum=getSubSeasonEnumBySubSeason(subSeason);
        if (seasonEnum != null) {
            return seasonEnum.getTimeWeight();
        }
        return -1;
    }
}
