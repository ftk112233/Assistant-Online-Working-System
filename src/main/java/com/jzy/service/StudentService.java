package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.model.dto.*;
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
     * 判断输入学生对象的student是否与数据库中已有的有冲突
     *
     * @param student 要判断的学生对象
     * @return 学员号是否冲突
     */
    boolean isRepeatedStudentId(Student student);

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
    UpdateResult insertOneStudent(Student student);

    /**
     * 根据从excel中读取到的students信息（包括手机等字段），更新插入多个。根据学员编号判断：
     * if 当前学员编号不存在
     * 执行插入
     * else
     * 根据学员编号更新
     *
     * @param students 读取到的学生信息
     * @return 更新结果
     */
    DefaultFromExcelUpdateResult insertAndUpdateStudentsDetailedFromExcel(List<Student> students);

    /**
     * 根据从excel中读取到的students信息（就学号姓名，不包括手机等字段），更新插入多个。根据学员编号判断：
     * if 当前学员编号不存在
     * 执行插入
     * else
     * 根据学员编号更新姓名
     *
     * @param students 只含学号、姓名的学生信息集合
     * @return 更新结果
     */
    DefaultFromExcelUpdateResult insertAndUpdateStudentsFromExcel(List<Student> students);

    /**
     * 根据从excel中读取到的students学校信息，更新多个。根据学员编号判断：
     * if 当前学员编号不存在
     * 执行插入
     * else
     * 根据学员编号更新学校
     *
     * @param students 只含学号、学校的学生信息集合
     * @return 更新结果
     */
    DefaultFromExcelUpdateResult insertAndUpdateStudentsSchoolsFromExcel(List<Student> students);

    /**
     * 查询学员个人信息
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return 分页结果
     */
    PageInfo<Student> listStudents(MyPage myPage, StudentSearchCondition condition);

    /**
     * 查询学员个人信息，以上课记录中的信息为查询条件，返回唯一的学生个人信息对象集合
     *
     * @param condition 上课记录的查询条件入参
     * @return 结果集合
     */
    List<Student> listStudents(StudentAndClassSearchCondition condition);

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
