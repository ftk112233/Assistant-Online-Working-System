package com.jzy.model.excel.input;

import com.jzy.manager.constant.ExcelConstants;
import com.jzy.manager.exception.ExcelColumnNotFoundException;
import com.jzy.manager.exception.ExcelTooManyRowsException;
import com.jzy.manager.exception.InvalidFileTypeException;
import com.jzy.manager.util.MyTimeUtils;
import com.jzy.model.dto.StudentAndClassDetailedDto;
import com.jzy.model.entity.Student;
import com.jzy.model.excel.AbstractInputExcel;
import com.jzy.model.excel.ExcelVersionEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName StudentListImportToDatabaseExcel
 * @description 学生花名册用来导入数据库的
 * @date 2019/11/1 11:07
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentListImportToDatabaseExcel extends AbstractInputExcel {
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
    private static final String REMARK_COLUMN = ExcelConstants.REMARK_COLUMN_2;


    /**
     * 有效信息开始的行
     */
    private static int startRow = 0;

    /**
     * 规定名称的列的索引位置，初始值为-1无效值，即表示还没找到
     */
    private int columnIndexOfStudentId = -1;
    private int columnIndexOfStudentName = -1;
    private int columnIndexOfStudentPhone = -1;
    private int columnIndexOfStudentPhoneBackup = -1;
    private int columnIndexOfClassId = -1;
    private int columnIndexOfRegisterTime = -1;
    private int columnIndexOfRemark = -1;


    /**
     * 读取到的信息封装成Student对象
     */
    private Set<Student> students;

    /**
     * 读取到的信息封装成StudentAndClassDto对象
     */
    private List<StudentAndClassDetailedDto> studentAndClassDetailedDtos;

    public StudentListImportToDatabaseExcel(String inputFile) throws IOException, InvalidFileTypeException {
        super(inputFile);
    }

    public StudentListImportToDatabaseExcel(InputStream inputStream, ExcelVersionEnum version) throws IOException, InvalidFileTypeException {
        super(inputStream, version);
    }

    /**
     * 从学生花名册表中读取信息
     * 学生Student对象直接读出，教师teacher对象也直接读出，班级信息整体和学生、助教、教师封装成StudentAndClassDto
     *
     * @return 返回表格有效数据的行数
     * @throws ExcelColumnNotFoundException 列属性中有未匹配的属性名
     * @throws ExcelTooManyRowsException 行数超过规定值，将规定的上限值和实际值都传给异常对象
     */
    public int readStudentAndClassInfoFromExcel() throws ExcelColumnNotFoundException, ExcelTooManyRowsException {
        resetOutput();

        int sheetIx = 0;

        testRowCountValidityOfSheet(sheetIx);

        // 先扫描第startRow行找到"学员号"、"姓名"、"手机"等信息所在列的位置
        findColumnIndexOfSpecifiedNameForReadingStudentAndClassInfo(sheetIx);

        int effectiveDataRowCount = 0;

        int rowCount = getRowCount(sheetIx); // 表的总行数
        for (int i = startRow + 1; i < rowCount; i++) {
            String studentId = getValueAt(sheetIx, i, columnIndexOfStudentId);
            if (StringUtils.isEmpty(studentId)) {
                //当前行学员号为空，跳过
                continue;
            } else {
                effectiveDataRowCount++;
            }
            studentId = studentId.toUpperCase();
            String studentName = getValueAt(sheetIx, i, columnIndexOfStudentName);
            String studentPhone = getValueAt(sheetIx, i, columnIndexOfStudentPhone);
            String studentPhoneBackup = getValueAt(sheetIx, i, columnIndexOfStudentPhoneBackup);
            String classId = getValueAt(sheetIx, i, columnIndexOfClassId);
            classId = StringUtils.upperCase(classId);
            String registerTime = getValueAt(sheetIx, i, columnIndexOfRegisterTime);
            String remark = getValueAt(sheetIx, i, columnIndexOfRemark);

            //先封装student
            Student student = new Student();
            student.setStudentId(studentId);
            student.setStudentName(studentName);
            student.setStudentPhone(studentPhone);
            student.setStudentPhoneBackup(studentPhoneBackup);
            students.add(student);

            StudentAndClassDetailedDto studentAndClassDetailedDto = new StudentAndClassDetailedDto();
            studentAndClassDetailedDto.setStudentId(studentId);
            studentAndClassDetailedDto.setClassId(classId);

            Date registerTimeToDate = null;
            try {
                registerTimeToDate = MyTimeUtils.cstToDate(registerTime);
            } catch (ParseException e) {
                //cst时间转换失败
                registerTimeToDate = MyTimeUtils.stringToDateYMDHMS(registerTime);
                if (registerTimeToDate == null) {
                    //yyyy-MM-dd HH:mm:ss格式的时间转换失败，这里再尝试FORMAT_YMDHMS_BACKUP格式的
                    registerTimeToDate = MyTimeUtils.stringToDate(registerTime, MyTimeUtils.FORMAT_YMDHMS_BACKUP);
                }
            }
            studentAndClassDetailedDto.setRegisterTime(registerTimeToDate);

            studentAndClassDetailedDto.setRemark(remark);
            studentAndClassDetailedDtos.add(studentAndClassDetailedDto);
        }

        return effectiveDataRowCount;
    }

    /**
     * 在指定的sheet中找到规定名称的列的索引位置，并设置索引值到成员变量中。如果找不到的要抛出异常。
     *
     * @param sheetIx sheet索引
     * @throws ExcelColumnNotFoundException 列属性中有未匹配的属性名，将具体的哪一列未匹配传入异常对象
     */
    private void findColumnIndexOfSpecifiedNameForReadingStudentAndClassInfo(int sheetIx) throws ExcelColumnNotFoundException {
        resetColumnIndex();

        int row0ColumnCount = getColumnCount(sheetIx, startRow); // 第startRow行的列数
        for (int i = 0; i < row0ColumnCount; i++) {
            String value = getValueAt(sheetIx, startRow, i);
            if (value != null) {
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
        }

        if (columnIndexOfStudentId < 0) {
            throw new ExcelColumnNotFoundException(null, STUDENT_ID_COLUMN);
        }
        if (columnIndexOfStudentName < 0) {
            throw new ExcelColumnNotFoundException(null, STUDENT_NAME_COLUMN);
        }
        if (columnIndexOfClassId < 0) {
            throw new ExcelColumnNotFoundException(null, CLASS_ID_COLUMN);
        }
    }

    /**
     * 从刚开班的时候带学生电话的学生花名册表中读取信息
     * 学生Student对象直接读出
     *
     * @return 返回表格有效数据的行数
     * @throws ExcelColumnNotFoundException 列属性中有未匹配的属性名
     * @throws ExcelTooManyRowsException 行数超过规定值，将规定的上限值和实际值都传给异常对象
     */
    public int readStudentDetailInfoFromExcel() throws ExcelColumnNotFoundException, ExcelTooManyRowsException {
        resetOutput();

        int sheetIx = 0;

        testRowCountValidityOfSheet(sheetIx);

        // 先扫描第startRow行找到"学员号"、"姓名"、"手机"等信息所在列的位置
        findColumnIndexOfSpecifiedNameForReadingStudentDetail(sheetIx);

        int effectiveDataRowCount = 0;

        int rowCount = getRowCount(sheetIx); // 表的总行数
        for (int i = startRow + 1; i < rowCount; i++) {
            String studentId = getValueAt(sheetIx, i, columnIndexOfStudentId);
            if (StringUtils.isEmpty(studentId)) {
                //当前行学员号为空，跳过
                continue;
            } else {
                effectiveDataRowCount++;
            }
            studentId = studentId.toUpperCase();
            String studentName = getValueAt(sheetIx, i, columnIndexOfStudentName);
            String studentPhone = getValueAt(sheetIx, i, columnIndexOfStudentPhone);
            String studentPhoneBackup = getValueAt(sheetIx, i, columnIndexOfStudentPhoneBackup);

            //封装student
            Student student = new Student();
            student.setStudentId(studentId);
            student.setStudentName(studentName);
            student.setStudentPhone(studentPhone);
            student.setStudentPhoneBackup(studentPhoneBackup);
            students.add(student);
        }

        return effectiveDataRowCount;
    }

    /**
     * 在指定的sheet中找到规定名称的列的索引位置，并设置索引值到成员变量中。如果找不到的要抛出异常。
     *
     * @param sheetIx sheet索引
     * @throws ExcelColumnNotFoundException 列属性中有未匹配的属性名，将具体的哪一列未匹配传入异常对象
     */
    private void findColumnIndexOfSpecifiedNameForReadingStudentDetail(int sheetIx) throws ExcelColumnNotFoundException {
        int row0ColumnCount = getColumnCount(sheetIx, startRow); // 第startRow行的列数
        for (int i = 0; i < row0ColumnCount; i++) {
            String value = getValueAt(sheetIx, startRow, i);
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

        if (columnIndexOfStudentId < 0) {
            throw new ExcelColumnNotFoundException(null, STUDENT_ID_COLUMN);
        }
        if (columnIndexOfStudentPhone < 0) {
            throw new ExcelColumnNotFoundException(null, STUDENT_PHONE_COLUMN);
        }
    }

    /**
     * 重置所有列索引值
     */
    @Override
    public void resetColumnIndex() {
        columnIndexOfStudentId = -1;
        columnIndexOfStudentName = -1;
        columnIndexOfStudentPhone = -1;
        columnIndexOfStudentPhoneBackup = -1;
        columnIndexOfClassId = -1;
        columnIndexOfRegisterTime = -1;
        columnIndexOfRemark = -1;
    }

    /**
     * 参见findColumnIndexOfSpecifiedNameForReadingStudentAndClassInfo()、findColumnIndexOfSpecifiedNameForReadingStudentDetail()
     */
    @Override
    protected void findColumnIndexOfSpecifiedName(int sheetIx) throws ExcelColumnNotFoundException{
        //do nothing
    }

    @Override
    public void resetOutput() {
        students = new HashSet<>();
        studentAndClassDetailedDtos = new ArrayList<>();
    }

    public static void main(String[] args) throws IOException, ExcelColumnNotFoundException, InvalidFileTypeException, ExcelTooManyRowsException {
        StudentListImportToDatabaseExcel excel = new StudentListImportToDatabaseExcel("C:\\Users\\92970\\Desktop\\t\\xz花名册1 - 副本.xlsx");
        excel.readStudentAndClassInfoFromExcel();
        for (StudentAndClassDetailedDto dto : excel.getStudentAndClassDetailedDtos()) {
            System.out.println(dto);
        }
    }
}
