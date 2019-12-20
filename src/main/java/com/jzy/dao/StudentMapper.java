package com.jzy.dao;

import com.jzy.model.dto.StudentSearchCondition;
import com.jzy.model.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName StudentMapper
 * @description 学生业务dao接口
 * @date 2019/11/13 16:20
 **/
public interface StudentMapper {
    /**
     * 根据主键id修改查询学生
     *
     * @param id 主键id
     * @return
     */
    Student getStudentById(@Param("id") Long id);

    /**
     * 根据学员编号修改查询学生
     *
     * @param studentId 学员编号
     * @return
     */
    Student getStudentByStudentId(@Param("studentId") String studentId);

    /**
     * 添加学生
     *
     * @param student 添加学生的信息
     */
    long insertStudent(Student student);

    /**
     * 修改学生信息学员编号修改
     *
     * @param student 修改后的学生信息
     * @return
     */
    long updateStudentByStudentId(Student student);

    /**
     * 修改学生姓名学员编号修改
     *
     * @param student 修改后的学生信息
     * @return
     */
    long updateStudentNameByStudentId(Student student);

    /**
     * 查询学员个人信息
     *
     * @param condition 查询条件入参
     * @return
     */
    List<Student> listStudents(StudentSearchCondition condition);

    /**
     * 修改学生信息由id修改
     *
     * @param student 修改后的学生信息
     * @return
     */
    long updateStudentInfo(Student student);

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
    long deleteStudentsByCondition(StudentSearchCondition condition);

    /**
     * 修改学生学校由学员编号修改
     *
     * @param student 修改后的学生信息
     * @return
     */
    long updateStudentSchoolByStudentId(Student student);

    /**
     * 修改学生姓名、手机、备用手机由学员编号修改
     *
     * @param student 修改后的学生信息
     * @return
     */
    long updateStudentNameAndPhoneByStudentId(Student student);
}
