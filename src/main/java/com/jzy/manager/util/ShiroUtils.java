package com.jzy.manager.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.util.ByteSource;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName ShiroUtils
 * @description shiro工具类
 * @date 2019/11/15 15:46
 **/
public class ShiroUtils {
    private ShiroUtils() {
    }

    /**
     * shiro记住我cookie有效时间2小时
     */
    public static final int SHIRO_REMEMBER_ME_COOKIE_TIME = 3600 * 2;

    /**
     * hash算法迭代次数
     */
    public static final int HASH_ITERATIONS =5;

    /**
     * 免密登录时用的明文
     */
    public static final String FINAL_PASSWORD_PLAINTEXT="123456";

    /**
     * 免密登录时用的盐值
     */
    public static final String FINAL_PASSWORD_SALT="654321";

    /**
     * 获得shrio的session
     *
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }


    /**
     * 免密登录时用的密文
     */
    public static final String FINAL_PASSWORD_CIPHERTEXT=encryptUserPassword(FINAL_PASSWORD_PLAINTEXT, FINAL_PASSWORD_SALT);


    /**
     * md5加密，加密内容source,带盐加密salt，指定加密次数：HASH_ITERATIONS
     *
     * @param source 明文
     * @param salt 盐
     * @return
     */
    public static String encryptUserPassword(String source, String salt){
        return new Md5Hash(source, ByteSource.Util.bytes(salt), HASH_ITERATIONS).toHex();
    }
}
