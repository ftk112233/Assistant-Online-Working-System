package com.jzy.service;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName RedisOperation
 * @description redis操作的封装
 * @date 2019/12/21 9:31
 **/
public interface RedisOperation {
    /**
     * 清除redis指定key的缓存
     *
     * @param key redis缓存的键
     */
    void expireKey(String key);

    /**
     * 清除redis指定key的hash
     *
     * @param key redis缓存的键
     */
    void deleteHashByKey(String key, String hashKey);
}
