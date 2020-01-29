package com.jzy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jzy.dao.CampusAndClassroomMapper;
import com.jzy.manager.constant.RedisConstants;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.model.entity.CampusAndClassroom;
import com.jzy.service.CampusAndClassroomService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CampusAndClassroomServiceImpl
 * @Author JinZhiyun
 * @Description 校区及教室业务实现
 * @Date 2019/11/28 10:59
 * @Version 1.0
 **/
@Service
public class CampusAndClassroomServiceImpl extends AbstractServiceImpl implements CampusAndClassroomService {
    private final static Logger logger = LogManager.getLogger(CampusAndClassroomServiceImpl.class);

    /**
     * 校区和教室组合已存在
     */
    private final static String CAMPUS_AND_CLASSROOM_REPEAT = "campusAndClassroomRepeat";

    @Autowired
    private CampusAndClassroomMapper campusAndClassroomMapper;

    @Override
    public boolean isRepeatedCampusAndClassroom(CampusAndClassroom campusAndClassroom) {
        if (campusAndClassroom == null) {
            throw new InvalidParameterException("输入对象不能为空");
        }
        return getByCampusAndClassroom(campusAndClassroom.getCampus(), campusAndClassroom.getClassroom()) != null;
    }

    @Override
    public CampusAndClassroom getCampusAndClassroomById(Long id) {
        return id == null ? null : campusAndClassroomMapper.getCampusAndClassroomById(id);
    }

    @Override
    public List<String> listClassroomsByCampus(String campus) {
        if (StringUtils.isEmpty(campus)) {
            return new ArrayList<>();
        }

        String key = RedisConstants.CLASSROOMS_KEY;
        if (hashOps.hasKey(key, campus)) {
            //缓存中有
            String classroomsJSON = (String) hashOps.get(key, campus);
            return JSONArray.parseArray(classroomsJSON, String.class);
        }
        //缓存中无，从数据库查
        List<String> classrooms = campusAndClassroomMapper.listClassroomsByCampus(campus);
        //添加缓存
        hashOps.put(key, campus, JSON.toJSONString(classrooms));
        return classrooms;
    }

    @Override
    public CampusAndClassroom getByCampusAndClassroom(String campus, String classroom) {
        return (StringUtils.isEmpty(campus) || StringUtils.isEmpty(classroom)) ? null : campusAndClassroomMapper.getByCampusAndClassroom(campus, classroom);
    }

    @Override
    public long deleteCampusAndClassroomsByCampus(String campus) {
        if (StringUtils.isEmpty(campus)) {
            return 0;
        }

        return campusAndClassroomMapper.deleteCampusAndClassroomsByCampus(campus);
    }

    @Override
    public String insertOneCampusAndClassroom(CampusAndClassroom campusAndClassroom) {
        if (campusAndClassroom == null) {
            return FAILURE;
        }
        if (isRepeatedCampusAndClassroom(campusAndClassroom)) {
            //已存在
            return CAMPUS_AND_CLASSROOM_REPEAT;
        }

        campusAndClassroomMapper.insertOneCampusAndClassroom(campusAndClassroom);
        return SUCCESS;
    }
}
