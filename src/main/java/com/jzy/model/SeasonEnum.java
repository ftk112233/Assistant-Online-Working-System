package com.jzy.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @EnumName SeasonEnum
 * @description 季度的枚举
 * @date 2019/12/1 9:55
 **/
public enum SeasonEnum {
    SUMMER("暑假", "A"), AUTUMN_1("秋上", "B"),AUTUMN_2("秋下", "C"), WINTER("寒假", "D"), SPRING("春季", "H");

    private String season;

    /**
     * 班号中对应的代码，与名称一一对应
     */
    private String code;

    public String getCode() {
        return code;
    }

    public String getSeason() {
        return season;
    }

    SeasonEnum(String season, String code) {
        this.season=season;
        this.code=code;
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
    public static boolean hasRole(String season) {
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
    public boolean equals(String season){
        if (this.getSeason().equals(season)){
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
    public static SeasonEnum getSeasonEnumByCode(String code){
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
    public static String getSeasonByCode(String code){
        SeasonEnum seasonEnum=getSeasonEnumByCode(code);
        if (seasonEnum != null) {
            return seasonEnum.getSeason();
        }
        return null;
    }
}
