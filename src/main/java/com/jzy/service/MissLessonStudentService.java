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
     * @return
     */
    PageInfo<MissLessonStudentDetailedDto> listMissLessonStudents(MyPage myPage, MissLessonStudentSearchCondition condition);

    /**
     * 修改补课学生信息，由id修改
     *
     * @param missLessonStudentDetailedDto 修改后的补课学生信息
     * @return
     */
    String updateMissLessonStudentInfo(MissLessonStudentDetailedDto missLessonStudentDetailedDto);

    /**
     * 添加补课学生
     *
     * @param missLessonStudentDetailedDto 新添加补课学生
     * @return
     */
    String insertMissLessonStudent(MissLessonStudentDetailedDto missLessonStudentDetailedDto);

    /**
     * 删除一个补课学生记录
     *
     * @param id 被删除补课学生的id
     * @return
     */
    void deleteOneMissLessonStudentById(Long id);

    /**
     * 根据id删除多个补课学生记录
     *
     * @param ids 补课学生id的列表
     */
    void deleteManyMissLessonStudentsByIds(List<Long> ids);
}
