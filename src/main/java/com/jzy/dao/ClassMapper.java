package com.jzy.dao;

import com.jzy.model.dto.ClassDetailedDto;
import com.jzy.model.entity.Class;
import org.apache.ibatis.annotations.Param;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName ClassMapper
 * @description 班级业务dao接口
 * @date 2019/11/13 16:18
 **/
public interface ClassMapper {
    /**
     * 根据班级id查询班级
     *
     * @param id 班级id
     * @return
     */
    Class getClassById(@Param("id") Long id);

    /**
     * 根据班级编码查询班级,，注意这里classId不是主键id
     *
     * @param classId 班级编码
     * @return
     */
    Class getClassByClassId(@Param("classId") String classId);

    /**
     * 修改班级信息由班级编码修改，注意这里classId不是主键id
     *
     * @param classDetailedDto 修改后的班级信息
     * @return
     */
    void updateClassByClassId(ClassDetailedDto classDetailedDto);

    /**
     * 添加班级
     *
     * @param classDetailedDto 新添加班级的信息
     * @return
     */
    void insertClass(ClassDetailedDto classDetailedDto);
}
