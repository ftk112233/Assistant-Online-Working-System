package com.jzy.dao;

import com.jzy.model.dto.StudentAndClassDetailedDto;
import com.jzy.model.dto.StudentAndClassDetailedWithSubjectsDto;
import com.jzy.model.dto.search.StudentAndClassSearchCondition;
import com.jzy.model.dto.echarts.GroupedByGradeObjectTotal;
import com.jzy.model.dto.echarts.GroupedBySubjectObjectTotal;
import com.jzy.model.dto.echarts.GroupedByTypeObjectTotal;
import com.jzy.model.entity.StudentAndClass;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @InterfaceName StudentAndClassMapper.xml
 * @Author JinZhiyun
 * @Description 学生和上课班级dao接口
 * @Date 2019/11/23 18:31
 * @Version 1.0
 **/
public interface StudentAndClassMapper {
    /**
     * 查询指定学员编号和班号的记录数，即当前学员是否报了当前班
     *
     * @param studentId 学员编号
     * @param classId   班号
     * @return 指定学员编号和班号的记录数
     */
    Long countStudentAndClassByStudentIdAndClassId(@Param("studentId") String studentId, @Param("classId") String classId);

    /**
     * 插入一个学生报班记录
     *
     * @param studentAndClassDetailedDto 学员上课信息
     * @return 更新记录数
     */
    long insertOneStudentAndClass(StudentAndClassDetailedDto studentAndClassDetailedDto);

    /**
     * 插入多个学生报班记录
     *
     * @param studentAndClassDetailedDtos 学员上课信息集合
     * @return 更新记录数
     */
    long insertManyStudentAndClasses(List<StudentAndClassDetailedDto> studentAndClassDetailedDtos);

    /**
     * 根据当前学员号和报班班号更新，报班情况
     *
     * @param studentAndClassDetailedDto 学员上课信息
     * @return 更新记录数
     */
    long updateStudentAndClassByStudentIdAndClassId(StudentAndClassDetailedDto studentAndClassDetailedDto);

    /**
     * 返回符合条件的学生上课信息
     *
     * @param condition 查询条件入参
     * @return 学生上课信息集合
     */
    List<StudentAndClassDetailedDto> listStudentAndClasses(StudentAndClassSearchCondition condition);

    /**
     * 根据id查询StudentAndClass
     *
     * @param id 学员上课对象的自增主键id
     * @return 学生上课信息
     */
    StudentAndClass getStudentAndClassById(Long id);

    /**
     * 编辑学员上课信息，由id修改
     *
     * @param studentAndClassDetailedDto 修改后的学员上课信息
     * @return 更新记录数
     */
    long updateStudentAndClassInfo(StudentAndClassDetailedDto studentAndClassDetailedDto);

    /**
     * 删除一个学员上课记录
     *
     * @param id 被删除学员上课的id
     * @return 更新记录数
     */
    long deleteOneStudentAndClassById(Long id);

    /**
     * 根据id删除多个学员上课记录
     *
     * @param ids 学员上课记录id的列表
     * @return 更新记录数
     */
    long deleteManyStudentAndClassesByIds(List<Long> ids);

    /**
     * 根据班级编码查询班级的所有学生及班级的详细信息
     *
     * @param classId 班级编码
     * @return 结果用 {@link StudentAndClassDetailedDto} 的子类 {@link StudentAndClassDetailedWithSubjectsDto} 返回，子类和父类字段的差集都先空着
     */
    List<StudentAndClassDetailedWithSubjectsDto> listStudentAndClassesByClassId(@Param("classId") String classId);

    /**
     * 根据学员号, 当前年份-季度，查询该学生的所有上课详细信息
     *
     * @param condition 学员号, 当前年份-季度等信息
     * @return 所有上课详细信息
     */
    List<StudentAndClassDetailedDto> listStudentAndClassesWithSubjectsByStudentId(StudentAndClassSearchCondition condition);

    /**
     * 查询指定学生在指定季度和助教带的班级中出现的次数
     *
     * @param condition 学员号, 当前年份-季度，助教姓名等信息
     * @return 指定学生出现的次数
     */
    long countStudentAndClassBySeasonAndAssistant(StudentAndClassSearchCondition condition);

    /**
     * 条件删除多个学生上课记录
     *
     * @param condition 输入的查询条件
     * @return 更新记录数
     */
    long deleteStudentAndClassesByCondition(StudentAndClassSearchCondition condition);

    /**
     * 查询指定年级下的学生人数
     *
     * @param condition 年份-季度-校区
     * @return 如，[GroupedByGradeObjectTotal(name=小初衔接, value=100), GroupedByGradeObjectTotal(name=初一, value=200)],
     * 表示小初衔接100人，初一200人
     */
    List<GroupedByGradeObjectTotal> countStudentsGroupByClassGrade(StudentAndClassSearchCondition condition);

    /**
     * 查询指定年级的学生人数
     *
     * @param condition 年份-季度-校区
     * @return 如，[GroupedBySubjectObjectTotal(name=语文, value=100), GroupedByGradeObjectTotal(name=数学, value=200)],
     * 表示语文100人，数学200人
     */
    List<GroupedBySubjectObjectTotal> countStudentsGroupByClassSubject(StudentAndClassSearchCondition condition);

    /**
     * 查询指定班型的学生人数
     *
     * @param condition 年份-季度-校区-年级-学科
     * @return 如，[GroupedBySubjectObjectTotal(name=精进, value=100), GroupedByGradeObjectTotal(name=志高, value=200)],
     *  表示精进100人，志高200人
     */
    List<GroupedByTypeObjectTotal> countStudentsGroupByClassType(StudentAndClassSearchCondition condition);
}
