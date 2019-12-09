package com.jzy.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName Time
 * @description 计算程序执行时间、速度的模型类
 * @date 2019/12/2 15:42
 **/
@Data
public abstract class Time implements Serializable {
    private static final long serialVersionUID = 6565242925450400215L;

    protected static final String HOUR = "小时";
    protected static final String MINUTE = "分";
    protected static final String SECOND = "秒";

    /**
     * 毫秒时间值
     */
    protected long totalMilliSecond;

    /**
     * 把milliSecond毫秒转成：hour小时-minute分-second秒
     */
    private double second;
    private int minute;
    private long hour;

    /**
     * 总秒数: totalMilliSecond/1000
     */
    protected double totalSecond;

    /**
     * 总分钟数: totalMilliSecond/(1000*60)
     */
    protected double totalMinute;

    /**
     * 总小时数: totalMilliSecond/(1000*60*60)
     */
    protected double totalHour;

    /**
     * hour小时-minute分-second秒
     */
    private String parsedTime;

    /**
     * 保留三位小数
     *
     * @param d
     * @return
     */
    private static double format(double d){
        BigDecimal bg = new BigDecimal(d);
        return bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 把毫秒单位的执行时间转成xx小时xx分xx秒
     *
     * @return 返回字符串：xx小时xx分xx秒
     */
    public String parseTime() {
        if (totalMilliSecond > 0) {
            double totalSeconds = totalMilliSecond / 1000.0;

            hour = (long) (totalSeconds / (60 * 60));
            minute = (int) ((totalSeconds % (60 * 60)) / 60);
            second = totalSeconds % 60;

            if (hour > 0) {
                parsedTime = hour + HOUR + minute + MINUTE + (int)second + SECOND;
            } else if (minute > 0) {
                parsedTime = minute + MINUTE + (int)second + SECOND;
            } else {
                parsedTime = format(second) + SECOND;
            }

        }
        return parsedTime;
    }

    /**
     * 计算处理速度，交给子类实现
     *
     * @return xx条/分、xx条/秒、....
     */
    public abstract String parseSpeed();

    public Time() {
    }

    public Time(long totalMilliSecond) {
        this.totalMilliSecond = totalMilliSecond;
        this.totalSecond = totalMilliSecond / 1000.0;
        this.totalMinute = totalMilliSecond / (1000.0 * 60);
        this.totalHour = totalMilliSecond / (1000.0 * 60 * 60);
    }
}
