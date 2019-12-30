package com.jzy.model.vo;

import com.jzy.manager.constant.RedisConstants;
import com.jzy.model.entity.User;
import lombok.Data;

import java.io.Serializable;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UserLoginResult
 * @description 用户登录请求的结果信息的封装
 * @date 2019/9/7 9:19
 **/
@Data
public class UserLoginResult implements Serializable {
    private static final long serialVersionUID = 3347183837879977277L;

    //默认连续输错多少次，触发
    public static final int DEFAULT_WRONG_TIMES = 5;

    //默认连续输错达到阈值次数后，触发的下次可尝试登录的操作时间间隔，单位：分钟
    public static final int DEFAULT_BASE_DELAY_TIME = 15;

    //是否登录成功标志
    private boolean success;

    //是否验证码错误
    private boolean imgCodeWrong;

    //是否结果是用户名不存在
    private boolean unknownAccount;

    //是否结果是密码错误
    private boolean passwordWrong;

    //用户是否冻结标志
    private boolean locked;

    //还剩多少时间可重新尝试登录
    private long timeRemaining;

    //单次登录失败的次数，上限默认为5次
    private int wrongTimes;

    //如果成功，设置当前在User表中查询到的user信息
    private User user;

    /**
     * 设置登录失败的次数为默认次数
     */
    public void setDefaultWrongTimes(){
        this.wrongTimes=DEFAULT_WRONG_TIMES;
    }

    /**
     * 获取当前账户redis冻结缓存的键
     *
     * @param userId 用户id
     * @return 缓存的键
     */
    public static String getUserLoginFailKey(String userId) {
        return RedisConstants.USER_LOGIN_FAIL_KEY + ":" + userId;
    }
}
