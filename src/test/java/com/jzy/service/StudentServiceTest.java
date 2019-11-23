package com.jzy.service;

import com.jzy.BaseTest;
import com.jzy.model.excel.input.StudentListExcel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class StudentServiceTest extends BaseTest {
    @Autowired
    private StudentService studentService;

    @Test
    public void insertAndUpdateStudentsFromExcel() throws Exception {
        StudentListExcel excel = new StudentListExcel("D:\\aows_resources\\toolbox\\example\\秋下花名册.xls");
        excel.readStudentAndClassInfoFromExcel();
        studentService.insertAndUpdateStudentsFromExcel(new ArrayList<>(excel.getStudents()));
    }
}