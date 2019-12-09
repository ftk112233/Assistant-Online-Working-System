package com.jzy.service;

import com.jzy.BaseTest;
import com.jzy.config.FilePathProperties;
import com.jzy.manager.exception.InputFileTypeException;
import com.jzy.model.dto.StudentAndClassDetailedWithSubjectsDto;
import com.jzy.model.dto.StudentAndClassSearchCondition;
import com.jzy.model.excel.input.StudentListImportToDatabaseExcel;
import com.jzy.model.excel.template.SeatTableTemplateExcel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.util.List;

public class StudentAndClassServiceTest extends BaseTest {
    @Autowired
    private StudentAndClassService studentAndClassService;

    @Autowired
    private FilePathProperties filePathProperties;

    @Autowired
    protected RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private HashOperations<String, String, Object> hashOps;

    @Test
    public void insertAndUpdateStudentAndClassesFromExcel() throws Exception {
        StudentListImportToDatabaseExcel excel = new StudentListImportToDatabaseExcel("D:\\aows_resources\\toolbox\\example\\秋下花名册.xls");
        excel.readStudentAndClassInfoFromExcel();
        studentAndClassService.insertAndUpdateStudentAndClassesFromExcel(excel.getStudentAndClassDetailedDtos());

    }

    @Test
    public void listStudentAndClassesByClassId() {
        List<StudentAndClassDetailedWithSubjectsDto> studentAndClassDetailedDtos=studentAndClassService.listStudentAndClassesByClassId("U6ECFC020006");
        for (StudentAndClassDetailedWithSubjectsDto s:studentAndClassDetailedDtos){
            System.out.println(s);
        }
        System.out.println(studentAndClassDetailedDtos.size());
    }

    @Test
    public void listStudentAndClassesWithSubjectsByClassId() throws IOException, InputFileTypeException {
        List<StudentAndClassDetailedWithSubjectsDto> results=studentAndClassService.listStudentAndClassesWithSubjectsByClassId("U6ECFC020006");
//        AssistantTutorialExcel excel=new AssistantTutorialExcel(filePathProperties.getToolboxAssistantTutorialTemplatePathAndName("曹杨"));
        SeatTableTemplateExcel excel=new SeatTableTemplateExcel(filePathProperties.getToolboxSeatTableTemplatePathAndName("曹杨"));
        excel.writeSeatTable(results);
//        excel.submitWrite("C:\\Users\\92970\\Downloads\\1\\"+FileUtils.TEMPLATES.get(2));
        hashOps.put("h",1+"","111");
        hashOps.put("h",1+"","121");

    }

    @Test
    public void countStudentsGroupByClassGrade() {
        System.out.println(studentAndClassService.countStudentsGroupByClassGrade(new StudentAndClassSearchCondition()));
    }

    @Test
    public void countStudentsGroupByClassGradeAndType() {
        System.out.println(studentAndClassService.countStudentsGroupByClassGradeAndType(new StudentAndClassSearchCondition()));
    }
}