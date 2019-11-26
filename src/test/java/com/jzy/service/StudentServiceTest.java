package com.jzy.service;

import com.jzy.BaseTest;
import com.jzy.manager.util.FileUtils;
import com.jzy.model.excel.input.StudentListExcel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class StudentServiceTest extends BaseTest {
    @Autowired
    private StudentService studentService;

    @Test
    public void insertAndUpdateStudentsFromExcel() throws Exception {
        StudentListExcel excel = new StudentListExcel("D:\\aows_resources\\toolbox\\example\\"+FileUtils.FILE_NAMES.get(4));
        excel.readStudentDetailInfoFromExcel();
        studentService.insertAndUpdateStudentsDetailedFromExcel(new ArrayList<>(excel.getStudents()));
    }
}