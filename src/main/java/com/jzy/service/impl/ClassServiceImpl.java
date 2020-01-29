package com.jzy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.dao.ClassMapper;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.ExcelConstants;
import com.jzy.manager.constant.RedisConstants;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.util.ClassUtils;
import com.jzy.model.dto.*;
import com.jzy.model.entity.Class;
import com.jzy.service.ClassService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    /**
     * 表示班级编码重复
     */
    private final static String CLASS_ID_REPEAT = "classIdRepeat";

    /**
     * 表示教师不存在
     */
    private final static String TEACHER_NOT_EXIST = "teacherNotExist";

    /**
     * 表示助教不存在
     */
    private final static String ASSISTANT_NOT_EXIST = "assistantNotExist";

    @Autowired
    private ClassMapper classMapper;

    @Override
    public boolean isRepeatedClassId(Class clazz) {
        if (clazz == null) {
            throw new InvalidParameterException("输入对象不能为空");
        }
        return getClassByClassId(clazz.getClassId()) != null;
    }

    @Override
    public boolean existClassTeacher(ClassDetailedDto classDetailedDto) {
        if (classDetailedDto == null) {
            throw new InvalidParameterException("输入对象不能为空");
        }

        if (!StringUtils.isEmpty(classDetailedDto.getTeacherName())) {
            //修改后的教师姓名不为空
            if (teacherService.getTeacherByName(classDetailedDto.getTeacherName()) == null) {
                //修改后的教师姓名不存在
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean existClassAssistant(ClassDetailedDto classDetailedDto) {
        if (classDetailedDto == null) {
            throw new InvalidParameterException("输入对象不能为空");
        }

        if (!StringUtils.isEmpty(classDetailedDto.getAssistantName())) {
            //修改后的助教姓名不为空
            if (assistantService.getAssistantByName(classDetailedDto.getAssistantName()) == null) {
                //修改后的助教姓名不存在
                return false;
            }
        }
        return true;
    }

    @Override
    public Class getClassById(Long id) {
        return id == null ? null : classMapper.getClassById(id);
    }

    @Override
    public Class getClassByClassId(String classId) {
        return StringUtils.isEmpty(classId) ? null : classMapper.getClassByClassId(classId);
    }

    @Override
    public List<Class> listClassesLikeClassId(String classId) {
        return StringUtils.isEmpty(classId) ? new ArrayList<>() : classMapper.listClassesLikeClassId(classId);
    }

    @Override
    public ClassDetailedDto getClassDetailByClassId(String classId) {
        if (StringUtils.isEmpty(classId)) {
            return null;
        } else {
            ClassDetailedDto classDetailedDto = classMapper.getClassDetailByClassId(classId);
            if (classDetailedDto != null) {
                classDetailedDto.setParsedClassYear();
            }
            return classDetailedDto;
        }
    }

    @Override
    public ClassDetailedDto getClassDetailById(Long id) {
        if (id == null) {
            return null;
        } else {
            ClassDetailedDto classDetailedDto = classMapper.getClassDetailById(id);
            if (classDetailedDto != null) {
                classDetailedDto.setParsedClassYear();
            }
            return classDetailedDto;
        }
    }

    @Override
    public UpdateResult updateClassByClassId(ClassDetailedDto classDetailedDto) {
        if (classDetailedDto == null) {
            return new UpdateResult(FAILURE);
        }
        long count = classMapper.updateClassByClassId(classDetailedDto);
        UpdateResult result = new UpdateResult(SUCCESS);
        result.setUpdateCount(count);
        return result;
    }

    @Override
    public UpdateResult insertOneClass(ClassDetailedDto classDetailedDto) {
        if (classDetailedDto == null) {
            return new UpdateResult(FAILURE);
        }
        //新班号不为空
        if (isRepeatedClassId(classDetailedDto)) {
            //添加的班号已存在
            return new UpdateResult(CLASS_ID_REPEAT);
        }

        return insertClassWithUnrepeatedClassId(classDetailedDto);
    }

    /**
     * 插入班号不重复的班级信息
     *
     * @param classDetailedDto 新添加班级的信息
     * @return (更新结果 ， 更新记录数)
     * 1."teacherNotExist"：教师不存在
     * 2."assistantNotExist"：助教不存在
     * 3."success": 更新成功
     */
    private UpdateResult insertClassWithUnrepeatedClassId(ClassDetailedDto classDetailedDto) {
        if (classDetailedDto == null) {
            return new UpdateResult(FAILURE);
        }
        if (!existClassTeacher(classDetailedDto)) {
            //修改后的教师姓名不为空，且教师姓名不存在
            return new UpdateResult(TEACHER_NOT_EXIST);
        }

        if (!existClassAssistant(classDetailedDto)) {
            //修改后的助教不为空，且助教姓名不存在
            return new UpdateResult(ASSISTANT_NOT_EXIST);
        }

        classDetailedDto.setParsedClassTime(classDetailedDto.getClassTime());

        long count = classMapper.insertOneClass(classDetailedDto);
        UpdateResult result = new UpdateResult(SUCCESS);
        result.setInsertCount(count);
        return result;
    }

    @Override
    public DefaultFromExcelUpdateResult insertAndUpdateClassesFromExcel(List<ClassDetailedDto> classDetailedDtos) {
        if (classDetailedDtos == null) {
            String msg = "insertAndUpdateClassesFromExcel方法输入classDetailedDto为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        DefaultFromExcelUpdateResult result = new DefaultFromExcelUpdateResult(SUCCESS);
        String teacherNameKeyword = ExcelConstants.TEACHER_NAME_COLUMN;
        String classIdKeyword = ExcelConstants.CLASS_ID_COLUMN;
        InvalidData invalidData = new InvalidData(teacherNameKeyword, classIdKeyword);
        for (ClassDetailedDto classDetailedDto : classDetailedDtos) {
            if (ClassUtils.isValidClassInfo(classDetailedDto)) {
                UpdateResult resultTmp = insertAndUpdateOneClassFromExcel(classDetailedDto);
                result.add(resultTmp);
            } else {
                String msg = "输入助教排班表中读取到的classDetailedDtos不合法！";
                logger.error(msg);
                result.setResult(Constants.EXCEL_INVALID_DATA);
                invalidData.putValue(teacherNameKeyword, classDetailedDto.getTeacherName());
                invalidData.putValue(classIdKeyword, classDetailedDto.getClassId());
            }
        }

        result.setInvalidData(invalidData);
        return result;
    }

    /**
     * 根据从excel中读取到的classDetailedDtos信息，更新插入一个。根据班号判断：
     * if 当前班号不存在
     * 执行插入
     * else
     * 根据班号更新
     *
     * @param classDetailedDto 班级的详细信息
     * @return (更新结果, 更新记录数)
     * @throws InvalidParameterException 不合法的入参异常
     */
    private UpdateResult insertAndUpdateOneClassFromExcel(ClassDetailedDto classDetailedDto) throws InvalidParameterException {
        if (classDetailedDto == null) {
            String msg = "insertAndUpdateOneClassFromExcel方法输入classDetailedDto为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        UpdateResult result = new UpdateResult(SUCCESS);

        ClassDetailedDto originalClass = getClassDetailByClassId(classDetailedDto.getClassId());
        if (originalClass != null) {
            //班号已存在，更新
            if (!originalClass.equalsExceptBaseParamsAndAssistantIdAndTeacherId(classDetailedDto)) {
                long updateCount = updateClassByClassId(classDetailedDto).getUpdateCount();
                result.setUpdateCount(updateCount);
            }
        } else {
            //插入
            long insertCount = insertClassWithUnrepeatedClassId(classDetailedDto).getInsertCount();
            result.setInsertCount(insertCount);
        }

        return result;
    }

    @Override
    public PageInfo<ClassDetailedDto> listClasses(MyPage myPage, ClassSearchCondition condition) {
        PageHelper.startPage(myPage.getPageNum(), myPage.getPageSize());
        List<ClassDetailedDto> classDetailedDtos = classMapper.listClasses(condition);

        ClassSeasonDto classSeason = getCurrentClassSeason();
        Class current = new Class();
        current.setClassYear(classSeason.getClassYear());
        current.setClassSeason(classSeason.getClassSeason());
        current.setClassSubSeason(classSeason.getClassSubSeason());

        for (ClassDetailedDto classDetailedDto : classDetailedDtos) {
            if (!StringUtils.isEmpty(classDetailedDto.getClassYear())) {
                classDetailedDto.setParsedClassYear();
            }

            if (classDetailedDto.getClassroomCapacity() == null || classDetailedDto.getClassStudentsCount() >= classDetailedDto.getClassroomCapacity()) {
                //满班判断
                classDetailedDto.setFull(true);
            }

            if (Class.CLASS_YEAR_SEASON_SUB_SEASON_COMPARATOR_ASC.compare(classDetailedDto, current) < 0) {
                //结课判断
                classDetailedDto.setOver(true);
            }
        }
        return new PageInfo<>(classDetailedDtos);
    }

    @Override
    public List<String> listAllClassIds() {
        return classMapper.listAllClassIds();
    }

    @Override
    public List<String> listClassIdsLikeClassId(String classId) {
        return StringUtils.isEmpty(classId) ? new ArrayList<>() : classMapper.listClassIdsLikeClassId(classId);
    }

    @Override
    public String updateClassInfo(ClassDetailedDto classDetailedDto) {
        if (classDetailedDto == null) {
            return FAILURE;
        }
        ClassDetailedDto originalClass = getClassDetailById(classDetailedDto.getId());
        if (originalClass == null) {
            return FAILURE;
        }

        //班号不为空
        if (isModifiedAndRepeatedClassId(originalClass, classDetailedDto)) {
            //班号修改过了，判断是否与已存在的工号冲突，且修改后的班号已存在
            return CLASS_ID_REPEAT;
        }

        if (!existClassTeacher(classDetailedDto)) {
            //修改后的教师姓名不为空，且教师姓名不存在
            return TEACHER_NOT_EXIST;
        }

        if (!existClassAssistant(classDetailedDto)) {
            //修改后的助教不为空，且助教姓名不存在
            return ASSISTANT_NOT_EXIST;
        }

        classDetailedDto.setParsedClassTime(classDetailedDto.getClassTime());


        if (originalClass.equalsExceptBaseParamsAndAssistantIdAndTeacherId(classDetailedDto)) {
            //判断输入对象的对应字段是否未做任何修改
            return UNCHANGED;
        }

        classMapper.updateClassInfo(classDetailedDto);
        return SUCCESS;
    }

    /**
     * 判断当前要更新的班级的班号是否修改过且重复。
     * 只有相较于原来的班级修改过且与数据库中重复才返回false
     *
     * @param originalClass 用来比较的原来的班级
     * @param newClass      要更新的班级
     * @return 班号是否修改过且重复
     */
    private boolean isModifiedAndRepeatedClassId(Class originalClass, Class newClass) {
        //班号不为空
        if (!newClass.getClassId().equals(originalClass.getClassId())) {
            //班号修改过了，判断是否与已存在的工号冲突
            if (getClassByClassId(newClass.getClassId()) != null) {
                //修改后的班号已存在
                return true;
            }
        }
        return false;
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
        if (ids == null || ids.size() == 0) {
            return 0;
        }
        return classMapper.deleteManyClassesByIds(ids);
    }

    @Override
    public UpdateResult deleteClassesByCondition(ClassSearchCondition condition) {
        if (condition == null) {
            return new UpdateResult(FAILURE);
        }
        long count = classMapper.deleteClassesByCondition(condition);
        UpdateResult result = new UpdateResult(SUCCESS);
        result.setDeleteCount(count);
        return result;
    }

    @Override
    public ClassSeasonDto getCurrentClassSeason() {
        ClassSeasonDto currentClassSeason = new ClassSeasonDto();
        String key = RedisConstants.CURRENT_SEASON_KEY;
        if (redisTemplate.hasKey(key)) {
            //缓存中有
            return (ClassSeasonDto) valueOps.get(key);
        }
        //缓存中无，采用默认策略
        currentClassSeason.setClassYear(ClassUtils.getCurrentYear());
        currentClassSeason.setClassSeason(ClassUtils.getCurrentSeason());
        return currentClassSeason;
    }

    @Override
    public void updateCurrentClassSeason(ClassSeasonDto classSeason) {
        if (classSeason == null) {
            return;
        }
        valueOps.set(RedisConstants.CURRENT_SEASON_KEY, classSeason);
        redisTemplate.expire(RedisConstants.CURRENT_SEASON_KEY, RedisConstants.CURRENT_SEASON_EXPIRE, TimeUnit.DAYS);
    }

    @Override
    public void deleteCurrentClassSeason() {
        redisOperation.expireKey(RedisConstants.CURRENT_SEASON_KEY);
    }
}
