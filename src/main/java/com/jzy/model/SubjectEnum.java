package com.jzy.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @EnumName SubjectEnum
 * @description 所有学科的枚举
 * @date 2019/12/1 13:06
 **/
public enum SubjectEnum {
    /**
     * 学科枚举——语文，对应班号中的代码为"Y"，在排序中权重为0，即最小
     */
    CHINESE("语文","Y", 0),

    /**
     * 学科枚举——数学，对应班号中的代码为"M"，在排序中权重为1
     */
    MATH("数学", "M", 1),

    /**
     * 学科枚举——英语，对应班号中的代码为"E"，在排序中权重为2
     */
    ENGLISH("英语", "E", 2),

    /**
     * 学科枚举——物理，对应班号中的代码为"P"，在排序中权重为3
     */
    PHYSICS("物理" , "P", 3),

    /**
     * 学科枚举——化学，对应班号中的代码为"C"，在排序中权重为4
     */
    CHEMISTRY("化学", "C", 4),

    /**
     * 学科枚举——地理，对应班号中的代码为"G"，在排序中权重为5
     */
    GEOGRAPHY("地理", "G", 5),

    /**
     * 学科枚举——生物，对应班号中的代码为"B"，在排序中权重为6
     */
    BIOLOGY("生物", "B", 6),

    /**
     * 学科枚举——历史，对应班号中的代码为"H"，在排序中权重为7
     */
    HISTORY("历史", "H", 7),

    /**
     * 学科枚举——科学，对应班号中的代码为"S"，在排序中权重为8
     */
    SCIENCE("科学", "S", 8),

    /**
     * 学科枚举——联报，对应班号中的代码为"L"，在排序中权重为9
     */
    LIAN_BAO("联报", "L", 9);

    private String subject;

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

    public String getCode() {
        return code;
    }

    public String getSubject() {
        return subject;
    }

    SubjectEnum(String subject, String code, int weight) {
        this.subject=subject;
        this.code=code;
        this.weight=weight;
    }

    /**
     * 获取所有学科列表
     *
     * @return
     */
    public static List<String> getSubjectsList() {
        List<String> list = new ArrayList<>();
        for (SubjectEnum subjectEnum : SubjectEnum.values()) {
            list.add(subjectEnum.getSubject());
        }
        return list;
    }

    /**
     * 判断当前输入学科字串是否存在
     *
     * @param subject 输入学科字串
     * @return
     */
    public static boolean hasSubject(String subject) {
        for (SubjectEnum subjectEnum : SubjectEnum.values()) {
            if (subjectEnum.getSubject().equals(subject)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断当前枚举是否与学科（字符串）相同
     *
     * @param subject 字符串类型的学科
     * @return
     */
    public boolean equals(String subject){
        if (this.getSubject().equals(subject)){
            return true;
        }
        return false;
    }

    /**
     * 根据输入学科的字符串找到对应枚举对象
     *
     * @param subject 输入学科字串
     * @return 枚举对象
     */
    public static SubjectEnum getSubjectEnumBySubject(String subject) {
        for (SubjectEnum subjectEnum : SubjectEnum.values()) {
            if (subjectEnum.getSubject().equals(subject)) {
                return subjectEnum;
            }
        }
        return null;
    }

    /**
     * 根据学科名称返回权重
     *
     * @param subject 学科
     * @return
     */
    public static int getWeightBySubject(String subject){
        SubjectEnum subjectEnum=getSubjectEnumBySubject(subject);
        if (subjectEnum != null) {
            return subjectEnum.getWeight();
        }
        return -1;
    }

    /**
     * 根据学科代码返回枚举
     *
     * @param code 学科代码
     * @return
     */
    public static SubjectEnum getSubjectEnumByCode(String code){
        for (SubjectEnum subjectEnum : SubjectEnum.values()) {
            if (subjectEnum.getCode().equals(code)) {
                return subjectEnum;
            }
        }
        return null;
    }

    /**
     * 根据学科代码返回学科名称
     *
     * @param code 学科代码
     * @return
     */
    public static String getSubjectByCode(String code){
        SubjectEnum subjectEnum=getSubjectEnumByCode(code);
        if (subjectEnum != null) {
            return subjectEnum.getSubject();
        }
        return null;
    }
}
