package com.jzy.dao;

import com.jzy.BaseTest;
import com.jzy.model.dto.ClassSeasonDto;
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

    @Test
    public void listAssistantsByClassSeasonAndCampus() {
        ClassSeasonDto dto=new ClassSeasonDto("2020", "寒假", "一期");
        System.out.println(assistantMapper.listAssistantsByClassSeasonAndCampus(dto, "曹杨"));
        System.out.println("==================");
    }
}