package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.StudentAndClassDetailedDto;
import com.jzy.model.dto.StudentAndClassDetailedWithSubjectsDto;
import com.jzy.model.dto.StudentAndClassSearchCondition;
import com.jzy.model.entity.StudentAndClass;

import java.util.List;

/**
 * @InterfaceName StudentAndClassService
 * @Author JinZhiyun
 * @Description 学生报班业务
 * @Date 2019/11/23 18:06
 * @Version 1.0
 **/
public interface StudentAndClassService {
    /**
     * 根据id查询StudentAndClass
     *
     * @param id 学员上课对象的自增主键id
     * @return
     */
    StudentAndClass getStudentAndClassById(Long id);

    /**
     * 查询指定学员编号和班号的记录数，即当前学员是否报了当前班
     *
     * @param studentId 学员编号
     * @param classId 班号
     * @return
     */
    Long countStudentAndClassByStudentIdAndClassId(String studentId, String classId);

    /**
     * 插入一个学生报班记录
     *
     * @param studentAndClassDetailedDto
     * @return
     */
    String insertStudentAndClass(StudentAndClassDetailedDto studentAndClassDetailedDto);

    /**
     * 根据当前学员号和报班班号更新，报班情况
     *
     * @param studentAndClassDetailedDto
     * @return
     */
    String updateStudentAndClassByStudentIdAndClassId(StudentAndClassDetailedDto studentAndClassDetailedDto);

    /**
     * 根据从excel中读取到的studentAndClassDetailedDtos信息，更新插入多个。根据学员号和班号判断：
     *      if 当前学员号和班号组合不存在
     *          执行插入
     *      else
     *          根据学员号和班号更新
     * @param studentAndClassDetailedDtos
     * @return
     */
    String insertAndUpdateStudentAndClassesFromExcel(List<StudentAndClassDetailedDto> studentAndClassDetailedDtos) throws Exception;

    /**
     * 根据从excel中读取到的studentAndClassDetailedDto信息，更新插入一个。根据学员号和班号判断：
     *      if 当前学员号和班号组合不存在
     *          执行插入
     *      else
     *          根据学员号和班号更新
     * @param studentAndClassDetailedDto
     * @return
     */
    String insertAndUpdateOneStudentAndClassFromExcel(StudentAndClassDetailedDto studentAndClassDetailedDto) throws Exception;

    /**
     * 查询学员上课信息的ajax交互
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    PageInfo<StudentAndClassDetailedDto> listStudentAndClasses(MyPage myPage, StudentAndClassSearchCondition condition);

    /**
     * 编辑学员上课信息，由id修改
     *
     * @param studentAndClassDetailedDto 修改后的学员上课信息
     * @return
     */
    String updateStudentAndClassInfo(StudentAndClassDetailedDto studentAndClassDetailedDto);

    /**
     * 删除一个学员上课记录
     *
     * @param id 被删除学员上课的id
     * @return
     */
    void deleteOneStudentAndClassById(Long id);

    /**
     * 根据id删除多个学员上课记录
     *
     * @param ids 学员上课记录id的列表
     */
    void deleteManyStudentAndClassesByIds(List<Long> ids);

    /**
     * 根据班级编码查询班级的所有学生及班级的详细信息
     *
     * @param classId 班级编码
     * @return
     */
    List<StudentAndClassDetailedWithSubjectsDto> listStudentAndClassesByClassId(String classId);

    /**
     * 根据班级编码查询班级的所有学生及班级的详细信息，包括该学生在当前班级id所解析到的年份、季度下所修读的所有学科
     *
     * @param classId 班级编码
     * @return
     */
    List<StudentAndClassDetailedWithSubjectsDto> listStudentAndClassesWithSubjectsByClassId(String classId);
}
