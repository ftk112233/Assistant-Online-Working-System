package com.jzy.dao;

import com.jzy.model.dto.StudentAndClassDetailedDto;
import com.jzy.model.dto.StudentAndClassSearchCondition;
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
     * @param classId 班号
     * @return
     */
    Long countStudentAndClassByStudentIdAndClassId(@Param("studentId") String studentId, @Param("classId") String classId);

    /**
     * 插入一个学生报班记录
     *
     * @param studentAndClassDetailedDto
     * @return
     */
    void insertStudentAndClass(StudentAndClassDetailedDto studentAndClassDetailedDto);

    /**
     * 根据当前学员号和报班班号更新，报班情况
     *
     * @param studentAndClassDetailedDto
     * @return
     */
    void updateStudentAndClassByStudentIdAndClassId(StudentAndClassDetailedDto studentAndClassDetailedDto);

    /**
     * 返回符合条件的学生上课信息分页结果
     *
     * @param condition  查询条件入参
     * @return
     */
    List<StudentAndClassDetailedDto> listStudentAndClasses(StudentAndClassSearchCondition condition);

    /**
     * 根据id查询StudentAndClass
     *
     * @param id 学员上课对象的自增主键id
     * @return
     */
    StudentAndClass getStudentAndClassById(Long id);

    /**
     * 编辑学员上课信息，由id修改
     *
     * @param studentAndClassDetailedDto 修改后的学员上课信息
     * @return
     */
    void updateStudentAndClassInfo(StudentAndClassDetailedDto studentAndClassDetailedDto);

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
}
