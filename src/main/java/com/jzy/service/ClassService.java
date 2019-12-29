package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.manager.exception.InvalidParameterException;
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
     * @return 对应的班级对象
     */
    Class getClassById(Long id);

    /**
     * 根据班级编码查询班级,注意这里classId是班号不是主键id
     *
     * @param classId 班级编码
     * @return 对应的班级对象
     */
    Class getClassByClassId(String classId);

    /**
     * 根据班级编码模糊查询班级,注意这里classId是班号不是主键id
     *
     * @param classId 班级编码
     * @return 所有匹配的班级对象
     */
    List<Class> listClassesLikeClassId(String classId);

    /**
     * 根据班级编码查询班级详细信息，注意这里classId是班号不是主键id
     *
     * @param classId 班级编码
     * @return 班级的详细信息
     */
    ClassDetailedDto getClassDetailByClassId(String classId);

    /**
     * 根据班级id查询班级详细信息，注意这里是主键id
     *
     * @param id 班级id
     * @return 班级详细信息
     */
    ClassDetailedDto getClassDetailById(Long id);

    /**
     * 修改班级信息由班级编码修改，注意这里classId不是主键id
     *
     * @param classDetailedDto 修改后的班级信息
     * @return (更新结果, 更新记录数)
     * 1."failure"：错误入参等异常
     * 2."success": 更新成功
     */
    UpdateResult updateClassByClassId(ClassDetailedDto classDetailedDto);

    /**
     * 添加班级
     *
     * @param classDetailedDto 新添加班级的信息
     * @return (更新结果, 更新记录数)
     * 1."failure"：错误入参等异常
     * 2."classIdRepeat"：班号冲突
     * 3."teacherNotExist"：教师不存在
     * 4."assistantNotExist"：助教不存在
     * 5."success": 更新成功
     */
    UpdateResult insertClass(ClassDetailedDto classDetailedDto);

    /**
     * 根据从excel中读取到的classDetailedDtos信息，更新插入多个。根据班号判断：
     * if 当前班号不存在
     * 执行插入
     * else
     * 根据班号更新
     *
     * @param classDetailedDtos 班级的详细信息
     * @return (更新结果, 更新记录数)
     */
    UpdateResult insertAndUpdateClassesFromExcel(List<ClassDetailedDto> classDetailedDtos) throws InvalidParameterException;

    /**
     * 查询班级信息的ajax交互。
     * 其中慢班的判断，结课与否的判断在服务层完成。结课与否取决于当前缓存中的校历和班级的“年份-季度-分期”的比较结果
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return 分页结果
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
     * @return 1."failure"：错误入参等异常
     * 2."classIdRepeat"：班号冲突
     * 3."teacherNotExist"：教师不存在
     * 4."assistantNotExist"：助教不存在
     * 5."unchanged": 对比数据库原记录未做任何修改
     * 6."success": 更新成功
     */
    String updateClassInfo(ClassDetailedDto classDetailedDto);

    /**
     * 删除一个班级
     *
     * @param id 被删除班级的id
     * @return 更新记录数
     */
    long deleteOneClassById(Long id);

    /**
     * 根据id删除多个班级
     *
     * @param ids 班级id的列表
     * @return 更新记录数
     */
    long deleteManyClassesByIds(List<Long> ids);

    /**
     * 根据输入条件删除指定的班级
     *
     * @param condition 输入条件封装
     * @return (更新结果, 更新记录数)
     * 1."failure"：错误入参等异常
     * 2."success"：删除成功
     */
    UpdateResult deleteClassesByCondition(ClassSearchCondition condition);

    /**
     * 获得当前开课的年份-季度-分期
     * 如有缓存，从缓存中取；
     * 无则返回默认的智能选择结果（根据年份月份判断）
     *
     * @return 年份-季度-分期的封装对象
     */
    CurrentClassSeason getCurrentClassSeason();

    /**
     * 修改当前校历
     */
    void updateCurrentClassSeason(CurrentClassSeason classSeason);

    /**
     * 从缓存中清除当前校历
     */
    void deleteCurrentClassSeason();
}
