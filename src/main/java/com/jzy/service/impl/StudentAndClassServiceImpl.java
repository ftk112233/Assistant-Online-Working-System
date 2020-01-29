package com.jzy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.dao.StudentAndClassMapper;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.ExcelConstants;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.util.StudentAndClassUtils;
import com.jzy.model.dto.*;
import com.jzy.model.dto.echarts.*;
import com.jzy.model.entity.Class;
import com.jzy.model.entity.StudentAndClass;
import com.jzy.model.vo.echarts.NamesAndValues;
import com.jzy.service.StudentAndClassService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
    private final static Logger logger = LogManager.getLogger(StudentAndClassServiceImpl.class);

    /**
     * 表示学员在当前班的上课记录已存在
     */
    private final static String STUDENT_AND_CLASS_EXIST = "studentAndClassExist";

    /**
     * 表示学员不存在
     */
    private final static String STUDENT_NOT_EXIST = "studentNotExist";

    /**
     * 表示班级不存在
     */
    private final static String CLASS_NOT_EXIST = "classNotExist";

    @Autowired
    private StudentAndClassMapper studentAndClassMapper;

    @Override
    public boolean isRepeatedStudentAndClass(StudentAndClassDetailedDto studentAndClassDetailedDto) {
        if (studentAndClassDetailedDto == null) {
            throw new InvalidParameterException("输入对象不能为空");
        }
        return countStudentAndClassByStudentIdAndClassId(studentAndClassDetailedDto.getStudentId(), studentAndClassDetailedDto.getClassId()) > 0;
    }

    @Override
    public boolean existStudentId(StudentAndClassDetailedDto studentAndClassDetailedDto) {
        if (studentAndClassDetailedDto == null) {
            throw new InvalidParameterException("输入对象不能为空");
        }
        return studentService.getStudentByStudentId(studentAndClassDetailedDto.getStudentId()) != null;
    }

    @Override
    public boolean existClassId(StudentAndClassDetailedDto studentAndClassDetailedDto) {
        if (studentAndClassDetailedDto == null) {
            throw new InvalidParameterException("输入对象不能为空");
        }
        return classService.getClassByClassId(studentAndClassDetailedDto.getClassId()) != null;
    }

    @Override
    public StudentAndClass getStudentAndClassById(Long id) {
        return id == null ? null : studentAndClassMapper.getStudentAndClassById(id);
    }

    @Override
    public Long countStudentAndClassByStudentIdAndClassId(String studentId, String classId) {
        return (StringUtils.isEmpty(studentId) || StringUtils.isEmpty(classId)) ? 0 : studentAndClassMapper.countStudentAndClassByStudentIdAndClassId(studentId, classId);
    }

    @Override
    public UpdateResult insertOneStudentAndClass(StudentAndClassDetailedDto studentAndClassDetailedDto) {
        if (studentAndClassDetailedDto == null) {
            return new UpdateResult(Constants.FAILURE);
        }
        if (isRepeatedStudentAndClass(studentAndClassDetailedDto)) {
            //重复报班
            return new UpdateResult(STUDENT_AND_CLASS_EXIST);
        }

        return insertUnrepeatedStudentAndClass(studentAndClassDetailedDto);
    }


    /**
     * 插入学员号不重复的学员报班信息
     *
     * @param studentAndClassDetailedDto 学员号不重复的学员报班对象
     * @return 1."studentNotExist": 学员不存在
     * 2."classNotExist": 班级不存在
     * 3."success": 更新成功
     */
    private UpdateResult insertUnrepeatedStudentAndClass(StudentAndClassDetailedDto studentAndClassDetailedDto) {
        if (studentAndClassDetailedDto == null) {
            return new UpdateResult(Constants.FAILURE);
        }
        if (!existStudentId(studentAndClassDetailedDto)) {
            //学员号不存在
            return new UpdateResult(STUDENT_NOT_EXIST);
        }

        if (!existClassId(studentAndClassDetailedDto)) {
            //班号不存在
            return new UpdateResult(CLASS_NOT_EXIST);
        }

        UpdateResult result = new UpdateResult(Constants.SUCCESS);
        long count = studentAndClassMapper.insertOneStudentAndClass(studentAndClassDetailedDto);
        result.setInsertCount(count);
        return result;
    }

    @Override
    public UpdateResult updateStudentAndClassByStudentIdAndClassId(StudentAndClassDetailedDto studentAndClassDetailedDto) {
        if (studentAndClassDetailedDto == null) {
            return new UpdateResult(Constants.FAILURE);
        }
        UpdateResult result = new UpdateResult(Constants.SUCCESS);
        long count = studentAndClassMapper.updateStudentAndClassByStudentIdAndClassId(studentAndClassDetailedDto);
        result.setUpdateCount(count);
        return result;
    }

    @Override
    public DefaultFromExcelUpdateResult insertAndUpdateStudentAndClassesFromExcel(List<StudentAndClassDetailedDto> studentAndClassDetailedDtos) {
        if (studentAndClassDetailedDtos == null) {
            String msg = "insertAndUpdateStudentAndClassesFromExcel方法输入studentAndClassDetailedDtos为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        DefaultFromExcelUpdateResult result = new DefaultFromExcelUpdateResult(Constants.SUCCESS);
        String studentIdKeyword = ExcelConstants.STUDENT_ID_COLUMN;
        String classIdKeyword = ExcelConstants.CLASS_ID_COLUMN_2;
        InvalidData invalidData = new InvalidData(studentIdKeyword, classIdKeyword);


        List<StudentAndClassDetailedDto> studentAndClassesToInsert = new ArrayList<>();
        for (StudentAndClassDetailedDto studentAndClassDetailedDto : studentAndClassDetailedDtos) {
            if (StudentAndClassUtils.isValidStudentAndClassDetailedDtoInfo(studentAndClassDetailedDto)) {
                if (isRepeatedStudentAndClass(studentAndClassDetailedDto)) {
                    //记录已存在，更新
                    /*
                    不做是否修改过判断，规范是死的，人是活的。
                    不是说偷懒，而是常见业务场景中，都会开启先删后导，这样的判断也没有太大必要
                     */
                    result.add(updateStudentAndClassByStudentIdAndClassId(studentAndClassDetailedDto));
                } else {
                    //插入
                    studentAndClassesToInsert.add(studentAndClassDetailedDto);
                }
            } else {
                String msg = "输入学生花名册表中读取到的studentAndClassDetailedDtos不合法!";
                logger.error(msg);
                result.setResult(Constants.EXCEL_INVALID_DATA);
                invalidData.putValue(studentIdKeyword, studentAndClassDetailedDto.getStudentId());
                invalidData.putValue(classIdKeyword, studentAndClassDetailedDto.getClassId());
            }
        }


        //插入
        result.add(insertManyUnrepeatedStudentAndClasses(studentAndClassesToInsert));

        result.setInvalidData(invalidData);
        return result;
    }

    /**
     * 插入多个学生报班记录，这些记录的学员号和班号都是不冲突的。
     *
     * @param studentAndClassDetailedDtos 学员上课信息集合
     * @return (更新结果, 更新记录数)
     * 1."failure"：错误入参等异常
     * 2."studentNotExist": 学员不存在
     * 3."classNotExist": 班级不存在
     * 4."success": 更新成功
     */
    private UpdateResult insertManyUnrepeatedStudentAndClasses(List<StudentAndClassDetailedDto> studentAndClassDetailedDtos) {
        if (studentAndClassDetailedDtos == null || studentAndClassDetailedDtos.size() == 0) {
            return new UpdateResult(Constants.FAILURE);
        }

        /*
         *  真正要插入的学生上课记录dtosToInsert。
         *  因为入参集合中可能出现班号或学员号在系统中不存在的记录，所以遍历整体集合跳过这些记录，然后将其他有效的记录添加到dtosToInsert
         */
        List<StudentAndClassDetailedDto> dtosToInsert = new ArrayList<>();
        for (StudentAndClassDetailedDto dto : studentAndClassDetailedDtos) {
            if (dto == null) {
                return new UpdateResult(Constants.FAILURE);
            }

            if (!existStudentId(dto)) {
                //学员号不存在
                continue;
            }

            if (!existClassId(dto)) {
                //班号不存在
                continue;
            }

            dtosToInsert.add(dto);
        }

        UpdateResult result = new UpdateResult(Constants.SUCCESS);
        long count = studentAndClassMapper.insertManyStudentAndClasses(dtosToInsert);
        result.setInsertCount(count);
        return result;
    }

    /**
     * 根据从excel中读取到的studentAndClassDetailedDto信息，更新插入一个。根据学员号和班号判断：
     * if 当前学员号和班号组合不存在
     * 执行插入
     * else
     * 根据学员号和班号更新
     * <p>
     * 对于插入改用批量插入的方式，sql的执行效率更高。详见insertAndUpdateStudentAndClassesFromExcel()具体内容
     *
     * @param studentAndClassDetailedDto 要更新的学员上课记录
     * @return 更新结果
     * @throws InvalidParameterException 不合法的入参异常
     */
    @Deprecated
    private UpdateResult insertAndUpdateOneStudentAndClassFromExcel(StudentAndClassDetailedDto studentAndClassDetailedDto) throws InvalidParameterException {
        if (studentAndClassDetailedDto == null) {
            String msg = "insertAndUpdateOneStudentAndClassFromExcel方法输入studentAndClassDetailedDto为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        UpdateResult result = new UpdateResult();

        if (isRepeatedStudentAndClass(studentAndClassDetailedDto)) {
            //记录已存在，更新
            /*
            不做是否修改过判断，规范是死的，人是活的。
            不是说偷懒，而是常见业务场景中，都会开启先删后导，这样的判断也没有太大必要
             */
            result.add(updateStudentAndClassByStudentIdAndClassId(studentAndClassDetailedDto));
        } else {
            //插入
            result.add(insertUnrepeatedStudentAndClass(studentAndClassDetailedDto));
        }
        result.setResult(Constants.SUCCESS);
        return result;
    }

    @Override
    public PageInfo<StudentAndClassDetailedDto> listStudentAndClasses(MyPage myPage, StudentAndClassSearchCondition condition) {
        PageHelper.startPage(myPage.getPageNum(), myPage.getPageSize());
        List<StudentAndClassDetailedDto> studentAndClassDetailedDtos = studentAndClassMapper.listStudentAndClasses(condition);
        for (int i = 0; i < studentAndClassDetailedDtos.size(); i++) {
            StudentAndClassDetailedDto studentAndClassDetailedDto = studentAndClassDetailedDtos.get(i);
            if (!StringUtils.isEmpty(studentAndClassDetailedDto.getClassYear())) {
                studentAndClassDetailedDto.setClassYear(Class.parseYear(studentAndClassDetailedDto.getClassYear()));
                studentAndClassDetailedDtos.set(i, studentAndClassDetailedDto);
            }
        }
        return new PageInfo<>(studentAndClassDetailedDtos);
    }

    @Override
    public String updateStudentAndClassInfo(StudentAndClassDetailedDto studentAndClassDetailedDto) {
        if (studentAndClassDetailedDto == null) {
            return Constants.FAILURE;
        }
        StudentAndClass originalDto = getStudentAndClassById(studentAndClassDetailedDto.getId());

        //原来的学员编号
        String originalStudentId = studentService.getStudentById(originalDto.getStudentId()).getStudentId();
        String originalClassId = classService.getClassById(originalDto.getClassId()).getClassId();

        if (!studentAndClassDetailedDto.getStudentId().equals(originalStudentId)
                || !studentAndClassDetailedDto.getClassId().equals(originalClassId)) {
            //学员号和班号中的一个修改过了，判断是否与已存在的<学员号, 班号>冲突
            if (isRepeatedStudentAndClass(studentAndClassDetailedDto)) {
                //修改后的上课记录已存在
                return STUDENT_AND_CLASS_EXIST;
            }

            if (!studentAndClassDetailedDto.getStudentId().equals(originalStudentId)) {
                //学员号修改过了
                if (!existStudentId(studentAndClassDetailedDto)) {
                    //学员号不存在
                    return STUDENT_NOT_EXIST;
                }
            }

            if (!studentAndClassDetailedDto.getClassId().equals(originalClassId)) {
                //班号修改过了
                if (!existClassId(studentAndClassDetailedDto)) {
                    //班号不存在
                    return CLASS_NOT_EXIST;
                }
            }
        }

        studentAndClassMapper.updateStudentAndClassInfo(studentAndClassDetailedDto);
        return Constants.SUCCESS;
    }

    @Override
    public long deleteOneStudentAndClassById(Long id) {
        if (id == null) {
            return 0;
        }
        return studentAndClassMapper.deleteOneStudentAndClassById(id);
    }

    @Override
    public long deleteManyStudentAndClassesByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return 0;
        }
        return studentAndClassMapper.deleteManyStudentAndClassesByIds(ids);
    }

    @Override
    public List<StudentAndClassDetailedWithSubjectsDto> listStudentAndClassesByClassId(String classId) {
        return StringUtils.isEmpty(classId) ? new ArrayList<>() : studentAndClassMapper.listStudentAndClassesByClassId(classId);
    }

    @Override
    public List<StudentAndClassDetailedWithSubjectsDto> listStudentAndClassesWithSubjectsByClassId(String classId) {
        List<StudentAndClassDetailedWithSubjectsDto> dtos = listStudentAndClassesByClassId(classId);
        //先查出该班级所有学生
        for (StudentAndClassDetailedWithSubjectsDto dto : dtos) {
            StudentAndClassSearchCondition condition = new StudentAndClassSearchCondition();
            condition.setStudentId(dto.getStudentId());
            //查出当前学生迄今为止所有的上课记录
            List<StudentAndClassDetailedDto> allRecords = studentAndClassMapper.listStudentAndClassesWithSubjectsByStudentId(condition);
            ClassSeasonDto currentSeason = classService.getCurrentClassSeason();
            for (StudentAndClassDetailedDto record : allRecords) {
                ClassSeasonDto recordSeason = new ClassSeasonDto(record.getClassYear(), record.getClassSeason(), record.getClassSubSeason());
                if (recordSeason.compareTo(currentSeason) < 0) {
                    //如果该学生的上课记录中有季度时间节点在当前季度之前的，表示该学生之前上过课，是老生
                    dto.setOldStudent(true);
                    break;
                }
            }

            //===============================================//
            condition.setClassYear(dto.getClassYear());
            condition.setClassSeason(dto.getClassSeason());
            condition.setClassSubSeason(dto.getClassSubSeason());
            //查出当前年份-季度下该学生的所有上课记录
            List<StudentAndClassDetailedDto> recordsInCurrentSeason = studentAndClassMapper.listStudentAndClassesWithSubjectsByStudentId(condition);

            List<String> subjects = new ArrayList<>();
            for (StudentAndClassDetailedDto record : recordsInCurrentSeason) {
                if (!StringUtils.isEmpty(record.getClassSubject())) {
                    subjects.add(record.getClassSubject());
                }
            }
            //读取设置该学生所有修读的学科
            dto.setSubjects(subjects);

            //===============================================//
            condition.setAssistantName(dto.getAssistantName());
            //查出当前季度该学生在当前助教所有班级中出现次数
            long count = studentAndClassMapper.countStudentAndClassBySeasonAndAssistant(condition);
            dto.setCountOfSpecifiedAssistant((int) count);
        }
        return dtos;
    }

    @Override
    public UpdateResult deleteStudentAndClassesByCondition(StudentAndClassSearchCondition condition) {
        if (condition == null) {
            return new UpdateResult(Constants.FAILURE);
        }
        long count = studentAndClassMapper.deleteStudentAndClassesByCondition(condition);
        UpdateResult result = new UpdateResult(Constants.SUCCESS);
        result.setDeleteCount(count);
        return result;
    }

    @Override
    public NamesAndValues countStudentsGroupByClassGrade(StudentAndClassSearchCondition condition) {
        NamesAndValues namesAndValues = new NamesAndValues();
        if (condition == null) {
            return namesAndValues;
        }
        List<GroupedByGradeObjectTotal> objectTotals = studentAndClassMapper.countStudentsGroupByClassGrade(condition);
        namesAndValues.addAll(objectTotals);
        return namesAndValues;
    }

    @Override
    public NamesAndValues countStudentsGroupByClassSubject(StudentAndClassSearchCondition condition) {
        NamesAndValues namesAndValues = new NamesAndValues();
        if (condition == null) {
            return namesAndValues;
        }
        List<GroupedBySubjectObjectTotal> objectTotals = studentAndClassMapper.countStudentsGroupByClassSubject(condition);
        namesAndValues.addAll(objectTotals);
        return namesAndValues;
    }

    @Override
    public List<GroupedByTypeObjectTotal> countStudentsGroupByClassType(StudentAndClassSearchCondition condition) {
        if (condition == null) {
            return new ArrayList<>();
        }
        return studentAndClassMapper.countStudentsGroupByClassType(condition);
    }

    @Override
    public List<GroupedByGradeAndTypeObjectTotal> countStudentsGroupByClassGradeAndType(StudentAndClassSearchCondition condition) {
        if (condition == null) {
            return new ArrayList<>();
        }
        //先查出各年级对应人数
        List<GroupedByGradeObjectTotal> objectTotals = studentAndClassMapper.countStudentsGroupByClassGrade(condition);
        Collections.sort(objectTotals);

        //结果集
        List<GroupedByGradeAndTypeObjectTotal> byGradeAndTypeObjectTotals = new ArrayList<>();
        for (GroupedByGradeObjectTotal objectTotal : objectTotals) {
            //遍历各年级
            condition.setClassGrade(objectTotal.getName());
            //查出当前年级各班型对应人数
            List<GroupedByTypeObjectTotal> byTypeObjectTotals = studentAndClassMapper.countStudentsGroupByClassType(condition);
            Collections.sort(byTypeObjectTotals);

            //把含有当前年级对应人数，以及该年级下各班型对应人数信息的对象添加到结果集
            byGradeAndTypeObjectTotals.add(new GroupedByGradeAndTypeObjectTotal(objectTotal, byTypeObjectTotals));
        }

        return byGradeAndTypeObjectTotals;
    }

    @Override
    public List<GroupedBySubjectAndTypeObjectTotal> countStudentsGroupByClassSubjectAndType(StudentAndClassSearchCondition condition) {
        if (condition == null) {
            return new ArrayList<>();
        }
        //先查出各学科对应人数
        List<GroupedBySubjectObjectTotal> objectTotals = studentAndClassMapper.countStudentsGroupByClassSubject(condition);
        Collections.sort(objectTotals);

        //结果集
        List<GroupedBySubjectAndTypeObjectTotal> bySubjectAndTypeObjectTotals = new ArrayList<>();
        for (GroupedBySubjectObjectTotal objectTotal : objectTotals) {
            //遍历各学科
            condition.setClassSubject(objectTotal.getName());
            //查出当前学科各班型对应人数
            List<GroupedByTypeObjectTotal> byTypeObjectTotals = studentAndClassMapper.countStudentsGroupByClassType(condition);
            Collections.sort(byTypeObjectTotals);

            //把含有当前学科对应人数，以及该学科下各班型对应人数信息的对象添加到结果集
            bySubjectAndTypeObjectTotals.add(new GroupedBySubjectAndTypeObjectTotal(objectTotal, byTypeObjectTotals));
        }

        return bySubjectAndTypeObjectTotals;
    }

}
