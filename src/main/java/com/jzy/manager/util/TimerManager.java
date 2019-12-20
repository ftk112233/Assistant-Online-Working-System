package com.jzy.manager.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName TimeManager
 * @description 定时任务管理器
 * @date 2019/12/19 16:37
 **/
public class TimerManager {
    /**
     * 时间间隔(一天)
     */
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

    /**
     * 默认的任务执行时间凌晨两点
     */
    private static final int TASK_HOUR = 2;
    private static final int TASK_MINUTE = 0;
    private static final int TASK_SECOND = 0;

    private TimerManager() {
    }

    /**
     * 开启一个定时任务线程
     *
     * @param task   任务
     * @param period 定时间隔周期
     * @param hour   第一次执行时刻的小时
     * @param minute 第一次执行时刻的分钟
     * @param second 第一次执行时刻的秒
     */
    public static void startTask(TimerTask task, long period, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        Date date = calendar.getTime(); //第一次执行定时任务的时间
        //如果第一次执行定时任务的时间 小于当前的时间
        //此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
        if (date.before(new Date())) {
            date = addDay(date, (int) (period / PERIOD_DAY));
        }
        Timer timer = new Timer();
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。
        timer.schedule(task, date, period);
    }

    /**
     * 开启一个每天执行一次的定时任务线程
     *
     * @param task   任务
     * @param hour   第一次执行时刻的小时
     * @param minute 第一次执行时刻的分钟
     * @param second 第一次执行时刻的秒
     */
    public static void startDailyTask(TimerTask task, int hour, int minute, int second) {
        startTask(task, PERIOD_DAY, hour, minute, second);
    }

    /**
     * 开启一个每天执行一次的定时任务线程，默认执行时间每天凌晨两点
     *
     * @param task
     */
    public static void startDailyTask(TimerTask task) {
        startDailyTask(task, TASK_HOUR, TASK_MINUTE, TASK_SECOND);
    }

    /**
     * 第一次执行定时任务的时间加num天，以便此任务在下个时间点执行。如果不加num天，任务会立即执行。
     *
     * @param date 第一次执行定时任务的时间
     * @param num  下一次执行任务增加的天数
     * @return
     */
    private static Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }

    public static void main(String[] args) {
        startTask(new  TimerTask(){
            public void run() {
                System.out.println("我有一头小毛驴!");
            }
        },2000, 16,52,0);
    }

}
