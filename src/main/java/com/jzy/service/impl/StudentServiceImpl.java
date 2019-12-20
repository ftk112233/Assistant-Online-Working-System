package com.jzy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.dao.StudentMapper;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.util.StudentUtils;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.StudentSearchCondition;
import com.jzy.model.dto.UpdateResult;
import com.jzy.model.entity.Student;
import com.jzy.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName StudentServiceImpl
 * @description 学生业务
 * @date 2019/11/14 23:31
 **/
@Service
public class StudentServiceImpl extends AbstractServiceImpl implements StudentService {
    private final static Logger logger = LogManager.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student getStudentById(Long id) {
        return id == null ? null : studentMapper.getStudentById(id);
    }

    @Override
    public Student getStudentByStudentId(String studentId) {
        return StringUtils.isEmpty(studentId) ? null : studentMapper.getStudentByStudentId(studentId);
    }

    @Override
    public UpdateResult updateStudentByStudentId(Student student) {
        if (student == null){
            return new UpdateResult(Constants.FAILURE);
        }
        if (StringUtils.isEmpty(student.getStudentSex())) {
            student.setStudentSex(null);
        }
        long count = studentMapper.updateStudentByStudentId(student);
        UpdateResult result = new UpdateResult();
        result.setUpdateCount(count);
        result.setResult(Constants.SUCCESS);
        return result;
    }

    @Override
    public UpdateResult updateStudentNameAndPhoneByStudentId(Student student) {
        if (student == null){
            return new UpdateResult(Constants.FAILURE);
        }
        long count = studentMapper.updateStudentNameAndPhoneByStudentId(student);
        UpdateResult result = new UpdateResult();
        result.setUpdateCount(count);
        result.setResult(Constants.SUCCESS);
        return result;
    }

    @Override
    public UpdateResult updateStudentSchoolByStudentId(Student student) {
        if (student == null){
            return new UpdateResult(Constants.FAILURE);
        }
        long count = studentMapper.updateStudentSchoolByStudentId(student);
        UpdateResult result = new UpdateResult();
        result.setUpdateCount(count);
        result.setResult(Constants.SUCCESS);
        return result;
    }

    @Override
    public UpdateResult insertStudent(Student student) {
        if (student == null){
            return new UpdateResult(Constants.FAILURE);
        }
        if (getStudentByStudentId(student.getStudentId()) != null) {
            //添加的学号号已存在
            return new UpdateResult("studentIdRepeat");
        }

        return insertStudentWithUnrepeatedStudentId(student);
    }

    /**
     * 插入不重复的学员信息
     *
     * @param student
     * @return
     */
    private UpdateResult insertStudentWithUnrepeatedStudentId(Student student) {
        if (student == null){
            return new UpdateResult(Constants.FAILURE);
        }
        UpdateResult result = new UpdateResult();
        if (StringUtils.isEmpty(student.getStudentSex())) {
            student.setStudentSex(null);
        }

        long count = studentMapper.insertStudent(student);
        result.setInsertCount(count);
        result.setResult(Constants.SUCCESS);
        return result;
    }

    @Override
    public UpdateResult insertAndUpdateStudentsDetailedFromExcel(List<Student> students) throws Exception {
        UpdateResult result = new UpdateResult();
        for (Student student : students) {
            if (StudentUtils.isValidStudentInfo(student)) {
                result.add(insertAndUpdateOneStudentDetailedFromExcel(student));
            } else {
                String msg = "学生花名册读取到的student不合法!";
                logger.error(msg);
                throw new InvalidParameterException(msg);
            }
        }
        result.setResult(Constants.SUCCESS);
        return result;
    }

