package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.TeacherSearchCondition;
import com.jzy.model.dto.UpdateResult;
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
     * @return 对应教师对象
     */
    Teacher getTeacherById(Long id);

    /**
     * 根据教师工号查询教师
     *
     * @param teacherId 教师工号
     * @return 对应教师对象
     */
    Teacher getTeacherByWorkId(String teacherId);

    /**
     * 根据教师姓名查询教师
     *
     * @param teacherName 教师姓名
     * @return 对应教师对象
     */
    Teacher getTeacherByName(String teacherName);

    /**
     * 添加教师
     *
     * @param teacher 新添加教师的信息
     * @return (更新结果, 更新记录数)
     * 1."failure"：错误入参等异常
     * 2."workIdRepeat"：工号冲突
     * 3."nameRepeat"：姓名冲突
     * 4."success": 更新成功
     */
    UpdateResult insertTeacher(Teacher teacher);

    /**
     * 修改教师信息由工号修改
     *
     * @param teacher 修改后的教师信息
     * @return 1."failure"：错误入参等异常
     * 2."nameRepeat"：姓名冲突
     * 3."unchanged": 对比数据库原记录未做任何修改
     * 4."success": 更新成功
     */
    String updateTeacherByWorkId(Teacher teacher);

    /**
     * 修改教师信息由工号修改
     *
     * @param originalTeacher 原来的教师信息
     * @param newTeacher      修改教师信息由工号修改
     * @return 1."failure"：错误入参等异常
     * 2."nameRepeat"：姓名冲突
     * 3."unchanged": 对比数据库原记录未做任何修改
     * 4."success": 更新成功
     */
    String updateTeacherByWorkId(Teacher originalTeacher, Teacher newTeacher);

    /**
     * 根据从excel中读取到的teachers信息，更新插入多个。根据工号判断：
     * 仅执行插入。由于目前版本从表格只能读取教师姓名字段，所以不用工号做重名校验。只要当前名字不存在，即插入
     *
     * @param teachers 读取到的教师信息集合
     * @return 更新结果
     * @throws InvalidParameterException 不合法的入参异常
     */
    UpdateResult insertAndUpdateTeachersFromExcel(List<Teacher> teachers) throws InvalidParameterException;

    /**
     * 返回符合条件的教师信息分页结果
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return 分页结果
     */
    PageInfo<Teacher> listTeachers(MyPage myPage, TeacherSearchCondition condition);

    /**
     * 修改教师信息由id修改
     *
     * @param teacher 修改后的教师信息
     * @return 1."failure"：错误入参等异常
     * 2."workIdRepeat"：工号冲突
     * 3."nameRepeat"：姓名冲突
     * 4."unchanged": 对比数据库原记录未做任何修改
     * 5."success": 更新成功
     */
    String updateTeacherInfo(Teacher teacher);

    /**
     * 根据id删除一个教师
     *
     * @param id 教师id
     * @return 更新记录数
     */
    long deleteOneTeacherById(Long id);

    /**
     * 根据id删除多个个教师
     *
     * @param ids 教师id的列表
     * @return 更新记录数
     */
    long deleteManyTeachersByIds(List<Long> ids);

    /**
     * 根据输入条件删除指定的教师
     *
     * @param condition 输入条件封装
     * @return 更新记录数
     */
    long deleteTeachersByCondition(TeacherSearchCondition condition);
}
