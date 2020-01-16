package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.model.dto.*;
import com.jzy.model.dto.echarts.GroupedByGradeAndTypeObjectTotal;
import com.jzy.model.dto.echarts.GroupedBySubjectAndTypeObjectTotal;
import com.jzy.model.dto.echarts.GroupedByTypeObjectTotal;
import com.jzy.model.entity.StudentAndClass;
import com.jzy.model.vo.echarts.NamesAndValues;

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
     * @return 对应的学员上课记录
     */
    StudentAndClass getStudentAndClassById(Long id);

    /**
     * 查询指定学员编号和班号的记录数，即当前学员是否报了当前班
     *
     * @param studentId 学员编号
     * @param classId   班号
     * @return 0或1
     */
    Long countStudentAndClassByStudentIdAndClassId(String studentId, String classId);

    /**
     * 插入一个学生报班记录
     *
     * @param studentAndClassDetailedDto 新的学员报班记录
     * @return (更新结果, 更新记录数)
     * 1."failure"：错误入参等异常
     * 2."studentAndClassExist"：学员在当前班的上课记录已存在
     * 3."studentNotExist": 学员不存在
     * 4."classNotExist": 班级不存在
     * 5."success": 更新成功
     */
    UpdateResult insertOneStudentAndClass(StudentAndClassDetailedDto studentAndClassDetailedDto);

    /**
     * 根据当前学员号和报班班号更新，报班情况
     *
     * @param studentAndClassDetailedDto 更新后的学员上课记录
     * @return (更新结果, 更新记录数)
     * 1."failure"：错误入参等异常
     * 2."success": 更新成功
     */
    UpdateResult updateStudentAndClassByStudentIdAndClassId(StudentAndClassDetailedDto studentAndClassDetailedDto);

    /**
     * 根据从excel中读取到的studentAndClassDetailedDtos信息，更新插入多个。根据学员号和班号判断：
     * if 当前学员号和班号组合不存在
     * 执行插入
     * else
     * 根据学员号和班号更新
     *
     * @param studentAndClassDetailedDtos 要更新的学员上课记录
     * @return 更新结果
     */
    DefaultFromExcelUpdateResult insertAndUpdateStudentAndClassesFromExcel(List<StudentAndClassDetailedDto> studentAndClassDetailedDtos);

    /**
     * 查询学员上课信息的ajax交互。其中classYear
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return 分页结果
     */
    PageInfo<StudentAndClassDetailedDto> listStudentAndClasses(MyPage myPage, StudentAndClassSearchCondition condition);

    /**
     * 编辑学员上课信息，由id修改
     *
     * @param studentAndClassDetailedDto 修改后的学员上课信息
     * @return 1."failure"：错误入参等异常
     * 2."studentAndClassExist"：学员在当前班的上课记录已存在
     * 3."studentNotExist": 学员不存在
     * 4."classNotExist": 班级不存在
     * 5."success": 更新成功
     */
    String updateStudentAndClassInfo(StudentAndClassDetailedDto studentAndClassDetailedDto);

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
     * 根据班级编码查询班级的所有学生及班级的详细信息。
     *
     * @param classId 班级编码
     * @return 结果用 {@link StudentAndClassDetailedDto} 的子类 {@link StudentAndClassDetailedWithSubjectsDto} 返回，子类和父类字段的差集都先空着
     */
    List<StudentAndClassDetailedWithSubjectsDto> listStudentAndClassesByClassId(String classId);

    /**
     * 根据班级编码查询班级的所有学生及班级的详细信息，包括该学生在当前班级id所解析到的年份、季度下所修读的所有学科，还有判断当前学生是否是老生。
     *
     * @param classId 班级编码
     * @return 带当前学员所有在读学科的学员上课记录 {@link StudentAndClassDetailedWithSubjectsDto}
     */
    List<StudentAndClassDetailedWithSubjectsDto> listStudentAndClassesWithSubjectsByClassId(String classId);

    /**
     * 条件删除多个学生上课记录
     *
     * @param condition 输入的查询条件
     * @return (更新结果, 更新记录数)
     * 1."failure"：错误入参等异常
     * 2."success": 更新成功
     */
    UpdateResult deleteStudentAndClassesByCondition(StudentAndClassSearchCondition condition);

    /**
     * 查询指定年级下的学生人数。
     *
     * @param condition 年份-季度-校区
     * @return 如: NamesAndValues(names=[小初衔接, 初一, 初二], values=[100, 200, 300])，即小初衔接100人，初一200人....
     */
    NamesAndValues countStudentsGroupByClassGrade(StudentAndClassSearchCondition condition);

    /**
     * 查询指定学科的学生人数
     *
     * @param condition 年份-季度-校区
     * @return 如，NamesAndValues(names=[语文, 数学, 英语], values=[100, 200, 300])，即语文100人，数学200人....
     */
    NamesAndValues countStudentsGroupByClassSubject(StudentAndClassSearchCondition condition);

    /**
     * 查询指定班型的学生人数
     *
     * @param condition 年份-季度-校区
     * @return NamesAndValues(names = [精进, 志高, 行远], values = [100, 200, 300])，即精进100人，志高200人....
     */
    List<GroupedByTypeObjectTotal> countStudentsGroupByClassType(StudentAndClassSearchCondition condition);

    /**
     * 查询指定年级对应人数，以及该年级下各班型对应人数
     *
     * @param condition 年份-季度-校区
     * @return 结果已排序。
     * 如，[GroupedByGradeAndTypeObjectTotal(name=小初衔接, value=1000), groupedByTypeObjectTotals=[GroupedBySubjectObjectTotal(name=精进, value=100), GroupedByGradeObjectTotal(name=志高, value=200)]
     * ,GroupedByGradeAndTypeObjectTotal(name=初一, value=2000), groupedByTypeObjectTotals=[GroupedBySubjectObjectTotal(name=精进, value=300), GroupedByGradeObjectTotal(name=志高, value=400)]]
     * 小初衔接年级共1000人，其中精进班100人，志高班200人；初一年级共2000人，其中精进班300人，志高班400人
     */
    List<GroupedByGradeAndTypeObjectTotal> countStudentsGroupByClassGradeAndType(StudentAndClassSearchCondition condition);

    /**
     * 查询指定学科对应人数，以及该年级下各学科对应人数
     *
     * @param condition 年份-季度-校区
     * @return 结果已排序。
     * 如，[GroupedBySubjectAndTypeObjectTotal(name=语文, value=1000), groupedByTypeObjectTotals=[GroupedBySubjectObjectTotal(name=精进, value=100), GroupedByGradeObjectTotal(name=志高, value=200)]
     * ,GroupedBySubjectAndTypeObjectTotal(name=数学, value=2000), groupedByTypeObjectTotals=[GroupedBySubjectObjectTotal(name=精进, value=300), GroupedByGradeObjectTotal(name=志高, value=400)]]
     * 修读语文共1000人，其中精进班100人，志高班200人；修读数学共2000人，其中精进班300人，志高班400人
     */
    List<GroupedBySubjectAndTypeObjectTotal> countStudentsGroupByClassSubjectAndType(StudentAndClassSearchCondition condition);
}
