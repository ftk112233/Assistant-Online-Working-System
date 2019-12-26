package com.jzy.service.impl;

import com.jzy.service.RedisOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName RedisOperationImpl
 * @description redis操作的封装
 * @date 2019/12/21 9:32
 **/
@Service
public class RedisOperationImpl implements RedisOperation {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private HashOperations<String, String, Object> hashOps;

    @Autowired
    private ValueOperations<String, Object> valueOps;

    @Autowired
    private ListOperations<String, Object> listOps;

    @Autowired
    private SetOperations<String, Object> setOps;

    @Autowired
    private ZSetOperations<String, Object> zSetOps;

    public void expireKey(String key) {
        if (!StringUtils.isEmpty(key)) {
            redisTemplate.expire(key, 0, TimeUnit.SECONDS);
        }
    }

    public void deleteHashByKey(String key, String hashKey) {
        if (hashOps.hasKey(key, hashKey)) {
            //缓存中有
            hashOps.delete(key, hashKey);
        }
    }
}
