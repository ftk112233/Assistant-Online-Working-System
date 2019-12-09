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
     * @param id
     * @return
     */
    CampusAndClassroom getCampusAndClassroomById(@Param("id") Long id);

    /**
     * 删除指定校区的校区教室记录
     *
     * @param campus 校区名称
     * @return
     */
    long deleteCampusAndClassroomsByCampus(@Param("campus") String campus);

    /**
     * 根据校区和教室查询记录
     *
     * @param campus    校区名称
     * @param classroom 教室号
     * @return
     */
    CampusAndClassroom getByCampusAndClassroom(@Param("campus") String campus, @Param("classroom") String classroom);

    /**
     * 插入一个校区和教室记录
     *
     * @param campusAndClassroom 要插入的对象
     */
    long insertCampusAndClassroom(CampusAndClassroom campusAndClassroom);

    /**
     * 根据校区查询相应教室
     *
     * @param campus 校区名称
     * @return
     */
    List<String> listClassroomsByCampus(@Param("campus") String campus);
}
