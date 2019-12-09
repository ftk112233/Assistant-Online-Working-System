package com.jzy.dao;

import com.jzy.model.dto.TeacherSearchCondition;
import com.jzy.model.entity.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName TeacherMapper
 * @description 教师业务dao接口
 * @date 2019/11/13 16:20
 **/
public interface TeacherMapper {
    /**
     * 根据教师id查询教师
     *
     * @param id 教师id
     * @return
     */
    Teacher getTeacherById(@Param("id") Long id);

    /**
     * 根据教师姓名查询教师
     *
     * @param teacherName 教师姓名
     * @return
     */
    Teacher getTeacherByName(@Param("teacherName") String teacherName);

    /**
     * 根据教师工号查询教师
     *
     * @param teacherId 教师工号
     * @return
     */
    Teacher getTeacherByWorkId(@Param("teacherId") String teacherId);

    /**
     * 添加教师
     *
     * @param teacher 新添加教师的信息
     * @return
     */
    long insertTeacher(Teacher teacher);

    /**
     * 修改教师信息由工号修改
     *
     * @param teacher 修改后的教师信息
     * @return
     */
    long updateTeacherByWorkId(Teacher teacher);

    /**
     * 返回符合条件的教师信息分页结果
     *
     * @param condition  查询条件入参
     * @return
     */
    List<Teacher> listTeachers(TeacherSearchCondition condition);

    /**
     * 修改教师信息由id修改
     *
     * @param teacher 修改后的教师信息
     * @return
     */
    long updateTeacherInfo(Teacher teacher);

    /**
     * 根据id删除一个教师
     *
     * @param id 教师id
     */
    long deleteOneTeacherById(Long id);

    /**
     * 根据id删除多个个教师
     *
     * @param ids 教师id的列表
     */
    long deleteManyTeachersByIds(List<Long> ids);

    /**
     * 根据输入条件删除指定的教师
     *
     * @param condition 输入条件封装
     * @return
     */
    long deleteTeachersByCondition(TeacherSearchCondition condition);
}
