package com.jzy.service;

import com.jzy.model.dto.StudentAndClassDetailedDto;

import java.util.List;

/**
 * @InterfaceName StudentAndClassService
 * @Author JinZhiyun
 * @Description 学生报班业务
 * @Date 2019/11/23 18:06
 * @Version 1.0
 **/
public interface StudentAndClassService {
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
    String insertStudentAndClass(StudentAndClassDetailedDto studentAndClassDetailedDto);

    /**
     * 根据当前学员号和报班班号更新，报班情况
     *
     * @param studentAndClassDetailedDto
     * @return
     */
    String updateStudentAndClassByStudentIdAndClassId(StudentAndClassDetailedDto studentAndClassDetailedDto);

    /**
     * 根据从excel中读取到的studentAndClassDetailedDtos信息，更新插入多个。根据学员号和班号判断：
     *      if 当前学员号和班号组合不存在
     *          执行插入
     *      else
     *          根据学员号和班号更新
     * @param studentAndClassDetailedDtos
     * @return
     */
    String insertAndUpdateStudentAndClassesFromExcel(List<StudentAndClassDetailedDto> studentAndClassDetailedDtos) throws Exception;

    /**
     * 根据从excel中读取到的studentAndClassDetailedDto信息，更新插入一个。根据学员号和班号判断：
     *      if 当前学员号和班号组合不存在
     *          执行插入
     *      else
     *          根据学员号和班号更新
     * @param studentAndClassDetailedDto
     * @return
     */
    String insertAndUpdateOneStudentAndClassFromExcel(StudentAndClassDetailedDto studentAndClassDetailedDto) throws Exception;
}
