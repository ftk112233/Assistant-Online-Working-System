package com.jzy.manager.constant;

import com.jzy.manager.util.MyTimeUtils;

import java.util.Date;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName RedisConstants
 * @description redis相关常量
 * @date 2019/11/15 21:55
 **/
public final class RedisConstants {
    private RedisConstants() {
    }

    /**
     * 根键
     */
    private static final String ROOT_KEY = "aows";

    /**
     * 用户缓存根键
     */
    private static final String USER_KEY = ROOT_KEY + ":user";

    public static final String USER_LOGIN_KEY = USER_KEY + ":login";

    /**
     * 用户上次登录成功ip地址缓存
     */
    public static final String USER_LOGIN_IP_KEY = USER_LOGIN_KEY + ":ip";

    /**
     * 用户登录失败错误次数缓存
     */
    public static final String USER_LOGIN_FAIL_KEY = USER_LOGIN_KEY + ":fail";

    /**
     * 用户的验证码根键
     */
    private static final String USER_VERIFYCODE_KEY = USER_KEY + ":verifyCode";

    /**
     * 邮箱验证码键
     */
    public static final String USER_VERIFYCODE_EMAIL_KEY = USER_VERIFYCODE_KEY + ":email";


    /**
     * 系统缓存根键
     */
    private static final String SYSTEM_KEY = ROOT_KEY + ":system";

    /**
     * 系统公告的键
     */
    public static final String ANNOUNCEMENT_SYSTEM_KEY = SYSTEM_KEY + ":announcement";

    /**
     * 校区-教室列表缓存的键
     */
    public static final String CLASSROOMS_KEY = ROOT_KEY + ":classroom";

    /**
     * 角色-权限列表缓存的键
     */
    public static final String ROLE_AND_PERMS_KEY = ROOT_KEY + ":roleAndPerms";

    /**
     * 常用信息的缓存的键
     */
    public static final String USEFUL_INFORMATION_KEY = ROOT_KEY + ":usefulInformation";

    /**
     * 常用信息的缓存的过期时间，21天
     */
    public static final long USEFUL_INFORMATION_EXPIRE = 21;

    /**
     * 用户登陆成功ip的缓存的过期时间，30天
     */
    public static final long USER_LOGIN_IP_EXPIRE = 30;

    /**
     * 当前季度的缓存的键
     */
    public static final String CURRENT_SEASON_KEY = ROOT_KEY + ":currentSeason";

    /**
     * 季度年份智能日历缓存的过期时间，180天
     */
    public static final long CURRENT_SEASON_EXPIRE = 180;

    /**
     * 小问题的缓存的键
     */
    public static final String QUESTION_KEY = ROOT_KEY + ":question";

    /**
     * 访客量统计的缓存的基键
     */
    private static final String VISITOR_STATISTICS_KEY = ROOT_KEY + ":visitorStatistic";

    /**
     * 访客量统计的缓存的过期时间，31天
     */
    public static final long VISITOR_STATISTICS_EXPIRE = 31;

    /**
     * 主页访客量统计的缓存的键
     */
    public static final String INDEX_VISITOR_STATISTICS_KEY = VISITOR_STATISTICS_KEY + ":index";

    /**
     * 获得今天的主页访客量统计的缓存的键
     *
     * @return 缓存的键
     */
    public static String getTodayIndexVisitorStatisticsKey() {
        return getIndexVisitorStatisticsKey(new Date());
    }

    /**
     * 获得指定date的主页访客量统计的缓存的键
     *
     * @param date date
     * @return 缓存的键
     */
    public static String getIndexVisitorStatisticsKey(Date date) {
        return INDEX_VISITOR_STATISTICS_KEY + ":" + MyTimeUtils.dateToStringYMD(date);
    }

    /**
     * 信息管理区访客量统计的缓存的键
     */
    public static final String INFO_MANAGEMENT_VISITOR_STATISTICS_KEY = VISITOR_STATISTICS_KEY + ":infoManagement";

    /**
     * 获得今天的信息管理区访客量统计的缓存的键
     *
     * @return 缓存的键
     */
    public static String getTodayInfoManagementVisitorStatisticsKey() {
        return getInfoManagementVisitorStatisticsKey(new Date());
    }

    /**
     * 获得指定date的信息管理区访客量统计的缓存的键
     *
     * @param date date
     * @return 缓存的键
     */
    public static String getInfoManagementVisitorStatisticsKey(Date date) {
        return INFO_MANAGEMENT_VISITOR_STATISTICS_KEY + ":" + MyTimeUtils.dateToStringYMD(date);
    }

    /**
     * 百宝箱区访客量统计的缓存的键
     */
    public static final String TOOLBOX_VISITOR_STATISTICS_KEY = VISITOR_STATISTICS_KEY + ":toolbox";

    /**
     * 获得今天的百宝箱区访客量统计的缓存的键
     *
     * @return 缓存的键
     */
    public static String getTodayToolboxVisitorStatisticsKey() {
        return getToolboxVisitorStatisticsKey(new Date());
    }

    /**
     * 获得指定date的百宝箱区访客量统计的缓存的键
     *
     * @param date date
     * @return 缓存的键
     */
    public static String getToolboxVisitorStatisticsKey(Date date) {
        return TOOLBOX_VISITOR_STATISTICS_KEY + ":" + MyTimeUtils.dateToStringYMD(date);
    }
}
