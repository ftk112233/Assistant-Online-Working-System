package com.jzy.service;

import com.jzy.BaseTest;
import com.jzy.model.excel.input.ClassArrangementExcel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ClassServiceTest extends BaseTest {
    @Autowired
    private ClassService classService;

    @Test
    public void insertAndUpdateClassesFromExcel() throws Exception {
        ClassArrangementExcel excel = new ClassArrangementExcel("D:\\aows_resources\\toolbox\\example\\曹杨秋季助教排班.xlsx");
        excel.readClassDetailFromExcel();
        classService.insertAndUpdateClassesFromExcel(excel.getClassDetailedDtos());
    }

    @Test
    public void listClassesLikeClassId() {
        System.out.println(classService.listClassesLikeClassId("U6MC"));
    }

    @Test
    public void listClassIdsLikeClassId() {
        System.out.println(classService.listClassIdsLikeClassId("U6MC"));
    }

}