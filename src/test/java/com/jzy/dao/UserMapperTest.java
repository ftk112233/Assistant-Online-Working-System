package com.jzy.dao;

import com.jzy.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserMapperTest extends BaseTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void getUserById() {
        System.out.println(userMapper.getUserById(3L));
    }

    @Test
    public void getUserByName() {
    }

    @Test
    public void getUserByWorkId() {
    }

    @Test
    public void getUserByIdCard() {
    }

    @Test
    public void getUserByEmail() {
    }

    @Test
    public void getUserByPhone() {
    }

    @Test
    public void updatePasswordByEmail() {
    }
}