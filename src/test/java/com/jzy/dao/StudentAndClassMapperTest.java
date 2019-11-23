package com.jzy.dao;

import com.jzy.BaseTest;
import com.jzy.model.dto.StudentAndClassDetailedDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentAndClassMapperTest extends BaseTest {
    @Autowired
    protected StudentAndClassMapper studentAndClassMapper;

    @Test
    public void insertStudentAndClass() {
        studentAndClassMapper.insertStudentAndClass(new StudentAndClassDetailedDto());
    }
}