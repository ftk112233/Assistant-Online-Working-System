package com.jzy.dao;

import com.jzy.model.dto.StudentAndClassDetailedDto;

/**
 * @InterfaceName StudentAndClassMapper
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
    int countStudentAndClassByStudentIdAndClassId(String studentId, String classId);

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
}
