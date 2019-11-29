package com.jzy.service;

import com.jzy.BaseTest;
import com.jzy.manager.util.FileUtils;
import com.jzy.model.excel.input.StudentListImportToDatabaseExcel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class StudentServiceTest extends BaseTest {
    @Autowired
    private StudentService studentService;

    @Test
    public void insertAndUpdateStudentsFromExcel() throws Exception {
        StudentListImportToDatabaseExcel excel = new StudentListImportToDatabaseExcel("D:\\aows_resources\\toolbox\\example\\"+FileUtils.EXAMPLES.get(4));
        excel.readStudentDetailInfoFromExcel();
        studentService.insertAndUpdateStudentsDetailedFromExcel(new ArrayList<>(excel.getStudents()));
    }
}