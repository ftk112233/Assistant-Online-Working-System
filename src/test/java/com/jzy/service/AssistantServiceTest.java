package com.jzy.service;

import com.jzy.BaseTest;
import com.jzy.model.excel.input.AssistantInfoExcel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AssistantServiceTest extends BaseTest {
    @Autowired
    private AssistantService assistantService;
    @Test
    public void insertAndUpdateAssistantsFromExcel() throws Exception {
        AssistantInfoExcel excel=new AssistantInfoExcel("C:\\Users\\92970\\Desktop\\助教信息.xlsx");
        excel.readUsersAndAssistantsFromExcel();
        System.out.println(excel.getAssistants());
        System.out.println(assistantService.insertAndUpdateAssistantsFromExcel(excel.getAssistants()));
    }
}