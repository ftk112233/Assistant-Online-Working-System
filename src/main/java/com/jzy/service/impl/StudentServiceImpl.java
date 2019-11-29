package com.jzy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.dao.StudentMapper;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.util.StudentUtils;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.StudentSearchCondition;
import com.jzy.model.entity.Student;
import com.jzy.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
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
    private final static Logger logger = Logger.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student getStudentById(Long id) {
        return id == null ? null : studentMapper.getStudentById(id);
    }

    @Override
    public Student getStudentByStudentId(String studentId) {
        return StringUtils.isEmpty(studentId) ? null :studentMapper.getStudentByStudentId(studentId);
    }

    @Override
    public String updateStudentByStudentId(Student student) {
        if (StringUtils.isEmpty(student.getStudentSex())){
            student.setStudentSex(null);
        }
        studentMapper.updateStudentByStudentId(student);
        return Constants.SUCCESS;
    }

    @Override
    public String insertStudent(Student student) {
        if (getStudentByStudentId(student.getStudentId()) != null) {
            //添加的工号已存在
            return "studentIdRepeat";
        }

        return insertStudentWithUnrepeatedStudentId(student);
    }

    /**
     * 插入不重复的学员信息
     *
     * @param student
     * @return
     */
    private  String insertStudentWithUnrepeatedStudentId(Student student) {
        if (StringUtils.isEmpty(student.getStudentSex())) {
            student.setStudentSex(null);
        }

        studentMapper.insertStudent(student);
        return Constants.SUCCESS;
    }

    @Override
    public String insertAndUpdateStudentsDetailedFromExcel(List<Student> students) throws Exception {
        for (Student student:students){
            if (StudentUtils.isValidStudentInfo(student)){
                insertAndUpdateOneStudentDetailedFromExcel(student);
            } else {
                String msg = "学生花名册读取到的student不合法!";
                logger.error(msg);
                throw new InvalidParameterException(msg);
            }
        }
        return Constants.SUCCESS;
    }

    @Override
    public String insertAndUpdateOneStudentDetailedFromExcel(Student student) throws Exception {
        if (student == null) {
            String msg = "insertAndUpdateOneStudentFromExcel方法输入学生student为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }


        Student originalStudent=getStudentByStudentId(student.getStudentId());
        if (originalStudent != null) {
            //学员编号已存在，更新
            updateStudentByStudentId(student);
        } else {
            insertStudentWithUnrepeatedStudentId(student);
        }
        return Constants.SUCCESS;
    }

    @Override
    public String insertAndUpdateStudentsFromExcel(List<Student> students) throws Exception {
        for (Student student:students){
            if (StudentUtils.isValidStudentInfo(student)){
                insertAndUpdateOneStudentFromExcel(student);
            } else {
                String msg = "学生花名册读取到的student不合法!";
                logger.error(msg);
                throw new InvalidParameterException(msg);
            }
        }
        return Constants.SUCCESS;
    }

    @Override
    public String insertAndUpdateOneStudentFromExcel(Student student) throws Exception {
        if (student == null) {
            String msg = "insertAndUpdateOneStudentFromExcel方法输入学生student为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        Student originalStudent=getStudentByStudentId(student.getStudentId());
        if (originalStudent != null) {
            //学员编号已存在，更新
            studentMapper.updateStudentNameByStudentId(student);
        } else {
            insertStudent(student);
        }
        return Constants.SUCCESS;
    }

    @Override
    public PageInfo<Student> listStudents(MyPage myPage, StudentSearchCondition condition) {
        PageHelper.startPage(myPage.getPageNum(), myPage.getPageSize());
        List<Student> students = studentMapper.listStudents(condition);
        return new PageInfo<>(students);
    }

    @Override
    public String updateStudentInfo(Student student) {
        Student originalStudent = getStudentById(student.getId());

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

        studentMapper.updateStudentInfo(student);
        return Constants.SUCCESS;
    }

    @Override
    public void deleteOneStudentById(Long id) {
        studentMapper.deleteOneStudentById(id);
    }

    @Override
    public void deleteManyStudentsByIds(List<Long> ids) {
        studentMapper.deleteManyStudentsByIds(ids);
    }

}
