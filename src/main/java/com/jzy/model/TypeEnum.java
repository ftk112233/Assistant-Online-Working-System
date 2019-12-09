package com.jzy.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName TypeEnum
 * @description 班型枚举
 * @date 2019/12/1 13:13
 **/
public enum  TypeEnum {
    HAO_XUE("好学", "A", 0), JING_JIN("精进", "B", 1), ZHI_GAO("志高", "C", 2), XIN_YUAN("行远", "D", 3), ZHUANG_ZHI("壮志", "E", 4), LING_YUN("凌云", "F", 5)
    ,XING_YAO("星耀", "G", 6), ZHUAN_XIANG("专项", "Z", 7), XU_NI("虚拟", "X", 8);

    private String type;

    /**
     * 班号中对应的代码，与名称一一对应
     */
    private String code;

    /**
     * 权重，自定义排序时用
     */
    private int weight;

    public int getWeight() {
        return weight;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    TypeEnum(String type, String code, int weight) {
        this.type=type;
        this.code=code;
        this.weight=weight;
    }

    /**
     * 获取所有班型列表
     *
     * @return
     */
    public static List<String> getTypesList() {
        List<String> list = new ArrayList<>();
        for (TypeEnum typeEnum : TypeEnum.values()) {
            list.add(typeEnum.getType());
        }
        return list;
    }

    /**
     * 判断当前输入班型字串是否存在
     *
     * @param type 输入班型字串
     * @return
     */
    public static boolean hasType(String type) {
        for (TypeEnum typeEnum : TypeEnum.values()) {
            if (typeEnum.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断当前枚举是否与班型（字符串）相同
     *
     * @param type 字符串类型的班型
     * @return
     */
    public boolean equals(String type){
        if (this.getType().equals(type)){
            return true;
        }
        return false;
    }

    /**
     * 根据输入班型的字符串找到对应枚举对象
     *
     * @param type 输入班型字串
     * @return 枚举对象
     */
    public static TypeEnum getTypeEnumByType(String type) {
        for (TypeEnum typeEnum : TypeEnum.values()) {
            if (typeEnum.getType().equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }

    /**
     * 根据班型名称返回权重
     *
     * @param type 班型
     * @return
     */
    public static int getWeightByType(String type){
        TypeEnum typeEnum=getTypeEnumByType(type);
        if (typeEnum != null) {
            return typeEnum.getWeight();
        }
        return -1;
    }

    /**
     * 根据班型代码返回枚举
     *
     * @param code 班型代码
     * @return
     */
    public static TypeEnum getTypeEnumByCode(String code){
        for (TypeEnum typeEnum : TypeEnum.values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum;
            }
        }
        return null;
    }

    /**
     * 根据班型代码返回班型名称
     *
     * @param code 班型代码
     * @return
     */
    public static String getTypeByCode(String code){
        TypeEnum typeEnum=getTypeEnumByCode(code);
        if (typeEnum != null) {
            return typeEnum.getType();
        }
        return null;
    }
}
