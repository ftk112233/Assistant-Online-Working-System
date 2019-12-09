package com.jzy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.dao.ClassMapper;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.util.ClassUtils;
import com.jzy.model.dto.ClassDetailedDto;
import com.jzy.model.dto.ClassSearchCondition;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.UpdateResult;
import com.jzy.model.entity.Class;
import com.jzy.service.ClassService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName ClassServiceImpl
 * @description 班级业务实现
 * @date 2019/11/14 23:30
 **/
@Service
public class ClassServiceImpl extends AbstractServiceImpl implements ClassService {
    private final static Logger logger = LogManager.getLogger(ClassServiceImpl.class);

    @Autowired
    private ClassMapper classMapper;

    @Override
    public Class getClassById(Long id) {
        return id == null ? null : classMapper.getClassById(id);
    }

    @Override
    public Class getClassByClassId(String classId) {
        return StringUtils.isEmpty(classId) ? null : classMapper.getClassByClassId(classId);
    }

    @Override
    public ClassDetailedDto getClassDetailByClassId(String classId) {
        if (StringUtils.isEmpty(classId)) {
            return null;
        } else {
            ClassDetailedDto classDetailedDto = classMapper.getClassDetailByClassId(classId);
            classDetailedDto.setParsedClassYear();
            return classDetailedDto;
        }
    }

    @Override
    public UpdateResult updateClassByClassId(ClassDetailedDto classDetailedDto) {
        long count=classMapper.updateClassByClassId(classDetailedDto);
        UpdateResult result=new UpdateResult(Constants.SUCCESS);
        result.setUpdateCount(count);
        return result;
    }

    @Override
    public UpdateResult insertClass(ClassDetailedDto classDetailedDto) {
        //新班号不为空
        if (getClassByClassId(classDetailedDto.getClassId()) != null) {
            //添加的班号已存在
            return new UpdateResult("classIdRepeat");
        }

        return insertClassWithUnrepeatedClassId(classDetailedDto);
    }


    /**
     * 插入班号不重复的班级信息
     *
     * @param classDetailedDto
     * @return
     */
    private UpdateResult insertClassWithUnrepeatedClassId(ClassDetailedDto classDetailedDto) {
        if (!StringUtils.isEmpty(classDetailedDto.getTeacherName())) {
            //修改后的教师姓名不为空
            if (teacherService.getTeacherByName(classDetailedDto.getTeacherName()) == null) {
                //修改后的教师姓名不存在
                return new UpdateResult("teacherNotExist");

            }
        }

        if (!StringUtils.isEmpty(classDetailedDto.getAssistantName())) {
            //修改后的助教姓名不为空
            if (assistantService.getAssistantByName(classDetailedDto.getAssistantName()) == null) {
                //修改后的助教姓名不存在
                return new UpdateResult("assistantNotExist");
            }
        }

        classDetailedDto.setParsedClassTime(classDetailedDto.getClassTime());

        long count=classMapper.insertClass(classDetailedDto);
        UpdateResult result=new UpdateResult(Constants.SUCCESS);
        result.setInsertCount(count);
        return result;
    }

    @Override
    public UpdateResult insertAndUpdateClassesFromExcel(List<ClassDetailedDto> classDetailedDtos) throws Exception {
        UpdateResult result=new UpdateResult();
        for (ClassDetailedDto classDetailedDto : classDetailedDtos) {
            if (ClassUtils.isValidClassInfo(classDetailedDto)) {
                UpdateResult resultTmp=insertAndUpdateOneClassFromExcel(classDetailedDto);
                result.add(resultTmp);
            } else {
                String msg = "输入助教排班表中读取到的classDetailedDtos不合法！";
                logger.error(msg);
                throw new InvalidParameterException(msg);
            }
        }
        result.setResult(Constants.SUCCESS);
        return result;
    }

    @Override
    public UpdateResult insertAndUpdateOneClassFromExcel(ClassDetailedDto classDetailedDto) throws Exception {
        if (classDetailedDto == null) {
            String msg = "insertAndUpdateOneClassFromExcel方法输入classDetailedDto为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        UpdateResult result=new UpdateResult(Constants.SUCCESS);

        Class originalClasses = getClassByClassId(classDetailedDto.getClassId());
        if (originalClasses != null) {
            //班号已存在，更新
            long updateCount=updateClassByClassId(classDetailedDto).getUpdateCount();
            result.setUpdateCount(updateCount);
        } else {
            //插入
            long insertCount=insertClassWithUnrepeatedClassId(classDetailedDto).getInsertCount();
            result.setInsertCount(insertCount);
        }

        return result;
    }

    @Override
    public PageInfo<ClassDetailedDto> listClasses(MyPage myPage, ClassSearchCondition condition) {
        PageHelper.startPage(myPage.getPageNum(), myPage.getPageSize());
        List<ClassDetailedDto> classDetailedDtos = classMapper.listClasses(condition);
        for (int i = 0; i < classDetailedDtos.size(); i++) {
            ClassDetailedDto classDetailedDto = classDetailedDtos.get(i);
            if (!StringUtils.isEmpty(classDetailedDto.getClassYear())) {
                classDetailedDto.setParsedClassYear();
                classDetailedDtos.set(i, classDetailedDto);
            }

            if (classDetailedDto.getClassStudentsCount() >= classDetailedDto.getClassroomCapacity()){
                //慢班判断
                classDetailedDto.setFull(true);
            }
        }
        return new PageInfo<>(classDetailedDtos);
    }

    @Override
    public List<String> listAllClassIds() {
        return classMapper.listAllClassIds();
    }

    @Override
    public String updateClassInfo(ClassDetailedDto classDetailedDto) {
        Class originalClass = getClassById(classDetailedDto.getId());

        //班号不为空
        if (!classDetailedDto.getClassId().equals(originalClass.getClassId())) {
            //班号修改过了，判断是否与已存在的工号冲突
            if (getClassByClassId(classDetailedDto.getClassId()) != null) {
                //修改后的班号已存在
                return "classIdRepeat";
            }
        }

        if (!StringUtils.isEmpty(classDetailedDto.getTeacherName())) {
            //修改后的教师姓名不为空
            if (teacherService.getTeacherByName(classDetailedDto.getTeacherName()) == null) {
                //修改后的教师姓名不存在
                return "teacherNotExist";
            }
        }

        if (!StringUtils.isEmpty(classDetailedDto.getAssistantName())) {
            //修改后的助教姓名不为空
            if (assistantService.getAssistantByName(classDetailedDto.getAssistantName()) == null) {
                //修改后的助教姓名不存在
                return "assistantNotExist";
            }
        }

        classDetailedDto.setParsedClassTime(classDetailedDto.getClassTime());
        classMapper.updateClassInfo(classDetailedDto);
        return Constants.SUCCESS;
    }

    @Override
    public long deleteOneClassById(Long id) {
        if (id == null) {
            return 0;
        }
        return classMapper.deleteOneClassById(id);
    }

    @Override
    public long deleteManyClassesByIds(List<Long> ids) {
        if (ids == null ||ids.size() == 0){
            return 0;
        }
        return classMapper.deleteManyClassesByIds(ids);
    }

    @Override
    public UpdateResult deleteClassesByCondition(ClassSearchCondition condition) {
        long count=classMapper.deleteClassesByCondition(condition);
        UpdateResult result=new UpdateResult(Constants.SUCCESS);
        result.setDeleteCount(count);
        return result;
    }
}
