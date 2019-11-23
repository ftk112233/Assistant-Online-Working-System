package com.jzy.service;

import com.jzy.BaseTest;
import com.jzy.model.excel.input.AssistantInfoExcel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends BaseTest {
    @Autowired
    private UserService userService;

    @Test
    public void insertAndUpdateUsersFromExcel() throws Exception {
        AssistantInfoExcel excel=new AssistantInfoExcel("C:\\Users\\92970\\Desktop\\助教信息.xlsx");
        excel.readUsersAndAssistantsFromExcel();
        System.out.println(excel.getUsers());
        System.out.println(userService.insertAndUpdateUsersFromExcel(excel.getUsers()));
    }
}