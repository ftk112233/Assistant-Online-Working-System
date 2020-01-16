package com.jzy.dao;

import com.jzy.BaseTest;
import com.jzy.model.dto.StudentAndClassDetailedDto;
import com.jzy.model.dto.StudentAndClassSearchCondition;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class StudentAndClassMapperTest extends BaseTest {
    @Autowired
    protected StudentAndClassMapper studentAndClassMapper;

    @Test
    public void insertStudentAndClass() {
        studentAndClassMapper.insertOneStudentAndClass(new StudentAndClassDetailedDto());
    }

    @Test
    public void countStudentsGroupByClassSubject() {
        System.out.println(studentAndClassMapper.countStudentsGroupByClassSubject(new StudentAndClassSearchCondition()));
    }

    @Test
    public void insertStudentAndClasses() {
        List<StudentAndClassDetailedDto> dtos=new ArrayList<>();
        StudentAndClassDetailedDto dto1=new StudentAndClassDetailedDto();
        dto1.setStudentId("SH2060002");
        dto1.setClassId("U6ECFC020006");
        dtos.add(dto1);

        StudentAndClassDetailedDto dto2=new StudentAndClassDetailedDto();
        dto2.setStudentId("SH2060002");
        dto2.setClassId("U6ECFC020010");
        dtos.add(dto2);
        studentAndClassMapper.insertManyStudentAndClasses(dtos);
    }
}