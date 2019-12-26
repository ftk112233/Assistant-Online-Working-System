package com.jzy.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @EnumName SeasonEnum
 * @description 季度的枚举
 * @date 2019/12/1 9:55
 **/
public enum SeasonEnum {
    /**
     * 季度枚举——暑假，对应班号中的代码为"A"，在排序中权重为10
     */
    SUMMER("暑假", "A", 10),

    /**
     * 季度枚举——秋上，对应班号中的代码为"B"，在排序中权重为15
     */
    AUTUMN_1("秋上", "B", 15),

    /**
     * 季度枚举——秋下，对应班号中的代码为"C"，在排序中权重为20
     */
    AUTUMN_2("秋下", "C", 20),

    /**
     * 季度枚举——寒假，对应班号中的代码为"D"，在排序中权重为0，即最小
     */
    WINTER("寒假", "D", 0),

    /**
     * 季度枚举——春季，对应班号中的代码为"H"，在排序中权重为5
     */
    SPRING("春季", "H", 5);

    public static final Comparator<String> SEASON_COMPARATOR = new SeasonComparator();

    /**
     * 自定义的季度比较器(寒假< 春季< 暑假< 秋季)
     */
    private static class SeasonComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return SeasonEnum.getTimeWeightBySeason(o1) - SeasonEnum.getTimeWeightBySeason(o2);
        }
    }

    private String season;

    /**
     * 班号中对应的代码，与名称一一对应
     */
    private String code;

    /**
     * 时间上排序的权重，寒假<春季<暑假<秋季
     */
    private int timeWeight;

    public String getCode() {
        return code;
    }

    public String getSeason() {
        return season;
    }

    public int getTimeWeight() {
        return timeWeight;
    }

    SeasonEnum(String season, String code, int timeWeight) {
        this.season = season;
        this.code = code;
        this.timeWeight = timeWeight;
    }

    /**
     * 获取所有季度列表
     *
     * @return
     */
    public static List<String> getSeasonsList() {
        List<String> list = new ArrayList<>();
        for (SeasonEnum seasonEnum : SeasonEnum.values()) {
            list.add(seasonEnum.getSeason());
        }
        return list;
    }

    /**
     * 判断当前输入季度字串是否存在
     *
     * @param season 输入季度字串
     * @return
     */
    public static boolean hasSeason(String season) {
        for (SeasonEnum seasonEnum : SeasonEnum.values()) {
            if (seasonEnum.getSeason().equals(season)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断当前枚举是否与季度（字符串）相同
     *
     * @param season 字符串类型的季度
     * @return
     */
    public boolean equals(String season) {
        if (this.getSeason().equals(season)) {
            return true;
        }
        return false;
    }

    /**
     * 根据季度代码返回枚举
     *
     * @param code 季度代码
     * @return
     */
    public static SeasonEnum getSeasonEnumByCode(String code) {
        for (SeasonEnum seasonEnum : SeasonEnum.values()) {
            if (seasonEnum.getCode().equals(code)) {
                return seasonEnum;
            }
        }
        return null;
    }

    /**
     * 根据季度代码返回季度名称
     *
     * @param code 季度代码
     * @return
     */
    public static String getSeasonByCode(String code) {
        SeasonEnum seasonEnum = getSeasonEnumByCode(code);
        if (seasonEnum != null) {
            return seasonEnum.getSeason();
        }
        return null;
    }


    /**
     * 根据季度名称返回季度枚举
     *
     * @param season 季度
     * @return
     */
    public static SeasonEnum getSeasonEnumBySeason(String season) {
        for (SeasonEnum seasonEnum : SeasonEnum.values()) {
            if (seasonEnum.getSeason().equals(season)) {
                return seasonEnum;
            }
        }
        return null;
    }

    /**
     * 根据季度名称返回季度的时间权重
     *
     * @param season 季度
     * @return
     */
    public static int getTimeWeightBySeason(String season) {
        SeasonEnum seasonEnum = getSeasonEnumBySeason(season);
        if (seasonEnum != null) {
            return seasonEnum.getTimeWeight();
        }
        return -1;
    }
}
