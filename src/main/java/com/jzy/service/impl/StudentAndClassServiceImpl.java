package com.jzy.service.impl;

import com.jzy.dao.StudentAndClassMapper;
import com.jzy.manager.constant.Constants;
import com.jzy.model.dto.StudentAndClassDetailedDto;
import com.jzy.service.StudentAndClassService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * @ClassName StudentAndClassServiceImpl
 * @Author JinZhiyun
 * @Description 学生上课业务实现
 * @Date 2019/11/23 18:06
 * @Version 1.0
 **/
@Service
public class StudentAndClassServiceImpl extends AbstractServiceImpl implements StudentAndClassService {
    private final static Logger logger = Logger.getLogger(StudentAndClassServiceImpl.class);

    @Autowired
    private StudentAndClassMapper studentAndClassMapper;

    @Override
    public int countStudentAndClassByStudentIdAndClassId(String studentId, String classId) {
        return (StringUtils.isEmpty(studentId) || StringUtils.isEmpty(classId)) ? 0 : studentAndClassMapper.countStudentAndClassByStudentIdAndClassId(studentId, classId);
    }

    @Override
    public String insertStudentAndClass(StudentAndClassDetailedDto studentAndClassDetailedDto) {
        if (countStudentAndClassByStudentIdAndClassId(studentAndClassDetailedDto.getStudentId(),studentAndClassDetailedDto.getClassId()) >0){
            //重复报班
            return "studentAndClassRepeat";
        }

        studentAndClassMapper.insertStudentAndClass(studentAndClassDetailedDto);
        return Constants.SUCCESS;
    }

    @Override
    public String updateStudentAndClassByStudentIdAndClassId(StudentAndClassDetailedDto studentAndClassDetailedDto) {
        studentAndClassMapper.updateStudentAndClassByStudentIdAndClassId(studentAndClassDetailedDto);
        return Constants.SUCCESS;
    }

    @Override
    public String insertAndUpdateStudentAndClassesFromExcel(List<StudentAndClassDetailedDto> studentAndClassDetailedDtos) throws Exception {
        for (StudentAndClassDetailedDto studentAndClassDetailedDto:studentAndClassDetailedDtos){
            insertAndUpdateOneStudentAndClassFromExcel(studentAndClassDetailedDto);
        }
        return Constants.SUCCESS;
    }

    @Override
    public String insertAndUpdateOneStudentAndClassFromExcel(StudentAndClassDetailedDto studentAndClassDetailedDto) throws Exception {
        if (studentAndClassDetailedDto == null) {
            String msg = "insertAndUpdateOneStudentAndClassFromExcel方法输入studentAndClassDetailedDto为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        int count=countStudentAndClassByStudentIdAndClassId(studentAndClassDetailedDto.getStudentId(),studentAndClassDetailedDto.getClassId());
        if (count>0) {
            //记录已存在，更新
            updateStudentAndClassByStudentIdAndClassId(studentAndClassDetailedDto);
        } else {
            //插入
            insertStudentAndClass(studentAndClassDetailedDto);
        }
        return Constants.SUCCESS;
    }

}
