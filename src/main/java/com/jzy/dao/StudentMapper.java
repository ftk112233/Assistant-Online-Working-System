package com.jzy.dao;

import com.jzy.model.dto.StudentAndClassSearchCondition;
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
     * @return 对应学生对象
     */
    Student getStudentById(@Param("id") Long id);

    /**
     * 根据学员编号修改查询学生
     *
     * @param studentId 学员编号
     * @return 对应学生对象
     */
    Student getStudentByStudentId(@Param("studentId") String studentId);

    /**
     * 添加学生
     *
     * @param student 添加学生的信息
     * @return 更新记录数
     */
    long insertOneStudent(Student student);

    /**
     * 批量添加学生
     *
     * @param students 添加学生的信息集合
     * @return 更新记录数
     */
    long insertManyStudents(List<Student> students);

    /**
     * 修改学生信息学员编号修改
     *
     * @param student 修改后的学生信息
     * @return 更新记录数
     */
    long updateStudentByStudentId(Student student);

    /**
     * 修改学生姓名学员编号修改
     *
     * @param student 修改后的学生信息
     * @return 更新记录数
     */
    long updateStudentNameByStudentId(Student student);

    /**
     * 查询学员个人信息
     *
     * @param condition 查询条件入参
     * @return 结果集合
     */
    List<Student> listStudents(StudentSearchCondition condition);

    /**
     * 查询学员个人信息，以上课记录中的信息为查询条件，返回唯一的学生个人信息对象集合
     *
     * @param condition 上课记录的查询条件入参
     * @return 结果集合
     */
    List<Student> listStudentsByStudentAndClassSearchCondition(StudentAndClassSearchCondition condition);

    /**
     * 修改学生信息由id修改
     *
     * @param student 修改后的学生信息
     * @return 更新记录数
     */
    long updateStudentInfo(Student student);

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

    /**
     * 修改学生学校由学员编号修改
     *
     * @param student 修改后的学生信息
     * @return 更新记录数
     */
    long updateStudentSchoolByStudentId(Student student);

    /**
     * 修改学生姓名、手机、备用手机由学员编号修改
     *
     * @param student 修改后的学生信息
     * @return 更新记录数
     */
    long updateStudentNameAndPhoneByStudentId(Student student);
}
