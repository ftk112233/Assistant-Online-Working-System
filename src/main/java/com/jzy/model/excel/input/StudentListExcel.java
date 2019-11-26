package com.jzy.model.excel.input;

import com.jzy.manager.constant.ExcelConstants;
import com.jzy.manager.exception.ExcelColumnNotFoundException;
import com.jzy.manager.exception.InputFileTypeException;
import com.jzy.manager.util.FileUtils;
import com.jzy.manager.util.MyTimeUtils;
import com.jzy.model.dto.StudentAndClassDetailedDto;
import com.jzy.model.entity.Student;
import com.jzy.model.excel.Excel;
import com.jzy.model.excel.ExcelVersionEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName StudentListExcel
 * @description 学生花名册
 * @date 2019/11/1 11:07
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentListExcel extends Excel implements Serializable {
    private static final long serialVersionUID = 3823535210593191680L;

    private static final String STUDENT_ID_COLUMN = ExcelConstants.STUDENT_ID_COLUMN;

    private static final String STUDENT_NAME_COLUMN = ExcelConstants.STUDENT_NAME_COLUMN;

    private static final String STUDENT_PHONE_COLUMN = ExcelConstants.STUDENT_PHONE_COLUMN;

    private static final String STUDENT_PHONE_BACKUP_COLUMN = ExcelConstants.STUDENT_PHONE_BACKUP_COLUMN;

    private static final String TEACHER_NAME_COLUMN = ExcelConstants.TEACHER_NAME_COLUMN_2;

    private static final String ASSISTANT_NAME_COLUMN = ExcelConstants.ASSISTANT_NAME_COLUMN_2;

    private static final String CLASS_ID_COLUMN = ExcelConstants.CLASS_ID_COLUMN_2;

    private static final String CLASS_NAME_COLUMN = ExcelConstants.CLASS_NAME_COLUMN_2;

    private static final String CLASS_TIME_COLUMN = ExcelConstants.CLASS_TIME_COLUMN_2;

    private static final String CLASSROOM_COLUMN = ExcelConstants.CLASSROOM_COLUMN_2;

    private static final String REGISTER_TIME_COLUMN = ExcelConstants.REGISTER_TIME_COLUMN;

    private static final String REMARK_COLUMN =ExcelConstants.REMARK_COLUMN_2;


    /**
     * 有效信息开始的行
     */
    private static int startRow = 0;

    /**
     * 读取到的信息封装成Student对象
     */
    private Set<Student> students;

    /**
     * 读取到的信息封装成StudentAndClassDto对象
     */
    private List<StudentAndClassDetailedDto> studentAndClassDetailedDtos;

    public StudentListExcel() {
    }

    public StudentListExcel(String inputFile) throws IOException, InputFileTypeException {
        super(inputFile);
    }

    public StudentListExcel(File file) throws IOException, InputFileTypeException {
        super(file);
    }

    public StudentListExcel(InputStream inputStream, ExcelVersionEnum version) throws IOException, InputFileTypeException {
        super(inputStream, version);
    }

    public StudentListExcel(Workbook workbook) {
        super(workbook);
    }

    /**
     * 从学生花名册表中读取信息
     *       学生Student对象直接读出，教师teacher对象也直接读出，班级信息整体和学生、助教、教师封装成StudentAndClassDto
     */
    public void readStudentAndClassInfoFromExcel() throws ExcelColumnNotFoundException{
        resetParam();
        int sheetIx = 0;

        // 先扫描第startRow行找到"学员号"、"姓名"、"手机"等信息所在列的位置
        int columnIndexOfStudentId = -1, columnIndexOfStudentName = -2, columnIndexOfStudentPhone = -3
                , columnIndexOfStudentPhoneBackup = -4, columnIndexOfClassId = -10
                , columnIndexOfRegisterTime = -14,columnIndexOfRemark = -15;
        int row0ColumnCount = this.getColumnCount(sheetIx, startRow); // 第startRow行的列数
        for (int i = 0; i < row0ColumnCount; i++) {
            String value = this.getValueAt(sheetIx, startRow, i);
            switch (value) {
                case STUDENT_ID_COLUMN:
                    columnIndexOfStudentId = i;
                    break;
                case STUDENT_NAME_COLUMN:
                    columnIndexOfStudentName = i;
                    break;
                case STUDENT_PHONE_COLUMN:
                    columnIndexOfStudentPhone = i;
                    break;
                case STUDENT_PHONE_BACKUP_COLUMN:
                    columnIndexOfStudentPhoneBackup = i;
                    break;
                case CLASS_ID_COLUMN:
                    columnIndexOfClassId = i;
                    break;
                case REGISTER_TIME_COLUMN:
                    columnIndexOfRegisterTime = i;
                    break;
                case REMARK_COLUMN:
                    columnIndexOfRemark = i;
                    break;
                default:
            }
        }

        if (columnIndexOfStudentId < 0 || columnIndexOfStudentName < 0 || columnIndexOfClassId < 0) {
            //列属性中有未匹配的属性名
            throw new ExcelColumnNotFoundException("学生花名册列属性中有未匹配的属性名");
        }


        int rowCount = this.getRowCount(sheetIx); // 表的总行数
        for (int i = startRow + 1; i < rowCount; i++) {
            if (StringUtils.isEmpty(this.getValueAt(sheetIx, i, columnIndexOfStudentId))){
                //当前行学员号为空，跳过
                continue;
            }
            String studentId = this.getValueAt(sheetIx, i, columnIndexOfStudentId);
            String studentName = this.getValueAt(sheetIx, i, columnIndexOfStudentName);
            String studentPhone = this.getValueAt(sheetIx, i, columnIndexOfStudentPhone);
            String studentPhoneBackup = this.getValueAt(sheetIx, i, columnIndexOfStudentPhoneBackup);
            String classId = this.getValueAt(sheetIx, i, columnIndexOfClassId);
            String registerTime = this.getValueAt(sheetIx, i, columnIndexOfRegisterTime);
            String remark = this.getValueAt(sheetIx, i, columnIndexOfRemark);

            //先封装student
            Student student=new Student();
            student.setStudentId(studentId);
            student.setStudentName(studentName);
            student.setStudentPhone(studentPhone);
            student.setStudentPhoneBackup(studentPhoneBackup);
            students.add(student);

            StudentAndClassDetailedDto studentAndClassDetailedDto=new StudentAndClassDetailedDto();
            studentAndClassDetailedDto.setStudentId(studentId);
            studentAndClassDetailedDto.setClassId(classId);
            try {
                studentAndClassDetailedDto.setRegisterTime(MyTimeUtils.cstToDate(registerTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            studentAndClassDetailedDto.setRemark(remark);
            studentAndClassDetailedDtos.add(studentAndClassDetailedDto);
        }
    }

    /**
     * 从刚开班的时候带学生电话的学生花名册表中读取信息
     *       学生Student对象直接读出
     */
    public void readStudentDetailInfoFromExcel() throws ExcelColumnNotFoundException{
        resetParam();
        int sheetIx = 0;

        // 先扫描第startRow行找到"学员号"、"姓名"、"手机"等信息所在列的位置
        int columnIndexOfStudentId = -1, columnIndexOfStudentName = -2, columnIndexOfStudentPhone = -3
                , columnIndexOfStudentPhoneBackup = -4;
        int row0ColumnCount = this.getColumnCount(sheetIx, startRow); // 第startRow行的列数
        for (int i = 0; i < row0ColumnCount; i++) {
            String value = this.getValueAt(sheetIx, startRow, i);
            switch (value) {
                case STUDENT_ID_COLUMN:
                    columnIndexOfStudentId = i;
                    break;
                case STUDENT_NAME_COLUMN:
                    columnIndexOfStudentName = i;
                    break;
                case STUDENT_PHONE_COLUMN:
                    columnIndexOfStudentPhone = i;
                    break;
                case STUDENT_PHONE_BACKUP_COLUMN:
                    columnIndexOfStudentPhoneBackup = i;
                    break;
                default:
            }
        }

        if (columnIndexOfStudentId < 0 || columnIndexOfStudentPhone < 0) {
            //列属性中有未匹配的属性名
            throw new ExcelColumnNotFoundException("刚开班的时候带学生电话的学生花名册列属性中有未匹配的属性名");
        }


        int rowCount = this.getRowCount(sheetIx); // 表的总行数
        for (int i = startRow + 1; i < rowCount; i++) {
            if (StringUtils.isEmpty(this.getValueAt(sheetIx, i, columnIndexOfStudentId))){
                //当前行学员号为空，跳过
                continue;
            }
            String studentId = this.getValueAt(sheetIx, i, columnIndexOfStudentId);
            String studentName = this.getValueAt(sheetIx, i, columnIndexOfStudentName);
            String studentPhone = this.getValueAt(sheetIx, i, columnIndexOfStudentPhone);
            String studentPhoneBackup = this.getValueAt(sheetIx, i, columnIndexOfStudentPhoneBackup);

            //封装student
            Student student=new Student();
            student.setStudentId(studentId);
            student.setStudentName(studentName);
            student.setStudentPhone(studentPhone);
            student.setStudentPhoneBackup(studentPhoneBackup);
            students.add(student);
        }
    }

    @Override
    public void resetParam() {
        students = new HashSet<>();
        studentAndClassDetailedDtos = new ArrayList<>();
    }

    public static void main(String[] args) throws IOException {
        StudentListExcel excel = new StudentListExcel("D:\\aows_resources\\toolbox\\example\\"+FileUtils.FILE_NAMES.get(4));
        excel.readStudentDetailInfoFromExcel();
        for (Student student:excel.getStudents()){
            System.out.println(student);
        }
        System.out.println(excel.getStudents().size());
        System.out.println(excel.getValueAt(0,0,-1));
    }
}
