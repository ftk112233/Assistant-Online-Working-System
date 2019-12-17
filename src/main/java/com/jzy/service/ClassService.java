package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.model.dto.*;
import com.jzy.model.entity.Class;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName ClassService
 * @description 班级业务
 * @date 2019/11/14 23:30
 **/
public interface ClassService {
    /**
     * 根据班级id查询班级
     *
     * @param id 班级id
     * @return
     */
    Class getClassById(Long id);

    /**
     * 根据班级编码查询班级,注意这里classId不是主键id
     *
     * @param classId 班级编码
     * @return
     */
    Class getClassByClassId(String classId);

    /**
     * 根据班级编码模糊查询班级,注意这里classId不是主键id
     *
     * @param classId 班级编码
     * @return
     */
    List<Class> listClassesLikeClassId(String classId);

    /**
     * 根据班级编码查询班级详细信息，注意这里classId不是主键id
     *
     * @param classId 班级编码
     * @return
     */
    ClassDetailedDto getClassDetailByClassId(String classId);

    /**
     * 修改班级信息由班级编码修改，注意这里classId不是主键id
     *
     * @param classDetailedDto 修改后的班级信息
     * @return
     */
    UpdateResult updateClassByClassId(ClassDetailedDto classDetailedDto);

    /**
     * 添加班级
     *
     * @param classDetailedDto 新添加班级的信息
     * @return
     */
    UpdateResult insertClass(ClassDetailedDto classDetailedDto);

    /**
     * 根据从excel中读取到的classDetailedDtos信息，更新插入多个。根据班号判断：
     * if 当前班号不存在
     * 执行插入
     * else
     * 根据班号更新
     *
     * @param classDetailedDtos
     * @return
     */
    UpdateResult insertAndUpdateClassesFromExcel(List<ClassDetailedDto> classDetailedDtos) throws Exception;

    /**
     * 根据从excel中读取到的classDetailedDtos信息，更新插入一个。根据班号判断：
     * if 当前班号不存在
     * 执行插入
     * else
     * 根据班号更新
     *
     * @param classDetailedDto
     * @return
     */
    UpdateResult insertAndUpdateOneClassFromExcel(ClassDetailedDto classDetailedDto) throws Exception;

    /**
     * 查询班级信息的ajax交互
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    PageInfo<ClassDetailedDto> listClasses(MyPage myPage, ClassSearchCondition condition);

    /**
     * 返回所有的班级编码
     *
     * @param
     * @return
     */
    List<String> listAllClassIds();

    /**
     * 根据班级编码模糊查询返回匹配的班级编码,注意这里classId不是主键id
     *
     * @param classId 班级编码
     * @return
     */
    List<String> listClassIdsLikeClassId(String classId);

    /**
     * 修改班级信息请求，由id修改
     *
     * @param classDetailedDto 修改后的班级信息
     * @return
     */
    String updateClassInfo(ClassDetailedDto classDetailedDto);

    /**
     * 删除一个班级
     *
     * @param id 被删除班级的id
     */
    long deleteOneClassById(Long id);

    /**
     * 根据id删除多个班级
     *
     * @param ids 班级id的列表
     */
    long deleteManyClassesByIds(List<Long> ids);

    /**
     * 根据输入条件删除指定的班级
     *
     * @param condition 输入条件封装
     * @return
     */
    UpdateResult deleteClassesByCondition(ClassSearchCondition condition);

    /**
     * 获得当前开课的年份-季度-分期
     *      如有缓存，从缓存中取；无则返回默认的智能选择结果
     * @return
     */
    CurrentClassSeason getCurrentClassSeason();
}
