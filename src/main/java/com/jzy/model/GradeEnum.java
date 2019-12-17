package com.jzy.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName GradeEnum
 * @description 年级枚举类
 * @date 2019/12/1 12:55
 **/
public enum  GradeEnum {
    SIXTH("小初衔接", "6", 0), SEVENTH("初一", "7", 1), EIGHTH("初二", "8", 2), NINTH("中考", "9", 3), TENTH("高一", "A", 4), ELEVENTH("高二", "B", 5), TWELFTH("高考", "C", 6);

    /**
     * 年级名称
     */
    private String grade;

    /**
     * 班号中对应的代码，与名称一一对应
     */
    private String code;

    /**
     * 权重，自定义排序时用
     */
    private int weight;

    public String getGrade() {
        return grade;
    }

    public String getCode() {
        return code;
    }

    public int getWeight() {
        return weight;
    }

    GradeEnum(String grade, String code, int weight) {

        this.grade=grade;
        this.code=code;
        this.weight=weight;
    }


    public static final Comparator<String> GRADE_COMPARATOR =new GradeComparator();
    /**
     * 自定义的年级比较器(小初衔接<初一<初二<中考<高一<高二<高考)
     */
    private static class GradeComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return GradeEnum.getWeightByGrade(o1)-GradeEnum.getWeightByGrade(o2);
        }
    }

    /**
     * 获取所有年级列表
     *
     * @return
     */
    public static List<String> getGradesList() {
        List<String> list = new ArrayList<>();
        for (GradeEnum gradeEnum : GradeEnum.values()) {
            list.add(gradeEnum.getGrade());
        }
        return list;
    }

    /**
     * 判断当前输入年级字串是否存在
     *
     * @param grade 输入年级字串
     * @return
     */
    public static boolean hasGrade(String grade) {
        for (GradeEnum gradeEnum : GradeEnum.values()) {
            if (gradeEnum.getGrade().equals(grade)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断当前枚举是否与年级（字符串）相同
     *
     * @param grade 字符串类型的年级
     * @return
     */
    public boolean equals(String grade){
        if (this.getGrade().equals(grade)){
            return true;
        }
        return false;
    }

    /**
     * 根据输入年级的字符串找到对应枚举对象
     *
     * @param grade 输入年级字串
     * @return 枚举对象
     */
    public static GradeEnum getGradeEnumByGrade(String grade) {
        for (GradeEnum gradeEnum : GradeEnum.values()) {
            if (gradeEnum.getGrade().equals(grade)) {
                return gradeEnum;
            }
        }
        return null;
    }

    /**
     * 根据年级名称返回权重
     *
     * @param grade 年级
     * @return
     */
    public static int getWeightByGrade(String grade){
        GradeEnum gradeEnum=getGradeEnumByGrade(grade);
        if (gradeEnum != null) {
            return gradeEnum.getWeight();
        }
        return -1;
    }

    /**
     * 根据年级代码返回枚举
     *
     * @param code 年级代码
     * @return
     */
    public static GradeEnum getGradeEnumByCode(String code){
        for (GradeEnum gradeEnum : GradeEnum.values()) {
            if (gradeEnum.getCode().equals(code)) {
                return gradeEnum;
            }
        }
        return null;
    }

    /**
     * 根据年级代码返回年级名称
     *
     * @param code 年级代码
     * @return
     */
    public static String getGradeByCode(String code){
        GradeEnum gradeEnum=getGradeEnumByCode(code);
        if (gradeEnum != null) {
            return gradeEnum.getGrade();
        }
        return null;
    }
}
