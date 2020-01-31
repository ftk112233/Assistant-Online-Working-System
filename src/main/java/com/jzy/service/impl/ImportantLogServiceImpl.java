package com.jzy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.dao.ImportantLogMapper;
import com.jzy.model.dto.ImportantLogDetailedDto;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.search.ImportantLogSearchCondition;
import com.jzy.model.entity.ImportantLog;
import com.jzy.service.ImportantLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ImportantLogServiceImpl
 * @Author JinZhiyun
 * @Description 持久化到数据库的重要日志业务接口实现
 * @Date 2020/1/31 9:35
 * @Version 1.0
 **/
@Service
public class ImportantLogServiceImpl extends AbstractServiceImpl implements ImportantLogService{
    @Autowired
    private ImportantLogMapper importantLogMapper;

    @Override
    public String insertOneImportantLog(ImportantLog importantLog) {
        if (importantLog == null) {
            return FAILURE;
        }

        importantLogMapper.insertOneImportantLog(importantLog);
        return SUCCESS;
    }

    @Override
    public PageInfo<ImportantLogDetailedDto> listImportantLog(MyPage myPage, ImportantLogSearchCondition condition) {
        PageHelper.startPage(myPage.getPageNum(), myPage.getPageSize());
        List<ImportantLogDetailedDto> allLog = importantLogMapper.listImportantLog(condition);
        return new PageInfo<>(allLog);
    }

    @Override
    public long deleteManyImportantLogByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return 0;
        }
        return importantLogMapper.deleteManyImportantLogByIds(ids);
    }

    @Override
    public long deleteImportantLogByCondition(ImportantLogSearchCondition condition) {
        if (condition == null) {
            return 0;
        }
        return importantLogMapper.deleteImportantLogByCondition(condition);
    }
}
