package com.jzy.model;

import java.util.*;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName CampusEnum
 * @description 校区的枚举类，全市一共21个校区（不计算部分校区如曹杨有一校区和二小区），1.0版本目前通过数据表存储校区信息，
 * 因为校区的信息几乎不会增删改，通过枚举类的结构相较于数据表能够大大减少查询开销
 * @date 2019/10/31 21:43
 **/
public enum CampusEnum {
    CAO_YANG("曹杨"), XIN_ZHA("新闸"), JIANG_WAN("江湾"), WU_JIAO_CHANG("五角场"), NAN_DAN("南丹"),
    ZHONG_RONG("中融"), PING_YANG("平阳"), HU_TAI("沪太"), KONG_JIANG("控江"), JIN_QIAO("金桥"),
    QI_BAO("七宝"), TIAN_SHAN("天山"), GONG_KANG("共康"), TANG_QIAO("塘桥"), DA_HUA("大华"),
    XIN_SONG("莘松"), DA_LIAN("大连"), ZHAO_JIANG_BANG("肇嘉浜"), TANG_ZHENG("唐镇"), SAN_LING("三林"),
    YAO_HUA("耀华");


    private String name;

    CampusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * 校区总数
     */
    private static final int CAMPUS_COUNT = CampusEnum.values().length;

    /**
     * <校区编码，校区名称>的键值对
     */
    public static final Map<String, String> CAMPUSES = new HashMap<>(CAMPUS_COUNT);

    /**
     * <校区名称, 校区所有教室所组成的列表>的键值对
     * 改用数据库存储
     */
    @Deprecated
    public static final Map<String, List<String>> CAMPUS_CLASSROOMS = new HashMap<>(CAMPUS_COUNT);

    static {
        /**
         * 曹杨，一校区编码为"02"，二校区编码为"07"
         */
        CAMPUSES.put("02", CAO_YANG.getName());
        CAMPUSES.put("07", CAO_YANG.getName());
        CAMPUS_CLASSROOMS.put(CAO_YANG.getName(), Arrays.asList("301", "302", "306", "307", "308", "310", "311", "312", "313", "314", "315", "316", "318",
                "320", "321", "322", "323", "325", "201", "202", "203", "204", "205", "206", "207", "208"));

        //新闸
        CAMPUSES.put("03", XIN_ZHA.getName());
        CAMPUS_CLASSROOMS.put(XIN_ZHA.getName(), null);

        //江湾
        CAMPUSES.put("13", JIANG_WAN.getName());
        CAMPUS_CLASSROOMS.put(JIANG_WAN.getName(), null);

        //五角场
        CAMPUSES.put("22", WU_JIAO_CHANG.getName());
        CAMPUS_CLASSROOMS.put(WU_JIAO_CHANG.getName(), null);

        //南丹
        CAMPUSES.put("24", NAN_DAN.getName());
        CAMPUS_CLASSROOMS.put(NAN_DAN.getName(), null);

        //中融
        CAMPUSES.put("28", ZHONG_RONG.getName());
        CAMPUS_CLASSROOMS.put(ZHONG_RONG.getName(), null);

        //平阳
        CAMPUSES.put("33", PING_YANG.getName());
        CAMPUS_CLASSROOMS.put(PING_YANG.getName(), null);

        //沪太
        CAMPUSES.put("35", HU_TAI.getName());
        CAMPUS_CLASSROOMS.put(HU_TAI.getName(), null);

        //控江
        CAMPUSES.put("36", KONG_JIANG.getName());
        CAMPUS_CLASSROOMS.put(KONG_JIANG.getName(), null);

        //金桥
        CAMPUSES.put("39", JIN_QIAO.getName());
        CAMPUS_CLASSROOMS.put(JIN_QIAO.getName(), null);

        //七宝
        CAMPUSES.put("42", QI_BAO.getName());
        CAMPUS_CLASSROOMS.put(QI_BAO.getName(), null);

        //天山
        CAMPUSES.put("43", TIAN_SHAN.getName());
        CAMPUS_CLASSROOMS.put(TIAN_SHAN.getName(), null);

        //共康
        CAMPUSES.put("51", GONG_KANG.getName());
        CAMPUS_CLASSROOMS.put(GONG_KANG.getName(), null);

        //塘桥
        CAMPUSES.put("52", TANG_QIAO.getName());
        CAMPUS_CLASSROOMS.put(TANG_QIAO.getName(), null);

        //大华
        CAMPUSES.put("55", DA_HUA.getName());
        CAMPUS_CLASSROOMS.put(DA_HUA.getName(), null);

        //莘松
        CAMPUSES.put("57", XIN_SONG.getName());
        CAMPUS_CLASSROOMS.put(XIN_SONG.getName(), null);

        //大连
        CAMPUSES.put("58", DA_LIAN.getName());
        CAMPUS_CLASSROOMS.put(DA_LIAN.getName(), null);

        //肇嘉浜
        CAMPUSES.put("59", ZHAO_JIANG_BANG.getName());
        CAMPUS_CLASSROOMS.put(ZHAO_JIANG_BANG.getName(), null);

        //唐镇
        CAMPUSES.put("60", TANG_ZHENG.getName());
        CAMPUS_CLASSROOMS.put(TANG_ZHENG.getName(), null);

        //三林
        CAMPUSES.put("61", SAN_LING.getName());
        CAMPUS_CLASSROOMS.put(SAN_LING.getName(), null);

        //耀华
        CAMPUSES.put("15", YAO_HUA.getName());
        CAMPUS_CLASSROOMS.put(YAO_HUA.getName(), null);
    }

    /**
     * 获取当前校区名称的枚举类中所有校区名称字符串组成的列表
     *
     * @return ["曹杨","天山",.....]
     */
    public static List<String> getCampusNamesList() {
        List<String> list = new ArrayList<>();
        for (CampusEnum campusEnum : CampusEnum.values()) {
            list.add(campusEnum.getName());
        }
        return list;
    }

    /**
     * 判断当前输入校区名称字串是否存在
     *
     * @param campusNameStr 输入校区名称字串
     * @return
     */
    public static boolean hasCampusName(String campusNameStr) {
        for (CampusEnum campusEnum : CampusEnum.values()) {
            if (campusEnum.getName().equals(campusNameStr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据输入校区名的字符串找到对应枚举对象
     *
     * @param campusNameStr 输入校区名称字串
     * @return 枚举对象
     */
    public static CampusEnum getCampusEnumByNameString(String campusNameStr) {
        for (CampusEnum campusEnum : CampusEnum.values()) {
            if (campusEnum.getName().equals(campusNameStr)) {
                return campusEnum;
            }
        }
        return null;
    }

    /**
     *根据输入校区编码找到对应校区名称
     *
     * @param campusCode 校区编码
     * @return
     */
    public static String getCampusNameByCode(String campusCode){
        return CAMPUSES.get(campusCode);
    }

    /**
     * 根据输入校区编码找到对应校区枚举
     *
     * @param campusCode 校区编码
     * @return
     */
    public static CampusEnum getCampusEnumByCode(String campusCode){
        return getCampusEnumByNameString(CAMPUSES.get(campusCode));
    }

    /**
     * 根据输入的校区名称返回其所有的教师门牌号，改用数据库查询
     *
     * @param campusName 校区名称
     * @return
     */
    @Deprecated
    public static List<String> getClassroomsByCampusName(String campusName){
        return CAMPUS_CLASSROOMS.get(campusName);
    }
}
