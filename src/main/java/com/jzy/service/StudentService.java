package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.StudentSearchCondition;
import com.jzy.model.dto.UpdateResult;
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
    UpdateResult updateStudentByStudentId(Student student);

    /**
     * 添加学生
     *
     * @param student 添加学生的信息
     */
    UpdateResult insertStudent(Student student);


    /**
     * 根据从excel中读取到的students信息（包括手机等字段），更新插入多个。根据学员编号判断：
     * if 当前学员编号不存在
     * 执行插入
     * else
     * 根据学员编号更新
     *
     * @param students
     * @return
     */
    UpdateResult insertAndUpdateStudentsDetailedFromExcel(List<Student> students) throws Exception;

    /**
     * 根据从excel中读取到的students信息（包括手机等字段），更新插入一个。根据学员编号判断：
     * if 当前学员编号不存在
     * 执行插入
     * else
     * 根据学员编号更新
     *
     * @param student
     * @return
     */
    UpdateResult insertAndUpdateOneStudentDetailedFromExcel(Student student) throws Exception;

    /**
     * 根据从excel中读取到的students信息（一般就学号姓名，不包括手机等字段），更新插入多个。根据学员编号判断：
     * if 当前学员编号不存在
     * 执行插入
     * else
     * 根据学员编号更新
     *
     * @param students
     * @return
     */
    UpdateResult insertAndUpdateStudentsFromExcel(List<Student> students) throws Exception;

    /**
     * 根据从excel中读取到的students信息（一般就学号姓名，不包括手机等字段），更新插入一个。根据学员编号判断：
     * if 当前学员编号不存在
     * 执行插入
     * else
     * 根据学员编号更新
     *
     * @param student
     * @return
     */
    UpdateResult insertAndUpdateOneStudentFromExcel(Student student) throws Exception;

    /**
     * 查询学员个人信息
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    PageInfo<Student> listStudents(MyPage myPage, StudentSearchCondition condition);

    /**
     * 修改学生信息由id修改
     *
     * @param student 修改后的学生信息
     * @return
     */
    String updateStudentInfo(Student student);

    /**
     * 删除一个学生，根据学生id
     *
     * @param id 被删除学生的id
     * @return
     */
    long deleteOneStudentById(Long id);

    /**
     * 根据id删除多个学生
     *
     * @param ids 学生id的列表
     */
    long deleteManyStudentsByIds(List<Long> ids);

    /**
     * 条件删除多个学生
     *
     * @param condition 输入的查询条件
     * @return
     */
    String deleteStudentsByCondition(StudentSearchCondition condition);
}
