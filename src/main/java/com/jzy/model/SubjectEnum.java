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
    CHINESE("语文","Y", 0), MATH("数学", "M", 1), ENGLISH("英语", "E", 2), PHYSICS("物理" , "P", 3), CHEMISTRY("化学", "C", 4), GEOGRAPHY("地理", "G", 5)
    , BIOLOGY("生物", "B", 6), HISTORY("历史", "H", 7), SCIENCE("科学", "S", 8), LIAN_BAO("联报", "L", 9);

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
