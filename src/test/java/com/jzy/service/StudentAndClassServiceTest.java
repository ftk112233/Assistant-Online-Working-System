package com.jzy.service;

import com.jzy.BaseTest;
import com.jzy.model.excel.input.StudentListExcel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentAndClassServiceTest extends BaseTest {
    @Autowired
    private StudentAndClassService studentAndClassService;
    @Test
    public void insertAndUpdateStudentAndClassesFromExcel() throws Exception {
        StudentListExcel excel = new StudentListExcel("D:\\aows_resources\\toolbox\\example\\秋下花名册.xls");
        excel.readStudentAndClassInfoFromExcel();
        studentAndClassService.insertAndUpdateStudentAndClassesFromExcel(excel.getStudentAndClassDetailedDtos());
    }
}