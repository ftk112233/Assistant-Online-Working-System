package com.jzy.manager.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName CodeUtils
 * @description 所用验证码服务的工具类
 * @date 2019/11/14 23:19
 **/
public class CodeUtils {
    private CodeUtils() {
    }

    /**
     * 判断用户输入的验证码与实际是否相同
     *
     * @param input 输入的验证码
     * @param code  实际的验证码
     * @return
     */
    public static boolean equals(String input, String code) {
        if (StringUtils.isEmpty(input) && StringUtils.isEmpty(code)){
            return true;
        } else{
            if (StringUtils.isEmpty(input) || StringUtils.isEmpty(code)){
                return false;
            }
        }
        return input.trim().toLowerCase().equals(code.toLowerCase());
    }

    /**
     * 生成6位随机数
     *
     * @return
     */
    public static String randomCodes() {
        return randomCodes(6);
    }


    /**
     * 生成n位随机数
     *
     * @return
     */
    public static String randomCodes(int len) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < len; i++) {

            int r = random.nextInt(10); //每次随机出一个数字（0-9）

            code.append(r);  //把每次随机出的数字拼在一起

        }
        return code.toString();
    }

    /**
     * 返回start~end间的随机整数
     *
     * @param start 开始（含）
     * @param end   结束（含）
     * @return
     */
    public static Integer oneRandomNumber(int start, int end) {
        Random random = new Random();
        int r = random.nextInt(end - start + 1) + start; //每次随机出一个数字（1-3）
        return r;
    }
}
