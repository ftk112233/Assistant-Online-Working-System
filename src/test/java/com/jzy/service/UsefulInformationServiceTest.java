package com.jzy.service;

import com.jzy.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UsefulInformationServiceTest extends BaseTest {
    @Autowired
    private UsefulInformationService usefulInformationService;

    @Test
    public void listUsefulInformationByBelongTo() {
        System.out.println(usefulInformationService.listUsefulInformationWithPublicByBelongTo("曹杨"));
    }

    @Test
    public void getRecommendedSequence() {
        System.out.println(usefulInformationService.getRecommendedSequence("曹"));
    }
}