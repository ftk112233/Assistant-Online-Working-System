package com.jzy.service;

import com.jzy.BaseTest;
import com.jzy.model.excel.input.ClassArrangementExcel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class TeacherServiceTest extends BaseTest {
    @Autowired
    private TeacherService teacherService;

    @Test
    public void insertAndUpdateTeachersFromExcel() throws Exception {
        ClassArrangementExcel excel = new ClassArrangementExcel("D:\\aows_resources\\toolbox\\example\\曹杨秋季助教排班.xlsx");
        excel.readClassDetailFromExcel();
        teacherService.insertAndUpdateTeachersFromExcel(new ArrayList<>(excel.getTeachers()));
    }
}