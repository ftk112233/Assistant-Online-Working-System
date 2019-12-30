package com.jzy.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName TypeEnum
 * @description 班型枚举
 * @date 2019/12/1 13:13
 **/
public enum  TypeEnum {
    /**
     * 班型枚举——好学，对应班号中的代码为"A"，在排序中权重为0，即最小
     */
    HAO_XUE("好学", "A", 0),

    /**
     * 班型枚举——精进，对应班号中的代码为"B"，在排序中权重为1
     */
    JING_JIN("精进", "B", 1),

    /**
     * 班型枚举——志高，对应班号中的代码为"C"，在排序中权重为2
     */
    ZHI_GAO("志高", "C", 2),

    /**
     * 班型枚举——行远，对应班号中的代码为"D"，在排序中权重为3
     */
    XIN_YUAN("行远", "D", 3),

    /**
     * 班型枚举——壮志，对应班号中的代码为"E"，在排序中权重为4
     */
    ZHUANG_ZHI("壮志", "E", 4),

    /**
     * 班型枚举——凌云，对应班号中的代码为"F"，在排序中权重为5
     */
    LING_YUN("凌云", "F", 5),

    /**
     * 班型枚举——星耀，对应班号中的代码为"G"，在排序中权重为6
     */
    XING_YAO("星耀", "G", 6),

    /**
     * 班型枚举——专项，对应班号中的代码为"Z"，在排序中权重为7
     */
    ZHUAN_XIANG("专项", "Z", 7),

    /**
     * 班型枚举——虚拟，对应班号中的代码为"X"，在排序中权重为8
     */
    XU_NI("虚拟", "X", 8);

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

    public static final Comparator<String> TYPE_COMPARATOR=new TypeComparator();
    /**
     * 自定义的年级比较器(小初衔接<初一<初二<中考<高一<高二<高考)
     */
    private static class TypeComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return TypeEnum.getWeightByType(o1) - TypeEnum.getWeightByType(o2);
        }
    }

    /**
     * 获取所有班型列表
     *
     * @return 所有班型的list
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
     * @return 是否存在的布尔值
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
     * @return 是否相同的布尔值
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
     * @return 班型的权重
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
     * @return 班型枚举对象
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
     * @return 班型名称字符串
     */
    public static String getTypeByCode(String code){
        TypeEnum typeEnum=getTypeEnumByCode(code);
        if (typeEnum != null) {
            return typeEnum.getType();
        }
        return null;
    }
}
