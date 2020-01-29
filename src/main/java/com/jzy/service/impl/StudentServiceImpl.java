package com.jzy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.dao.StudentMapper;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.ExcelConstants;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.util.StudentUtils;
import com.jzy.model.dto.*;
import com.jzy.model.entity.Student;
import com.jzy.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    private final static String STUDENT_ID_REPEAT = "studentIdRepeat";

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public boolean isRepeatedStudentId(Student student) {
        if (student == null) {
            throw new InvalidParameterException("输入对象不能为空");
        }
        return getStudentByStudentId(student.getStudentId()) != null;
    }

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
        if (student == null) {
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
        if (student == null) {
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
        if (student == null) {
            return new UpdateResult(Constants.FAILURE);
        }
        long count = studentMapper.updateStudentSchoolByStudentId(student);
        UpdateResult result = new UpdateResult();
        result.setUpdateCount(count);
        result.setResult(Constants.SUCCESS);
        return result;
    }

    @Override
    public UpdateResult insertOneStudent(Student student) {
        if (student == null) {
            return new UpdateResult(Constants.FAILURE);
        }
        if (isRepeatedStudentId(student)) {
            //添加的学号号已存在
            return new UpdateResult(STUDENT_ID_REPEAT);
        }

        return insertStudentWithUnrepeatedStudentId(student);
    }

    /**
     * 插入不重复的学员信息
     *
     * @param student 插入的学生
     * @return 1."failure"：错误入参等异常
     * 2."unchanged": 对比数据库原记录未做任何修改
     * 3."success": 更新成功
     */
    private UpdateResult insertStudentWithUnrepeatedStudentId(Student student) {
        if (student == null) {
            return new UpdateResult(Constants.FAILURE);
        }
        UpdateResult result = new UpdateResult();
        if (StringUtils.isEmpty(student.getStudentSex())) {
            student.setStudentSex(null);
        }

        long count = studentMapper.insertOneStudent(student);
        result.setInsertCount(count);
        result.setResult(Constants.SUCCESS);
        return result;
    }

    /**
     * 批量插入学生，但这些学生的学员编号不做冲突校验。因此要求入参必须不存在学员号重复的情况
     *
     * @param students 批量插入的学生
     * @return (更新结果, 更细记录数)
     */
    private UpdateResult insertManyStudentsWithUnrepeatedStudentId(List<Student> students) {
        if (students == null || students.size() == 0) {
            return new UpdateResult(Constants.FAILURE);
        }

        for (Student student : students) {
            if (student == null) {
                return new UpdateResult(Constants.FAILURE);
            }

            if (StringUtils.isEmpty(student.getStudentSex())) {
                student.setStudentSex(null);
            }
        }

        UpdateResult result = new UpdateResult(Constants.SUCCESS);
        long count = studentMapper.insertManyStudents(students);
        result.setInsertCount(count);
        return result;
    }

    @Override
    public DefaultFromExcelUpdateResult insertAndUpdateStudentsDetailedFromExcel(List<Student> students) {
        if (students == null) {
            String msg = "insertAndUpdateStudentsFromExcel方法输入学生students为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        DefaultFromExcelUpdateResult result = new DefaultFromExcelUpdateResult(Constants.SUCCESS);
        String studentIdKeyword = ExcelConstants.STUDENT_ID_COLUMN;
        String studentPhoneKeyword = ExcelConstants.STUDENT_PHONE_COLUMN;
        InvalidData invalidData = new InvalidData(studentIdKeyword, studentPhoneKeyword);

        List<Student> studentsToInsert = new ArrayList<>();
        for (Student student : students) {
            if (StudentUtils.isValidStudentInfo(student)) {
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
                    studentsToInsert.add(student);
                }
            } else {
                String msg = "学生花名册读取到的student不合法!";
                logger.error(msg);
                result.setResult(Constants.EXCEL_INVALID_DATA);
                invalidData.putValue(studentIdKeyword, student.getStudentId());
                invalidData.putValue(studentPhoneKeyword, student.getStudentPhone());
            }
        }

        //插入
        result.add(insertManyStudentsWithUnrepeatedStudentId(studentsToInsert));

        result.setInvalidData(invalidData);
        return result;
    }

    /**
     * 根据从excel中读取到的student信息（包括手机等字段），更新插入一个。根据学员编号判断：
     * if 当前学员编号不存在
     * 执行插入
     * else
     * 根据学员编号更新
     *
     * @param student 学生信息（包括手机等字段）
     * @return 更新结果
     * @throws InvalidParameterException 不合法的入参异常
     */
    @Deprecated
    private UpdateResult insertAndUpdateOneStudentDetailedFromExcel(Student student) throws InvalidParameterException {
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
    public DefaultFromExcelUpdateResult insertAndUpdateStudentsFromExcel(List<Student> students) {
        if (students == null) {
            String msg = "insertAndUpdateStudentsFromExcel方法输入学生students为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        DefaultFromExcelUpdateResult result = new DefaultFromExcelUpdateResult(Constants.SUCCESS);
        String studentIdKeyword = ExcelConstants.STUDENT_ID_COLUMN;
        String classIdKeyword = ExcelConstants.CLASS_ID_COLUMN_2;
        InvalidData invalidData = new InvalidData(studentIdKeyword, classIdKeyword);

        List<Student> studentsToInsert = new ArrayList<>();
        for (Student student : students) {
            if (StudentUtils.isValidStudentInfo(student)) {
                Student originalStudent = getStudentByStudentId(student.getStudentId());
                if (originalStudent != null) {
                    //学员编号已存在，更新
                    if (!StringUtils.equals(originalStudent.getStudentName(), student.getStudentName())) {
                        //姓名有变化
                        long count = studentMapper.updateStudentNameByStudentId(student);
                        result.setUpdateCount(count);
                    }
                } else {
                    studentsToInsert.add(student);
                }
            } else {
                String msg = "学生花名册读取到的student不合法!";
                logger.error(msg);
                result.setResult(Constants.EXCEL_INVALID_DATA);
                invalidData.putValue(studentIdKeyword, student.getStudentId());
                invalidData.putValue(classIdKeyword, null);
            }
        }

        //插入
        result.add(insertManyStudentsWithUnrepeatedStudentId(studentsToInsert));

        result.setInvalidData(invalidData);
        return result;
    }

    /**
     * 根据从excel中读取到的students信息（一般就学号姓名，不包括手机等字段），更新插入一个。根据学员编号判断：
     * if 当前学员编号不存在
     * 执行插入
     * else
     * 根据学员编号更新
     *
     * @param student 只含学号、姓名的学生信息
     * @return 更新结果
     * @throws InvalidParameterException 不合法的入参异常
     */
    @Deprecated
    private UpdateResult insertAndUpdateOneStudentFromExcel(Student student) throws InvalidParameterException {
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
    public DefaultFromExcelUpdateResult insertAndUpdateStudentsSchoolsFromExcel(List<Student> students) {
        if (students == null) {
            String msg = "insertAndUpdateStudentsSchoolsFromExcel方法输入学生students为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        DefaultFromExcelUpdateResult result = new DefaultFromExcelUpdateResult(Constants.SUCCESS);
        String studentIdKeyword = ExcelConstants.STUDENT_ID_COLUMN;
        String studentSchoolKeyword = ExcelConstants.STUDENT_SCHOOL_COLUMN;
        InvalidData invalidData = new InvalidData(studentIdKeyword, studentSchoolKeyword);
        List<Student> studentsToInsert = new ArrayList<>();
        for (Student student : students) {
            if (StudentUtils.isValidStudentInfo(student)) {
                Student originalStudent = getStudentByStudentId(student.getStudentId());
                if (originalStudent != null) {
                    //学员编号已存在，更新
                    if (!StringUtils.equals(originalStudent.getStudentSchool(), student.getStudentSchool())) {
                        //有修改, 更新
                        result.add(updateStudentSchoolByStudentId(student));
                    }
                } else {
                    //由于学校统计不扫姓名，但数据库姓名字段约束为非空，所以给学号作为默认值
                    student.setStudentName(student.getStudentId());
                    studentsToInsert.add(student);
                }
            } else {
                String msg = "学生花名册读取到的student学校不合法!";
                logger.error(msg);
                result.setResult(Constants.EXCEL_INVALID_DATA);
                invalidData.putValue(studentIdKeyword, student.getStudentId());
                invalidData.putValue(studentSchoolKeyword, student.getStudentSchool());
            }
        }

        //插入
        result.add(insertManyStudentsWithUnrepeatedStudentId(studentsToInsert));

        result.setInvalidData(invalidData);
        return result;
    }

    /**
     * 根据从excel中读取到的student学校信息，更新一个。根据学员编号判断：
     * if 当前学员编号不存在
     * 执行插入
     * else
     * 根据学员编号更新学校
     *
     * @param student 只含学号、学校的学生信息
     * @return 更新结果
     * @throws InvalidParameterException 不合法的入参异常
     */
    @Deprecated
    private UpdateResult insertAndUpdateOneStudentSchoolFromExcel(Student student) throws InvalidParameterException {
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
                result.add(updateStudentSchoolByStudentId(student));
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
    public PageInfo<Student> listStudents(MyPage myPage, StudentSearchCondition condition) {
        PageHelper.startPage(myPage.getPageNum(), myPage.getPageSize());
        List<Student> students = studentMapper.listStudents(condition);
        return new PageInfo<>(students);
    }

    @Override
    public String updateStudentInfo(Student student) {
        if (student == null) {
            return Constants.FAILURE;
        }
        Student originalStudent = getStudentById(student.getId());
        if (originalStudent == null) {
            return Constants.FAILURE;
        }

        if (isModifiedAndRepeatedStudentId(originalStudent, student)) {
            //学员号修改过了，判断是否与已存在的学员号冲突
            return STUDENT_ID_REPEAT;
        }

        if (StringUtils.isEmpty(student.getStudentSex())) {
            student.setStudentSex(null);
        }

        if (originalStudent.equalsExceptBaseParams(student)) {
            //未修改
            return Constants.UNCHANGED;
        }

        studentMapper.updateStudentInfo(student);
        return Constants.SUCCESS;
    }

    /**
     * 判断当前要更新的学生学员号是否修改过且重复。
     * 只有相较于原来的学生修改过且与数据库中重复才返回false
     *
     * @param originalStudent 用来比较的原来的学生
     * @param newStudent      要更新的学生
     * @return 学员号的内容是否重复且修改过
     */
    private boolean isModifiedAndRepeatedStudentId(Student originalStudent, Student newStudent) {
        if (!newStudent.getStudentId().equals(originalStudent.getStudentId())) {
            //学员号修改过了，判断是否与已存在的学员号冲突
            if (isRepeatedStudentId(newStudent)) {
                //修改后的学员号已存在
                return true;
            }
        }
        return false;
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
    public long deleteStudentsByCondition(StudentSearchCondition condition) {
        if (condition == null) {
            return 0;
        }
        return studentMapper.deleteStudentsByCondition(condition);
    }

}
