package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.manager.exception.InvalidParameterException;
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
     * @return 对应学生对象
     */
    Student getStudentById(Long id);

    /**
     * 根据学员编号修改查询学生
     *
     * @param studentId 学员编号
     * @return 对应学生对象
     */
    Student getStudentByStudentId(String studentId);

    /**
     * 修改学生信息由学员编号修改
     *
     * @param student 修改后的学生信息
     * @return (更新结果, 更新记录数)
     * 1."failure"：错误入参等异常
     * 2."success": 更新成功
     */
    UpdateResult updateStudentByStudentId(Student student);

    /**
     * 修改学生姓名、手机、备用手机由学员编号修改
     *
     * @param student 修改后的学生信息
     * @return (更新结果, 更新记录数)
     * 1."failure"：错误入参等异常
     * 2."success": 更新成功
     */
    UpdateResult updateStudentNameAndPhoneByStudentId(Student student);

    /**
     * 修改学生学校由学员编号修改
     *
     * @param student 修改后的学生信息
     * @return (更新结果, 更新记录数)
     * 1."failure"：错误入参等异常
     * 2."success": 更新成功
     */
    UpdateResult updateStudentSchoolByStudentId(Student student);

    /**
     * 添加学生
     *
     * @param student 添加学生的信息
     * @return (更新结果, 更新记录数)
     * 1."failure"：错误入参等异常
     * 2."studentIdRepeat"：学员号冲突
     * 3."success": 更新成功
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
    UpdateResult insertAndUpdateStudentsDetailedFromExcel(List<Student> students) throws InvalidParameterException;

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
    UpdateResult insertAndUpdateOneStudentDetailedFromExcel(Student student) throws InvalidParameterException;

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
    UpdateResult insertAndUpdateStudentsFromExcel(List<Student> students) throws InvalidParameterException;

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
    UpdateResult insertAndUpdateOneStudentFromExcel(Student student) throws InvalidParameterException;

    /**
     * 根据从excel中读取到的student学校信息，更新一个。根据学员编号判断：
     * 仅执行更新
     *
     * @param student
     * @return
     */
    UpdateResult insertAndUpdateOneStudentSchoolFromExcel(Student student) throws InvalidParameterException;

    /**
     * 根据从excel中读取到的students学校信息，更新多个。根据学员编号判断：
     * 仅执行更新
     *
     * @param students
     * @return
     */
    UpdateResult insertAndUpdateStudentsSchoolsFromExcel(List<Student> students) throws InvalidParameterException;

    /**
     * 查询学员个人信息
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return 分页结果
     */
    PageInfo<Student> listStudents(MyPage myPage, StudentSearchCondition condition);

    /**
     * 修改学生信息由id修改
     *
     * @param student 修改后的学生信息
     * @return 1."failure"：错误入参等异常
     * 2."workIdRepeat"：工号冲突
     * 3."unchanged": 对比数据库原记录未做任何修改
     * 4."success": 更新成功
     */
    String updateStudentInfo(Student student);

    /**
     * 删除一个学生，根据学生id
     *
     * @param id 被删除学生的id
     * @return 更新记录数
     */
    long deleteOneStudentById(Long id);

    /**
     * 根据id删除多个学生
     *
     * @param ids 学生id的列表
     * @return 更新记录数
     */
    long deleteManyStudentsByIds(List<Long> ids);

    /**
     * 条件删除多个学生
     *
     * @param condition 输入的查询条件
     * @return 更新记录数
     */
    long deleteStudentsByCondition(StudentSearchCondition condition);
}
