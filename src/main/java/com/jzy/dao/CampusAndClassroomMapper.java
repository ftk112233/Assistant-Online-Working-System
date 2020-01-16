package com.jzy.dao;

import com.jzy.model.entity.CampusAndClassroom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @InterfaceName CampusAndClassroomMapper
 * @Author JinZhiyun
 * @Description 校区及教室dao接口
 * @Date 2019/11/28 10:59
 * @Version 1.0
 **/
public interface CampusAndClassroomMapper {
    /**
     * 根据id获取校区及教室
     *
     * @param id 主键id
     * @return 校区及教室对象
     */
    CampusAndClassroom getCampusAndClassroomById(@Param("id") Long id);

    /**
     * 删除指定校区的校区教室记录
     *
     * @param campus 校区名称
     * @return 更新记录数
     */
    long deleteCampusAndClassroomsByCampus(@Param("campus") String campus);

    /**
     * 根据校区和教室查询记录
     *
     * @param campus    校区名称
     * @param classroom 教室号
     * @return 校区及教室对象
     */
    CampusAndClassroom getByCampusAndClassroom(@Param("campus") String campus, @Param("classroom") String classroom);

    /**
     * 插入一个校区和教室记录
     *
     * @param campusAndClassroom 要插入的对象
     * @return 更新记录数
     */
    long insertOneCampusAndClassroom(CampusAndClassroom campusAndClassroom);

    /**
     * 根据校区查询相应教室
     *
     * @param campus 校区名称
     * @return 指定校区的所有教室
     */
    List<String> listClassroomsByCampus(@Param("campus") String campus);
}
