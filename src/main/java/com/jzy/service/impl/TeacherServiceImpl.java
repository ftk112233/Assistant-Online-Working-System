package com.jzy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.dao.TeacherMapper;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.ExcelConstants;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.util.TeacherUtils;
import com.jzy.model.dto.*;
import com.jzy.model.entity.Teacher;
import com.jzy.service.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName TeacherServiceImpl
 * @description 教师业务实现
 * @date 2019/11/14 23:29
 **/
@Service
public class TeacherServiceImpl extends AbstractServiceImpl implements TeacherService {
    private final static Logger logger = LogManager.getLogger(TeacherServiceImpl.class);

    /**
     * 表示工号冲突
     */
    private final static String WORK_ID_REPEAT = "workIdRepeat";

    /**
     * 表示姓名冲突
     */
    private final static String NAME_REPEAT = "nameRepeat";

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public boolean isRepeatedTeacherWorkId(Teacher teacher) {
        if (teacher == null) {
            throw new InvalidParameterException("输入对象不能为空");
        }
        return getTeacherByWorkId(teacher.getTeacherWorkId()) != null;
    }

    @Override
    public boolean isRepeatedTeacherName(Teacher teacher) {
        if (teacher == null) {
            throw new InvalidParameterException("输入对象不能为空");
        }
        return getTeacherByName(teacher.getTeacherName()) != null;
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return id == null ? null : teacherMapper.getTeacherById(id);
    }

    @Override
    public Teacher getTeacherByWorkId(String teacherId) {
        return StringUtils.isEmpty(teacherId) ? null : teacherMapper.getTeacherByWorkId(teacherId);
    }

    @Override
    public Teacher getTeacherByName(String teacherName) {
        return StringUtils.isEmpty(teacherName) ? null : teacherMapper.getTeacherByName(teacherName);
    }

    @Override
    public UpdateResult insertOneTeacher(Teacher teacher) {
        if (getTeacherByName(teacher.getTeacherName()) != null) {
            //添加的姓名已存在
            return new UpdateResult(NAME_REPEAT);
        }

        return insertTeacherWithUnrepeatedName(teacher);
    }

    /**
     * 插入姓名不重复的教师信息
     *
     * @param teacher 要插入的教师对象
     * @return (更新结果, 更新记录数)
     */
    private UpdateResult insertTeacherWithUnrepeatedName(Teacher teacher) {
        //新工号不为空
        if (isRepeatedTeacherWorkId(teacher)) {
            //添加的工号已存在
            return new UpdateResult(WORK_ID_REPEAT);
        }

        if (StringUtils.isEmpty(teacher.getTeacherSex())) {
            teacher.setTeacherSex(null);
        }
        long count = teacherMapper.insertOneTeacher(teacher);
        UpdateResult result = new UpdateResult(SUCCESS);
        result.setInsertCount(count);
        return result;
    }

    @Override
    public String updateTeacherByWorkId(Teacher teacher) {
        if (teacher == null) {
            return FAILURE;
        }
        Teacher originalTeacher = getTeacherByWorkId(teacher.getTeacherWorkId());
        return updateTeacherByWorkId(originalTeacher, teacher);
    }

    @Override
    public String updateTeacherByWorkId(Teacher originalTeacher, Teacher newTeacher) {
        if (originalTeacher == null || newTeacher == null) {
            return FAILURE;
        }
        if (isModifiedAndRepeatedTeacherName(originalTeacher, newTeacher)) {
            //姓名修改过了，判断是否与已存在的姓名冲突
            return NAME_REPEAT;
        }

        if (newTeacher.equalsExceptBaseParams(originalTeacher)) {
            //判断输入对象的对应字段是否未做任何修改
            return UNCHANGED;
        }
        teacherMapper.updateTeacherByWorkId(newTeacher);
        return SUCCESS;
    }

