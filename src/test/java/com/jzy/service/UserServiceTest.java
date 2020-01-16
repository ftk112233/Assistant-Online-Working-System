package com.jzy.service;

import com.jzy.BaseTest;
import com.jzy.manager.exception.*;
import com.jzy.model.excel.input.AssistantInfoExcel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class UserServiceTest extends BaseTest {
    @Autowired
    private UserService userService;

    @Test
    public void insertAndUpdateUsersFromExcel() throws IOException, InvalidFileTypeException, ExcelColumnNotFoundException, ExcelTooManyRowsException {
        AssistantInfoExcel excel=new AssistantInfoExcel("C:\\Users\\92970\\Desktop\\助教信息表(1).xlsx");
        excel.readUsersAndAssistantsFromExcel();
        System.out.println(excel.getUsers());
        try {
            System.out.println(userService.insertAndUpdateUsersFromExcel(null));
        } catch (InvalidParameterException e) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            e.printStackTrace();
        }
    }

}