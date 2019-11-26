package com.jzy.dao;

import com.jzy.BaseTest;
import com.jzy.model.dto.ClassSearchCondition;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ClassMapperTest extends BaseTest {
    @Autowired
    private ClassMapper classMapper;

    @Test
    public void listClasses() {
        System.out.println("_____"+classMapper.listClasses(new ClassSearchCondition()));
    }
}