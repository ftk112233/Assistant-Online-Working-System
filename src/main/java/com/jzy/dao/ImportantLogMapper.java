package com.jzy.dao;

import com.jzy.model.dto.ImportantLogDetailedDto;
import com.jzy.model.dto.search.ImportantLogSearchCondition;
import com.jzy.model.entity.ImportantLog;

import java.util.List;

/**
 * @InterfaceName ImportantLogMapper
 * @Author JinZhiyun
 * @Description 持久化到数据库的重要日志dao接口
 * @Date 2020/1/31 9:37
 * @Version 1.0
 **/
public interface ImportantLogMapper {
    /**
     * 插入一条重要日志
     *
     * @param importantLog 重要日志对象
     * @return 更新记录数
     */
    long insertOneImportantLog(ImportantLog importantLog);

    /**
     * 查询符合条件日志信息
     *
     * @param condition 查询条件入参
     * @return 符合条件日志信息
     */
    List<ImportantLogDetailedDto> listImportantLog(ImportantLogSearchCondition condition);

    /**
     * 根据id的列表删除多条日志
     *
     * @param ids 日志id的列表
     * @return 更新记录数
     */
    long deleteManyImportantLogByIds(List<Long> ids);

    /**
     * 条件删除多条日志记录
     *
     * @param condition 输入的查询条件
     * @return 更新记录数
     */
    long deleteImportantLogByCondition(ImportantLogSearchCondition condition);
}
