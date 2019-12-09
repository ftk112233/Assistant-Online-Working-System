package com.jzy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.dao.TeacherMapper;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.util.TeacherUtils;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.TeacherSearchCondition;
import com.jzy.model.dto.UpdateResult;
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

    @Autowired
    private TeacherMapper teacherMapper;

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
    public UpdateResult insertTeacher(Teacher teacher) {
        if (getTeacherByName(teacher.getTeacherName()) != null) {
            //添加的姓名已存在
            return new UpdateResult("nameRepeat");
        }

        return insertTeacherWithUnrepeatedName(teacher);
    }

    /**
     * 插入姓名不重复的教师信息
     *
     * @param teacher
     * @return
     */
    private UpdateResult insertTeacherWithUnrepeatedName(Teacher teacher) {
        //新工号不为空
        if (getTeacherByWorkId(teacher.getTeacherWorkId()) != null) {
            //添加的工号已存在
            return new UpdateResult("workIdRepeat");
        }

        if (StringUtils.isEmpty(teacher.getTeacherSex())) {
            teacher.setTeacherSex(null);
        }
        long count=teacherMapper.insertTeacher(teacher);
        UpdateResult result=new UpdateResult(Constants.SUCCESS);
        result.setInsertCount(count);
        return result;
    }

    @Override
    public String updateTeacherByWorkId(Teacher teacher) {
        Teacher originalTeacher = getTeacherByWorkId(teacher.getTeacherWorkId());
        return updateTeacherByWorkId(originalTeacher, teacher);
    }

    @Override
    public String updateTeacherByWorkId(Teacher originalTeacher, Teacher newTeacher) {
        if (!originalTeacher.getTeacherName().equals(newTeacher.getTeacherName())) {
            //姓名修改过了，判断是否与已存在的姓名冲突
            if (getTeacherByName(newTeacher.getTeacherName()) != null) {
                //添加的姓名已存在
                return "nameRepeat";
            }
        }

        teacherMapper.updateTeacherByWorkId(newTeacher);
        return Constants.SUCCESS;
    }

    @Override
    public UpdateResult insertAndUpdateTeachersFromExcel(List<Teacher> teachers) throws Exception {
        UpdateResult result=new UpdateResult();
        for (Teacher teacher : teachers) {
            if (TeacherUtils.isValidTeacherInfo(teacher)) {
                UpdateResult resultTmp=insertAndUpdateOneTeacherFromExcel(teacher);
                result.add(resultTmp);
            } else {
                String msg = "输入排班表中读取到的teacher不合法!";
                logger.error(msg);
                throw new InvalidParameterException(msg);
            }
        }
        result.setResult(Constants.SUCCESS);
        return result;
    }

    @Override
    public UpdateResult insertAndUpdateOneTeacherFromExcel(Teacher teacher) throws Exception {
        if (teacher == null) {
            String msg = "insertAndUpdateOneTeacherFromExcel方法输入teacher为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        UpdateResult result=new UpdateResult();

        /**
         * 由于目前版本从表格只能读取教师姓名字段，所以不用工号做重名校验。只要当前名字不存在，即插入
         */
        if (getTeacherByName(teacher.getTeacherName()) == null) {
            long insertCount=insertTeacherWithUnrepeatedName(teacher).getInsertCount();
            result.setInsertCount(insertCount);
        }
        result.setResult(Constants.SUCCESS);
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
        Teacher originalTeacher = getTeacherById(teacher.getId());

        if (!StringUtils.isEmpty(teacher.getTeacherWorkId())) {
            //新工号不为空
            if (!teacher.getTeacherWorkId().equals(originalTeacher.getTeacherWorkId())) {
                //工号修改过了，判断是否与已存在的工号冲突
                if (getTeacherByWorkId(teacher.getTeacherWorkId()) != null) {
                    //修改后的工号已存在
                    return "workIdRepeat";
                }
            }
        } else {
            teacher.setTeacherWorkId(null);
        }

        if (!teacher.getTeacherName().equals(originalTeacher.getTeacherName())) {
            //姓名修改过了，判断是否与已存在的姓名冲突
            if (getTeacherByName(teacher.getTeacherName()) != null) {
                //修改后的姓名已存在
                return "nameRepeat";
            }
        }

        if (StringUtils.isEmpty(teacher.getTeacherSex())) {
            teacher.setTeacherSex(null);
        }

        teacherMapper.updateTeacherInfo(teacher);
        return Constants.SUCCESS;
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
        if (ids == null ||ids.size() == 0){
            return 0;
        }
        return teacherMapper.deleteManyTeachersByIds(ids);
    }

    @Override
    public String deleteTeachersByCondition(TeacherSearchCondition condition) {
        teacherMapper.deleteTeachersByCondition(condition);
        return Constants.SUCCESS;
    }
}
