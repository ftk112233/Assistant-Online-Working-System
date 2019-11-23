package com.jzy.service;

import com.jzy.model.entity.Teacher;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName TeacherService
 * @description 教师业务
 * @date 2019/11/14 23:29
 **/
public interface TeacherService {
    /**
     * 根据教师id查询教师
     *
     * @param id 教师id
     * @return
     */
    Teacher getTeacherById(Long id);

    /**
     * 根据教师工号查询教师
     *
     * @param teacherId 教师工号
     * @return
     */
    Teacher getTeacherByWorkId(String teacherId);

    /**
     * 根据教师姓名查询教师
     *
     * @param teacherName 教师姓名
     * @return
     */
    Teacher getTeacherByName(String teacherName);

    /**
     * 添加教师
     *
     * @param teacher 新添加教师的信息
     * @return
     */
    String insertTeacher(Teacher teacher);

    /**
     * 修改教师信息由工号修改
     *
     * @param teacher 修改后的教师信息
     * @return
     */
    String updateTeacherByWorkId(Teacher teacher);

    /**
     * 修改教师信息由工号修改
     *
     * @param originalTeacher 原来的教师信息
     * @param newTeacher 修改教师信息由工号修改
     * @return
     */
    String updateTeacherByWorkId(Teacher originalTeacher, Teacher newTeacher);

    /**
     * 根据从excel中读取到的teachers信息，更新插入多个。根据工号判断：
     *      if 当前工号不存在
     *          执行插入
     *      else
     *          根据工号更新
     * @param teachers
     * @return
     */
    String insertAndUpdateTeachersFromExcel(List<Teacher> teachers) throws Exception;

    /**
     * 根据从excel中读取到的teachers信息，更新插入一个。根据工号判断：
     *      if 当前工号不存在
     *          执行插入
     *      else
     *          根据工号更新
     * @param teacher
     * @return
     */
    String insertAndUpdateOneTeacherFromExcel(Teacher teacher) throws Exception;
}