    /**
     * 判断当前要更新的教师的姓名是否修改过且重复。
     * 只有相较于原来的教师修改过且与数据库中重复才返回false
     *
     * @param originalTeacher 用来比较的原来的教师
     * @param newTeacher      要更新的教师
     * @return 姓名是否修改过且重复
     */
    private boolean isModifiedAndRepeatedTeacherName(Teacher originalTeacher, Teacher newTeacher) {
        if (!originalTeacher.getTeacherName().equals(newTeacher.getTeacherName())) {
            //姓名修改过了，判断是否与已存在的姓名冲突
            if (isRepeatedTeacherName(newTeacher)) {
                //添加的姓名已存在
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前要更新的教师的工号是否修改过且重复。
     * 只有相较于原来的教师修改过且与数据库中重复才返回false
     *
     * @param originalTeacher 用来比较的原来的教师
     * @param newTeacher      要更新的教师
     * @return 工号是否修改过且重复
     */
    private boolean isModifiedAndRepeatedTeacherWorkId(Teacher originalTeacher, Teacher newTeacher) {
        if (!newTeacher.getTeacherWorkId().equals(originalTeacher.getTeacherWorkId())) {
            //工号修改过了，判断是否与已存在的工号冲突
            if (isRepeatedTeacherWorkId(newTeacher)) {
                //修改后的工号已存在
                return true;
            }
        }
        return false;
    }


    @Override
    public DefaultFromExcelUpdateResult insertAndUpdateTeachersFromExcel(List<Teacher> teachers) {
        if (teachers == null) {
            String msg = "insertAndUpdateTeachersFromExcel方法输入教师teachers为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        DefaultFromExcelUpdateResult result = new DefaultFromExcelUpdateResult(SUCCESS);
        String teacherNameKeyword = ExcelConstants.TEACHER_NAME_COLUMN;
        String classIdKeyword = ExcelConstants.CLASS_ID_COLUMN;
        InvalidData invalidData = new InvalidData(teacherNameKeyword, classIdKeyword);
        for (Teacher teacher : teachers) {
            if (TeacherUtils.isValidTeacherInfo(teacher)) {
                result.add(insertAndUpdateOneTeacherFromExcel(teacher));
            } else {
                String msg = "输入排班表中读取到的teacher不合法!";
                logger.error(msg);
                result.setResult(Constants.EXCEL_INVALID_DATA);
                invalidData.putValue(teacherNameKeyword, teacher.getTeacherName());
                invalidData.putValue(classIdKeyword, null);
            }
        }

        result.setInvalidData(invalidData);
        return result;
    }

    /**
     * 根据从excel中读取到的teachers信息，更新插入一个。根据工号判断：
     * 仅执行插入。由于目前版本从表格只能读取教师姓名字段，所以不用工号做重名校验。只要当前名字不存在，即插入
     *
     * @param teacher 读取到的教师信息
     * @return 更新结果
     * @throws InvalidParameterException 不合法的入参异常
     */
    private UpdateResult insertAndUpdateOneTeacherFromExcel(Teacher teacher) throws InvalidParameterException {
        if (teacher == null) {
            String msg = "insertAndUpdateOneTeacherFromExcel方法输入teacher为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        UpdateResult result = new UpdateResult();

        /*
           TODO
         * 由于目前版本从表格只能读取教师姓名字段，所以不用工号做重名校验。只要当前名字不存在，即插入
         */
        if (!isRepeatedTeacherName(teacher)) {
            long insertCount = insertTeacherWithUnrepeatedName(teacher).getInsertCount();
            result.setInsertCount(insertCount);
        }
        result.setResult(SUCCESS);
        return result;
    }

    @Override
    public PageInfo<Teacher> listTeachers(MyPage myPage, TeacherSearchCondition condition) {
        PageHelper.startPage(myPage.getPageNum(), myPage.getPageSize());
        List<Teacher> teachers = teacherMapper.listTeachers(condition);
        return new PageInfo<>(teachers);
    }

    @Override
    public String updateTeacherInfo(Teacher teacher) {
        if (teacher == null) {
            return FAILURE;
        }
        Teacher originalTeacher = getTeacherById(teacher.getId());
        if (originalTeacher == null) {
            return FAILURE;
        }

        if (!StringUtils.isEmpty(teacher.getTeacherWorkId())) {
            //新工号不为空
            if (isModifiedAndRepeatedTeacherWorkId(originalTeacher, teacher)) {
                //工号修改过了，判断是否与已存在的工号冲突
                return WORK_ID_REPEAT;
            }
        } else {
            teacher.setTeacherWorkId(null);
        }

        if (isModifiedAndRepeatedTeacherName(originalTeacher, teacher)) {
            //姓名修改过了，判断是否与已存在的姓名冲突
            return NAME_REPEAT;

        }

        if (StringUtils.isEmpty(teacher.getTeacherSex())) {
            teacher.setTeacherSex(null);
        }

        if (teacher.equalsExceptBaseParams(originalTeacher)) {
            //判断输入对象的对应字段是否未做任何修改
            return UNCHANGED;
        }

        teacherMapper.updateTeacherInfo(teacher);
        return SUCCESS;
    }

    @Override
    public long deleteOneTeacherById(Long id) {
        if (id == null) {
            return 0;
        }
        return teacherMapper.deleteOneTeacherById(id);
    }

    @Override
    public long deleteManyTeachersByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return 0;
        }
        return teacherMapper.deleteManyTeachersByIds(ids);
    }

    @Override
    public long deleteTeachersByCondition(TeacherSearchCondition condition) {
        return teacherMapper.deleteTeachersByCondition(condition);
    }
}
