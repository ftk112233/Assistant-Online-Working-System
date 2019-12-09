package com.jzy.service;

import com.jzy.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class QuestionServiceTest extends BaseTest {
    @Autowired
    private QuestionService questionService;

    @Test
    public void getRandomQuestion() {
        System.out.println(questionService.getRandomQuestion());
    }
}