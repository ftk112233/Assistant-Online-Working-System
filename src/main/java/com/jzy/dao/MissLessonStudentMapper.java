package com.jzy.dao;

import com.jzy.model.dto.MissLessonStudentDetailedDto;
import com.jzy.model.dto.search.MissLessonStudentSearchCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @InterfaceName MissLessonStudentMapper
 * @Author JinZhiyun
 * @Description 补课学生业务dao接口
 * @Date 2019/11/21 22:03
 * @Version 1.0
 **/
public interface MissLessonStudentMapper {
    /**
     * 查询补课学员信息
     *
     * @param condition 查询条件入参
     * @return 符合条件的补课记录
     */
    List<MissLessonStudentDetailedDto> listMissLessonStudents(MissLessonStudentSearchCondition condition);

    /**
     * 修改补课学生信息，由id修改
     *
     * @param missLessonStudentDetailedDto 修改后的补课学生信息
     * @return 更新记录数
     */
    long updateMissLessonStudentInfo(MissLessonStudentDetailedDto missLessonStudentDetailedDto);

    /**
     * 添加补课学生
     *
     * @param missLessonStudentDetailedDto 新添加补课学生
     * @return 更新记录数
     */
    long insertOneMissLessonStudent(MissLessonStudentDetailedDto missLessonStudentDetailedDto);

    /**
     * 删除一个补课学生记录
     *
     * @param id 被删除补课学生的id
     * @return 更新记录数
     */
    long deleteOneMissLessonStudentById(@Param("id") Long id);

    /**
     * 根据id删除多个补课学生记录
     *
     * @param ids 补课学生id的列表
     * @return 更新记录数
     */
    long deleteManyMissLessonStudentsByIds(List<Long> ids);

}
