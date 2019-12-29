package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.model.dto.MissLessonStudentDetailedDto;
import com.jzy.model.dto.MissLessonStudentSearchCondition;
import com.jzy.model.dto.MyPage;

import java.util.List;

/**
 * @InterfaceName MissLessonStudentService
 * @Author JinZhiyun
 * @Description 补课学生业务
 * @Date 2019/11/21 22:01
 * @Version 1.0
 **/
public interface MissLessonStudentService {
    /**
     * 查询补课学员信息
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return 分页结果
     */
    PageInfo<MissLessonStudentDetailedDto> listMissLessonStudents(MyPage myPage, MissLessonStudentSearchCondition condition);

    /**
     * 修改补课学生信息，由id修改
     *
     * @param missLessonStudentDetailedDto 修改后的补课学生信息
     * @return
     * 1."failure"：错误入参等异常
     * 2."originalClassNotExist"：原班号不存在
     * 3."currentClassNotExist"：补课班号不存在
     * 4."success": 更新成功
     */
    String updateMissLessonStudentInfo(MissLessonStudentDetailedDto missLessonStudentDetailedDto);

    /**
     * 添加补课学生
     *
     * @param missLessonStudentDetailedDto 新添加补课学生
     * @return
     * 1."failure"：错误入参等异常
     * 2."originalClassNotExist"：原班号不存在
     * 3."currentClassNotExist"：补课班号不存在
     * 4."success": 更新成功
     */
    String insertMissLessonStudent(MissLessonStudentDetailedDto missLessonStudentDetailedDto);

    /**
     * 删除一个补课学生记录
     *
     * @param id 被删除补课学生的id
     * @return 更新记录数
     */
    long deleteOneMissLessonStudentById(Long id);

    /**
     * 根据id删除多个补课学生记录
     *
     * @param ids 补课学生id的列表
     * @return 更新记录数
     */
    long deleteManyMissLessonStudentsByIds(List<Long> ids);

    /**
     * 条件删除多个学补课生记录
     *
     * @param condition 输入的查询条件
     * @return 更新记录数
     */
    long deleteMissLessonStudentsByCondition(MissLessonStudentSearchCondition condition);
}
