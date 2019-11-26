package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.StudentSearchCondition;
import com.jzy.model.entity.Student;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName StudentService
 * @description 学生业务
 * @date 2019/11/14 23:31
 **/
public interface StudentService {
    /**
     * 根据主键id修改查询学生
     *
     * @param id 主键id
     * @return
     */
    Student getStudentById(Long id);

    /**
     * 根据学员编号修改查询学生
     *
     * @param studentId 学员编号
     * @return
     */
    Student getStudentByStudentId(String studentId);

    /**
     * 修改学生信息由学员编号修改
     *
     * @param student 修改后的学生信息
     * @return
     */
    String updateStudentByStudentId(Student student);

    /**
     * 添加学生
     *
     * @param student 添加学生的信息
     */
    String insertStudent(Student student);


    /**
     * 根据从excel中读取到的students信息（包括手机等字段），更新插入多个。根据学员编号判断：
     *      if 当前学员编号不存在
     *          执行插入
     *      else
     *          根据学员编号更新
     * @param students
     * @return
     */
    String insertAndUpdateStudentsDetailedFromExcel(List<Student> students) throws Exception;

    /**
     * 根据从excel中读取到的students信息（包括手机等字段），更新插入一个。根据学员编号判断：
     *      if 当前学员编号不存在
     *          执行插入
     *      else
     *          根据学员编号更新
     * @param student
     * @return
     */
    String insertAndUpdateOneStudentDetailedFromExcel(Student student) throws Exception;

    /**
     * 根据从excel中读取到的students信息（一般就学号姓名，不包括手机等字段），更新插入多个。根据学员编号判断：
     *      if 当前学员编号不存在
     *          执行插入
     *      else
     *          根据学员编号更新
     * @param students
     * @return
     */
    String insertAndUpdateStudentsFromExcel(List<Student> students) throws Exception;

    /**
     * 根据从excel中读取到的students信息（一般就学号姓名，不包括手机等字段），更新插入一个。根据学员编号判断：
     *      if 当前学员编号不存在
     *          执行插入
     *      else
     *          根据学员编号更新
     * @param student
     * @return
     */
    String insertAndUpdateOneStudentFromExcel(Student student) throws Exception;

    /**
     * 查询学员个人信息
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    PageInfo<Student> listStudents(MyPage myPage, StudentSearchCondition condition);
}
