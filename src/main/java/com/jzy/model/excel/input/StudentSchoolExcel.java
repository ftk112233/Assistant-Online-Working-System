package com.jzy.model.excel.input;

import com.jzy.manager.constant.ExcelConstants;
import com.jzy.manager.exception.ExcelColumnNotFoundException;
import com.jzy.manager.exception.InvalidFileTypeException;
import com.jzy.model.entity.Student;
import com.jzy.model.excel.Excel;
import com.jzy.model.excel.ExcelVersionEnum;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.*;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName StudentListImportToDatabaseExcel
 * @description 学生花名册用来导入数据库的
 * @date 2019/11/1 11:07
 **/
public class StudentSchoolExcel extends Excel implements Serializable {
    private static final long serialVersionUID = 3823535210593191680L;

    private static final String STUDENT_ID_COLUMN = ExcelConstants.STUDENT_ID_COLUMN_3;

    private static final String STUDENT_SCHOOL_COLUMN = ExcelConstants.STUDENT_SCHOOL_COLUMN;

    /**
     * 有效信息开始的行
     */
    private static int startRow = 0;

    private int columnIndexOfStudentId = -1;

    private int columnIndexOfStudentSchool = -1;

    /**
     * 读取到的信息封装成Student的list, 含学员号和学校信息的学生对象
     */
    @Getter
    private List<Student> students;

    /**
     * 读取到的学生id列表
     */
    @Getter
    private Set<String> studentIds;

    /**
     * 读取到的学生id和学生对象的临时缓存
     */
    @Getter
    @Setter
    private Map<String, Student> studentCache;

    public StudentSchoolExcel() {
    }

    public StudentSchoolExcel(String inputFile) throws IOException, InvalidFileTypeException {
        super(inputFile);
    }

    public StudentSchoolExcel(File file) throws IOException, InvalidFileTypeException {
        super(file);
    }

    public StudentSchoolExcel(InputStream inputStream, ExcelVersionEnum version) throws IOException, InvalidFileTypeException {
        super(inputStream, version);
    }

    public StudentSchoolExcel(Workbook workbook) {
        super(workbook);
    }

    @Override
    public boolean isValidColumn() {
        int sheetIx = 0;
        columnIndexOfStudentId = -1;
        columnIndexOfStudentSchool = -1;

        // 先扫描第startRow行找到"学员号"、"学校"等信息所在列的位置
        int row0ColumnCount = this.getColumnCount(sheetIx, startRow); // 第startRow行的列数
        for (int i = 0; i < row0ColumnCount; i++) {
            String value = this.getValueAt(sheetIx, startRow, i);
            switch (value) {
                case STUDENT_ID_COLUMN:
                    columnIndexOfStudentId = i;
                    break;
                case STUDENT_SCHOOL_COLUMN:
                    columnIndexOfStudentSchool = i;
                    break;
                default:
            }
        }

        if (columnIndexOfStudentId < 0 || columnIndexOfStudentSchool < 0) {
            //列属性中有未匹配的属性名
            return false;
        }
        return true;
    }

    /**
     * 从学生学校统计表中读取信息
     *
     * @return 返回表格有效数据的行数
     * @throws ExcelColumnNotFoundException 列属性中有未匹配的属性名
     */
    public int readStudentsSchoolsFromExcel() throws ExcelColumnNotFoundException {
        resetParam();
        int sheetIx = 0;


        if (!isValidColumn()) {
            //列属性中有未匹配的属性名
            throw new ExcelColumnNotFoundException("学生学校统计表列属性中有未匹配的属性名");
        }

        int effectiveDataRowCount = 0;
        Map<String, Student> studentMap = new HashMap<>();
        int rowCount = this.getRowCount(sheetIx); // 表的总行数
        for (int i = startRow + 1; i < rowCount; i++) {
            String studentId = this.getValueAt(sheetIx, i, columnIndexOfStudentId);
            if (StringUtils.isEmpty(studentId)) {
                //当前行学员号为空，跳过
                continue;
            } else {
                effectiveDataRowCount++;
            }
            studentId = studentId.toUpperCase();
            String studentSchool = this.getValueAt(sheetIx, i, columnIndexOfStudentSchool);

            if (!StringUtils.isEmpty(studentSchool)) {
                //封装student
                Student student = new Student();
                student.setStudentId(studentId);
                student.setStudentSchool(studentSchool);
                studentMap.put(studentId, student);
            }
        }

        students.addAll(studentMap.values());

        return effectiveDataRowCount;
    }


    /**
     * 从学生学校统计表中读取学生ids
     *
     * @return 返回表格有效数据的行数
     * @throws ExcelColumnNotFoundException
     */
    public int readStudentIdsFromExcel() throws ExcelColumnNotFoundException {
        resetParam();
        int sheetIx = 0;


        if (!isValidColumn()) {
            //列属性中有未匹配的属性名
            throw new ExcelColumnNotFoundException("学生学校统计表列属性中有未匹配的属性名");
        }

        int effectiveDataRowCount = 0;
        int rowCount = this.getRowCount(sheetIx); // 表的总行数
        for (int i = startRow + 1; i < rowCount; i++) {
            String studentId = this.getValueAt(sheetIx, i, columnIndexOfStudentId);
            if (StringUtils.isEmpty(studentId)) {
                //当前行学员号为空，跳过
                continue;
            } else {
                effectiveDataRowCount++;
            }
            studentId = studentId.toUpperCase();

            studentIds.add(studentId);
        }

        return effectiveDataRowCount;
    }

    /**
     * 向学生学校统计表中写读取到的学生信息
     *
     * @param studentMap <学员编号, 学生信息对象>
     * @return
     * @throws ExcelColumnNotFoundException
     * @throws IOException
     */
    public boolean writeStudentsSchools(Map<String, Student> studentMap) throws ExcelColumnNotFoundException, IOException {
        int sheetIx = 0;

        if (!isValidColumn()) {
            //列属性中有未匹配的属性名
            throw new ExcelColumnNotFoundException("学生学校统计表列属性中有未匹配的属性名");
        }

        if (studentMap == null || studentMap.size() == 0) {
            return true;
        }

        int rowCount = this.getRowCount(sheetIx); // 表的总行数
        for (int i = startRow + 1; i < rowCount; i++) {
            String studentId = this.getValueAt(sheetIx, i, columnIndexOfStudentId);
            if (StringUtils.isEmpty(studentId)) {
                //当前行学员号为空，跳过
                continue;
            }


            Student student = studentMap.get(studentId);
            String school = "";
            if (student != null) {
                school = student.getStudentSchool();
            }

            this.setValueAt(sheetIx, i, columnIndexOfStudentSchool, school);
        }


        return true;
    }

    /**
     * 向学生学校统计表中写读取到的学生信息
     *
     * @return
     * @throws ExcelColumnNotFoundException
     * @throws IOException
     */
    public boolean writeStudentsSchools() throws ExcelColumnNotFoundException, IOException {
        return writeStudentsSchools(studentCache);
    }

    @Override
    public void resetParam() {
        students = new ArrayList<>();
        studentIds = new HashSet<>();
        studentCache = new HashMap<>();
    }

    public static void main(String[] args) throws IOException, ExcelColumnNotFoundException, InvalidFileTypeException {
        StudentSchoolExcel excel = new StudentSchoolExcel("C:\\Users\\92970\\Desktop\\1.xlsx");
        excel.getValueAt(0, 1, 0);
        excel.setValueAt(0, 1, 1, "1");

    }
}
