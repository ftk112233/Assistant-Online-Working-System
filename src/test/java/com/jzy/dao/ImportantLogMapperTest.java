package com.jzy.dao;

import com.jzy.BaseTest;
import com.jzy.model.dto.search.ImportantLogSearchCondition;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ImportantLogMapperTest extends BaseTest {
    @Autowired
    private ImportantLogMapper importantLogMapper;

    @Test
    public void listImportantLog() {
        System.out.println(importantLogMapper.listImportantLog(new ImportantLogSearchCondition()));
    }
}