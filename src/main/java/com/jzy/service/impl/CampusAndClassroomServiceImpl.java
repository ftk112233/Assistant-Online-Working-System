package com.jzy.service.impl;

import com.jzy.dao.CampusAndClassroomMapper;
import com.jzy.manager.constant.Constants;
import com.jzy.model.entity.CampusAndClassroom;
import com.jzy.service.CampusAndClassroomService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private CampusAndClassroomMapper campusAndClassroomMapper;

    @Override
    public CampusAndClassroom getCampusAndClassroomById(Long id) {
        return id == null ? null : campusAndClassroomMapper.getCampusAndClassroomById(id);
    }

    @Override
    public List<String> listClassroomsByCampus(String campus) {
        return StringUtils.isEmpty(campus)? null :campusAndClassroomMapper.listClassroomsByCampus(campus);
    }

    @Override
    public CampusAndClassroom getByCampusAndClassroom(String campus, String classroom) {
        return (StringUtils.isEmpty(campus) || StringUtils.isEmpty(classroom)) ? null : campusAndClassroomMapper.getByCampusAndClassroom(campus, classroom);
    }

    @Override
    public void deleteCampusAndClassroomsByCampus(String campus) {
        campusAndClassroomMapper.deleteCampusAndClassroomsByCampus(campus);
    }

    @Override
    public String insetCampusAndClassroom(CampusAndClassroom campusAndClassroom) {
        if (getByCampusAndClassroom(campusAndClassroom.getCampus(), campusAndClassroom.getClassroom()) != null) {
            //已存在
            return "campusAndClassroomRepeat";
        }

        campusAndClassroomMapper.insetCampusAndClassroom(campusAndClassroom);
        return Constants.SUCCESS;
    }
}
