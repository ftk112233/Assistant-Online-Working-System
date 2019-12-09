package com.jzy.service;

import com.jzy.model.entity.CampusAndClassroom;

import java.util.List;

/**
 * @InterfaceName CampusAndClassroomService
 * @Author JinZhiyun
 * @Description 校区及教室
 * @Date 2019/11/28 10:58
 * @Version 1.0
 **/
public interface CampusAndClassroomService {
    /**
     * 根据id获取校区及教室
     *
     * @param id
     * @return
     */
    CampusAndClassroom getCampusAndClassroomById(Long id);

    /**
     * 根据校区查询相应教室
     *  先从缓存查，如果没有查数据库并设置缓存
     *
     * @param campus 校区名称
     * @return
     */
    List<String> listClassroomsByCampus(String campus);

    /**
     * 根据校区和教室查询记录
     *
     * @param campus    校区名称
     * @param classroom 教室号
     * @return
     */
    CampusAndClassroom getByCampusAndClassroom(String campus, String classroom);


    /**
     * 删除指定校区的校区教室记录
     *
     * @param campus 校区名称
     * @return
     */
    long deleteCampusAndClassroomsByCampus(String campus);

    /**
     * 插入一个校区和教室记录
     *
     * @param campusAndClassroom 要插入的对象
     * @return
     */
    String insertCampusAndClassroom(CampusAndClassroom campusAndClassroom);
}
