package com.jzy.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName Speed
 * @description 数据处理速度的封装
 * @date 2019/12/2 16:01
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Speed extends Time {
    private static final long serialVersionUID = -6511341868845659672L;

    protected static String COUNT_UNIT = "条";

    protected static String SEPARATOR = "/";

    protected static String PER_HOUR = COUNT_UNIT + SEPARATOR + HOUR;

    protected static String PER_MINUTE = COUNT_UNIT + SEPARATOR + MINUTE;

    protected static String PER_SECOND = COUNT_UNIT + SEPARATOR + SECOND;

    /**
     * 处理的数据总数
     */
    protected long count;

    /**
     * 每秒执行多少条
     */
    private long speedPerSecond;

    /**
     * 每分钟执行多少条
     */
    private long speedPerMinute;

    /**
     * 每小时执行多少条
     */
    private long speedPerHour;

    /**
     * xx条/分、xx条/秒、....
     */
    private String parsedSpeed;

    @Override
    public String parseSpeed() {
        super.parseTime();
        if (count > 0) {
            if (totalHour > 0) {
                speedPerHour = (long) (count / totalHour);
            }
            if (totalMinute > 0) {
                speedPerMinute = (long) (count / totalMinute);
            }
            if (totalSecond > 0) {
                speedPerSecond = (long) (count / totalSecond);
            }

            if (totalHour >= 2) {
                //小时级别耗时，大于等于两小时，输出xx条/小时
                parsedSpeed = speedPerHour + PER_HOUR;
                return parsedSpeed;
            }

            if (totalMinute >= 2) {
                //分钟级别耗时，大于等于两分钟，输出xx条/分钟
                parsedSpeed = speedPerMinute + PER_MINUTE;
                return parsedSpeed;
            }

            //秒级别耗时，大于等于两秒，输出xx条/秒
            parsedSpeed = speedPerSecond + PER_SECOND;
        } else {
            parsedSpeed = 0 + PER_SECOND;
        }
        return parsedSpeed;
    }

    public Speed(long count, long milliSecond) {
        super(milliSecond);
        this.count = count;
    }

}
