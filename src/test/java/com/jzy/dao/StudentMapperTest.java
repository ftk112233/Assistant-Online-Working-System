package com.jzy.dao;

import com.jzy.BaseTest;
import com.jzy.model.dto.StudentAndClassSearchCondition;
import com.jzy.model.entity.Student;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StudentMapperTest extends BaseTest {
    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void insertManyStudents() {
        List<Student> students=new ArrayList<>();
        Student student=new Student();
        student.setStudentId("111111");
        student.setStudentName("aaaaaaaaa");
        student.setStudentSex(null);
        students.add(student);
        studentMapper.insertManyStudents(students);
    }

    @Test
    public void listStudentsByStudentAndClassSearchCondition() {
        StudentAndClassSearchCondition condition=new StudentAndClassSearchCondition();
        condition.setClassYear("2020");
        condition.setClassSeason("秋下");
        condition.setAssistantName("金之贇");
        List<Student> students=studentMapper.listStudentsByStudentAndClassSearchCondition(condition);
        for (Student student:students){
            System.out.println(student);
        }
        System.out.println(students.size());
    }
}