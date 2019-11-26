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
    void insertStudent(Student student);

    /**
     * 修改学生信息学员编号修改
     *
     * @param student 修改后的学生信息
     * @return
     */
    void updateStudentByStudentId(Student student);

    /**
     * 修改学生姓名学员编号修改
     *
     * @param student 修改后的学生信息
     * @return
     */
    void updateStudentNameByStudentId(Student student);

    /**
     * 查询学员个人信息
     *
     * @param condition 查询条件入参
     * @return
     */
    List<Student> listStudents(StudentSearchCondition condition);
}
