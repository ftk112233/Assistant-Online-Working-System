package com.jzy.dao;

import com.jzy.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class AssistantMapperTest extends BaseTest {
    @Autowired
    private AssistantMapper assistantMapper;

    @Test
    public void getAssistantByWorkId() {
        System.out.println(assistantMapper.getAssistantByWorkId("12234"));
        System.out.println("==================");
    }
}