    @Override
    public UpdateResult insertAndUpdateOneStudentDetailedFromExcel(Student student) throws Exception {
        if (student == null) {
            String msg = "insertAndUpdateOneStudentFromExcel方法输入学生student为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        UpdateResult result = new UpdateResult(Constants.SUCCESS);

        Student originalStudent = getStudentByStudentId(student.getStudentId());
        if (originalStudent != null) {
            //学员编号已存在，更新
            if (!StringUtils.equals(originalStudent.getStudentName(), student.getStudentName())
                    || !StringUtils.equals(originalStudent.getStudentPhone(), student.getStudentPhone())
                    || !StringUtils.equals(originalStudent.getStudentPhoneBackup(), student.getStudentPhoneBackup())) {
                //有修改
                result.add(updateStudentNameAndPhoneByStudentId(student));
            }
        } else {
            result.add(insertStudentWithUnrepeatedStudentId(student));
        }
        return result;
    }

    @Override
    public UpdateResult insertAndUpdateStudentsFromExcel(List<Student> students) throws Exception {
        UpdateResult result = new UpdateResult();
        for (Student student : students) {
            if (StudentUtils.isValidStudentInfo(student)) {
                UpdateResult resultTmp = insertAndUpdateOneStudentFromExcel(student);
                result.add(resultTmp);
            } else {
                String msg = "学生花名册读取到的student不合法!";
                logger.error(msg);
                throw new InvalidParameterException(msg);
            }
        }
        result.setResult(Constants.SUCCESS);
        return result;
    }

    @Override
    public UpdateResult insertAndUpdateOneStudentFromExcel(Student student) throws Exception {
        if (student == null) {
            String msg = "insertAndUpdateOneStudentFromExcel方法输入学生student为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        UpdateResult result = new UpdateResult();

        Student originalStudent = getStudentByStudentId(student.getStudentId());
        if (originalStudent != null) {
            //学员编号已存在，更新
            if (!StringUtils.equals(originalStudent.getStudentName(), student.getStudentName())) {
                //姓名有变化
                long count = studentMapper.updateStudentNameByStudentId(student);
                result.setUpdateCount(count);
            }
        } else {
            result.add(insertStudentWithUnrepeatedStudentId(student));
        }
        result.setResult(Constants.SUCCESS);
        return result;
    }

    @Override
    public UpdateResult insertAndUpdateOneStudentSchoolFromExcel(Student student) throws Exception {
        if (student == null) {
            String msg = "updateOneStudentSchoolFromExcel方法输入学生student为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        UpdateResult result = new UpdateResult();

        Student originalStudent = getStudentByStudentId(student.getStudentId());
        if (originalStudent != null) {
            //学员编号已存在，更新
            if (!StringUtils.equals(originalStudent.getStudentSchool(), student.getStudentSchool())) {
                //有修改, 更新
                long count = studentMapper.updateStudentSchoolByStudentId(student);
                result.setUpdateCount(count);
            }
        } else {
            //由于学校统计不扫姓名，但数据库姓名字段约束为非空，所以给学号作为默认值
            student.setStudentName(student.getStudentId());
            result.add(insertStudentWithUnrepeatedStudentId(student));
        }

        result.setResult(Constants.SUCCESS);
        return result;
    }

    @Override
    public UpdateResult insertAndUpdateStudentsSchoolsFromExcel(List<Student> students) throws Exception {
        UpdateResult result = new UpdateResult();
        for (Student student : students) {
            if (StudentUtils.isValidStudentInfo(student)) {
                UpdateResult resultTmp = insertAndUpdateOneStudentSchoolFromExcel(student);
                result.add(resultTmp);
            } else {
                String msg = "学生花名册读取到的student学校不合法!";
                logger.error(msg);
                throw new InvalidParameterException(msg);
            }
        }
        result.setResult(Constants.SUCCESS);
        return result;
    }

    @Override
    public PageInfo<Student> listStudents(MyPage myPage, StudentSearchCondition condition) {
        PageHelper.startPage(myPage.getPageNum(), myPage.getPageSize());
        List<Student> students = studentMapper.listStudents(condition);
        return new PageInfo<>(students);
    }

    @Override
    public String updateStudentInfo(Student student) {
        if (student == null){
            return Constants.FAILURE;
        }
        Student originalStudent = getStudentById(student.getId());
        if (originalStudent == null){
            return Constants.FAILURE;
        }

        if (!student.getStudentId().equals(originalStudent.getStudentId())) {
            //学员号修改过了，判断是否与已存在的学员号冲突
            if (getStudentByStudentId(student.getStudentId()) != null) {
                //修改后的学员号已存在
                return "studentIdRepeat";
            }
        }

        if (StringUtils.isEmpty(student.getStudentSex())) {
            student.setStudentSex(null);
        }

        if (originalStudent.equalsExceptBaseParams(student)){
            //未修改
            return Constants.UNCHANGED;
        }

        studentMapper.updateStudentInfo(student);
        return Constants.SUCCESS;
    }

    @Override
    public long deleteOneStudentById(Long id) {
        if (id == null) {
            return 0;
        }
        return studentMapper.deleteOneStudentById(id);
    }

    @Override
    public long deleteManyStudentsByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return 0;
        }
        return studentMapper.deleteManyStudentsByIds(ids);
    }

    @Override
    public String deleteStudentsByCondition(StudentSearchCondition condition) {
        if (condition == null){
            return Constants.FAILURE;
        }
        studentMapper.deleteStudentsByCondition(condition);
        return Constants.SUCCESS;
    }

}
