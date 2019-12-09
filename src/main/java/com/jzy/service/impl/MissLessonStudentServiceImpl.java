package com.jzy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.dao.MissLessonStudentMapper;
import com.jzy.manager.constant.Constants;
import com.jzy.model.dto.MissLessonStudentDetailedDto;
import com.jzy.model.dto.MissLessonStudentSearchCondition;
import com.jzy.model.dto.MyPage;
import com.jzy.service.MissLessonStudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MissLessonStudentServiceImpl
 * @Author JinZhiyun
 * @Description 补课学生业务实现
 * @Date 2019/11/21 22:02
 * @Version 1.0
 **/
@Service
public class MissLessonStudentServiceImpl extends AbstractServiceImpl implements MissLessonStudentService {
    private final static Logger logger = LogManager.getLogger(MissLessonStudentServiceImpl.class);

    @Autowired
    private MissLessonStudentMapper missLessonStudentMapper;

    @Override
    public PageInfo<MissLessonStudentDetailedDto> listMissLessonStudents(MyPage myPage, MissLessonStudentSearchCondition condition) {
        PageHelper.startPage(myPage.getPageNum(), myPage.getPageSize());
        List<MissLessonStudentDetailedDto> students = missLessonStudentMapper.listMissLessonStudents(condition);
        return new PageInfo<>(students);
    }

    @Override
    public String updateMissLessonStudentInfo(MissLessonStudentDetailedDto missLessonStudentDetailedDto) {
        if (missLessonStudentDetailedDto.getCurrentClassId().equals(missLessonStudentDetailedDto.getOriginalClassId())) {
            //原班级和补课班级相同
            return "theSameClass";
        }

        if (classService.getClassByClassId(missLessonStudentDetailedDto.getOriginalClassId()) == null) {
            //原班号不存在
            return "originalClassNotExist";
        }

        if (classService.getClassByClassId(missLessonStudentDetailedDto.getCurrentClassId()) == null) {
            //补课班号不存在
            return "currentClassNotExist";
        }

        missLessonStudentMapper.updateMissLessonStudentInfo(missLessonStudentDetailedDto);
        return Constants.SUCCESS;
    }

    @Override
    public String insertMissLessonStudent(MissLessonStudentDetailedDto missLessonStudentDetailedDto) {
        if (missLessonStudentDetailedDto.getCurrentClassId().equals(missLessonStudentDetailedDto.getOriginalClassId())) {
            //原班级和补课班级相同
            return "theSameClass";
        }

        if (classService.getClassByClassId(missLessonStudentDetailedDto.getOriginalClassId()) == null) {
            //原班号不存在
            return "originalClassNotExist";
        }

        if (classService.getClassByClassId(missLessonStudentDetailedDto.getCurrentClassId()) == null) {
            //补课班号不存在
            return "currentClassNotExist";
        }

        missLessonStudentMapper.insertMissLessonStudent(missLessonStudentDetailedDto);
        return Constants.SUCCESS;
    }

    @Override
    public long deleteOneMissLessonStudentById(Long id) {
        if (id == null) {
            return 0;
        }
        return missLessonStudentMapper.deleteOneMissLessonStudentById(id);
    }

    @Override
    public long deleteManyMissLessonStudentsByIds(List<Long> ids) {
        if (ids == null ||ids.size() == 0){
            return 0;
        }
        return missLessonStudentMapper.deleteManyMissLessonStudentsByIds(ids);
    }

    @Override
    public String deleteMissLessonStudentsByCondition(MissLessonStudentSearchCondition condition) {
        List<MissLessonStudentDetailedDto> dtos=missLessonStudentMapper.listMissLessonStudents(condition);
        List<Long> ids=new ArrayList<>();
        for (MissLessonStudentDetailedDto dto:dtos){
            ids.add(dto.getId());
        }

        deleteManyMissLessonStudentsByIds(ids);
        return Constants.SUCCESS;
    }
}
