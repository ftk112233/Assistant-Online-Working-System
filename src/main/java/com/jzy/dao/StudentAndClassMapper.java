package com.jzy.dao;

import com.jzy.model.dto.StudentAndClassDetailedDto;
import com.jzy.model.dto.StudentAndClassSearchCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @InterfaceName StudentAndClassMapper.xml
 * @Author JinZhiyun
 * @Description 学生和上课班级dao接口
 * @Date 2019/11/23 18:31
 * @Version 1.0
 **/
public interface StudentAndClassMapper {
    /**
     * 查询指定学员编号和班号的记录数，即当前学员是否报了当前班
     *
     * @param studentId 学员编号
     * @param classId 班号
     * @return
     */
    Long countStudentAndClassByStudentIdAndClassId(@Param("studentId") String studentId, @Param("classId") String classId);

    /**
     * 插入一个学生报班记录
     *
     * @param studentAndClassDetailedDto
     * @return
     */
    void insertStudentAndClass(StudentAndClassDetailedDto studentAndClassDetailedDto);

    /**
     * 根据当前学员号和报班班号更新，报班情况
     *
     * @param studentAndClassDetailedDto
     * @return
     */
    void updateStudentAndClassByStudentIdAndClassId(StudentAndClassDetailedDto studentAndClassDetailedDto);

    /**
     * 返回符合条件的学生上课信息分页结果
     *
     * @param condition  查询条件入参
     * @return
     */
    List<StudentAndClassDetailedDto> listStudentAndClasses(StudentAndClassSearchCondition condition);
}
