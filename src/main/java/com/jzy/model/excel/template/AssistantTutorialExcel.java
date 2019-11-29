package com.jzy.model.excel.template;

import com.jzy.manager.constant.ExcelConstants;
import com.jzy.manager.exception.ClassTooManyStudentsException;
import com.jzy.manager.exception.ExcelColumnNotFoundException;
import com.jzy.manager.exception.InputFileTypeException;
import com.jzy.model.dto.StudentAndClassDetailedWithSubjectsDto;
import com.jzy.model.excel.Excel;
import com.jzy.model.excel.ExcelVersionEnum;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName AssistantTutorialTemplate
 * @description 助教工作手册模板的模型类
 * @date 2019/11/1 15:28
 **/
public class AssistantTutorialExcel extends Excel implements Serializable {
    private static final long serialVersionUID = 2416400649170324596L;

    private static final String CAMPUS_COLUMN = ExcelConstants.CAMPUS_COLUMN_2;

    private static final String CLASS_ID_COLUMN =ExcelConstants.CLASS_ID_COLUMN_3;

    private static final String TEACHER_NAME_COLUMN = ExcelConstants.TEACHER_NAME_COLUMN_3;

    private static final String ASSISTANT_NAME_COLUMN = ExcelConstants.ASSISTANT_NAME_COLUMN_3;

    private static final String STUDENT_ID_COLUMN = ExcelConstants.STUDENT_ID_COLUMN_2;

    private static final String STUDENT_NAME_COLUMN = ExcelConstants.STUDENT_NAME_COLUMN_2;

    private static final String STUDENT_PHONE_COLUMN = ExcelConstants.STUDENT_PHONE_COLUMN_2;

    private static final String TEACHER_REQUIREMENT_COLUMN = ExcelConstants.TEACHER_REQUIREMENT_COLUMN;

    private static final String SUBJECTS_COLUMN=ExcelConstants.SUBJECTS_COLUMN;

    /**
     * 班上默认最大人数上限
     */
    private static final int MAX_CLASS_STUDENTS_COUNT = 100;

    /**
     * 开班电话表sheet索引
     */
    private static final int CLASS_START_SHEET_INDEX = 0;

    /**
     * 签到表sheet索引
     */
    private static final int SIGN_SHEET_INDEX = 1;

    /**
     * 信息回访表sheet索引
     */
    private static final int CALLBACK_SHEET_INDEX = 3;

    /**
     * 座位表sheet索引
     */
    private static final int SEAT_SHEET_INDEX = 6;

    public AssistantTutorialExcel() {
    }

    public AssistantTutorialExcel(String inputFile) throws IOException, InputFileTypeException {
        super(inputFile);
    }

    public AssistantTutorialExcel(File file) throws IOException, InputFileTypeException {
        super(file);
    }

    public AssistantTutorialExcel(InputStream inputStream, ExcelVersionEnum version) throws IOException, InputFileTypeException {
        super(inputStream, version);
    }

    public AssistantTutorialExcel(Workbook workbook) {
        super(workbook);
    }

    /**
     * 修改制作开班电话表
     *
     * @param data
     *            从数据库中读取到的信息或手动输入的表格中读到的信息，以及用户输入的信息
     * @return
     * @throws IOException
     * @throws ClassTooManyStudentsException
     */
    public boolean writeClassStartSheet(List<StudentAndClassDetailedWithSubjectsDto> data) throws IOException, ClassTooManyStudentsException {
        // 获得班上学生总人数
        int rowCountToSave = data.size();
        if (rowCountToSave > MAX_CLASS_STUDENTS_COUNT) {
            throw new ClassTooManyStudentsException("班上学生人数超过了" + MAX_CLASS_STUDENTS_COUNT + "！");
        }

        int startRow=0;
        // 先扫描第startRow行找到"校区"、"班号"、"教师姓名"等信息所在列的位置
        int columnIndexOfCampus = -1, columnIndexOfClassId = -2, columnIndexOfTeacherName = -3, columnIndexOfAssistantName = -4, columnIndexOfStudentId = -7, columnIndexOfStudentName = -9
                , columnIndexOfStudentPhone = -10,  columnIndexOfTeacherRequirement = -11,  columnIndexOfSubjects = -12;
        int row0ColumnCount = this.getColumnCount(CLASS_START_SHEET_INDEX, startRow); // 第startRow行的列数
        for (int i = 0; i < row0ColumnCount; i++) {
            String value = this.getValueAt(CLASS_START_SHEET_INDEX, startRow, i);
            switch (value) {
                case CAMPUS_COLUMN:
                    columnIndexOfCampus = i;
                    break;
                case CLASS_ID_COLUMN:
                    columnIndexOfClassId = i;
                    break;
                case TEACHER_NAME_COLUMN:
                    columnIndexOfTeacherName = i;
                    break;
                case ASSISTANT_NAME_COLUMN:
                    columnIndexOfAssistantName = i;
                    break;
                case STUDENT_ID_COLUMN:
                    columnIndexOfStudentId = i;
                    break;
                case STUDENT_NAME_COLUMN:
                    columnIndexOfStudentName = i;
                    break;
                case STUDENT_PHONE_COLUMN:
                    columnIndexOfStudentPhone = i;
                    break;
                case TEACHER_REQUIREMENT_COLUMN:
                    columnIndexOfTeacherRequirement = i;
                    break;
                case SUBJECTS_COLUMN:
                    columnIndexOfSubjects = i;
                    break;
                default:
            }
        }

        if (columnIndexOfCampus < 0 || columnIndexOfClassId < 0 || columnIndexOfTeacherName < 0 || columnIndexOfAssistantName < 0
                || columnIndexOfStudentId < 0 || columnIndexOfStudentName < 0 || columnIndexOfStudentPhone < 0
                || columnIndexOfTeacherRequirement < 0 || columnIndexOfSubjects < 0) {
            //列属性中有未匹配的属性名
            throw new ExcelColumnNotFoundException("助教工作手册-开班电话表sheet列属性中有未匹配的属性名");
        }


        for (int i = 0; i < rowCountToSave; i++) {
            StudentAndClassDetailedWithSubjectsDto object=data.get(i);
            //遍历每行要填的学生上课信息对象
            // 填校区
            this.setValueAt(CLASS_START_SHEET_INDEX,i+1,columnIndexOfCampus,object.getClassCampus());
            // 填班号
            this.setValueAt(CLASS_START_SHEET_INDEX,i+1,columnIndexOfClassId,object.getClassId());
            // 填教师姓名
            this.setValueAt(CLASS_START_SHEET_INDEX,i+1,columnIndexOfTeacherName,object.getTeacherName());
            // 填助教
            this.setValueAt(CLASS_START_SHEET_INDEX,i+1,columnIndexOfAssistantName,object.getAssistantName());
            // 填学员编号
            this.setValueAt(CLASS_START_SHEET_INDEX,i+1,columnIndexOfStudentId,object.getStudentId());
            // 填学员姓名
            this.setValueAt(CLASS_START_SHEET_INDEX,i+1,columnIndexOfStudentName,object.getStudentName());
            // 填学员联系方式
            this.setValueAt(CLASS_START_SHEET_INDEX,i+1,columnIndexOfStudentPhone,object.getStudentPhone());
            // 填任课教师要求
            this.setValueAt(CLASS_START_SHEET_INDEX,i+1,columnIndexOfTeacherRequirement,object.getClassTeacherRequirement());
            // 填所有在读学科
            String subjectsToString=object.getSubjects() == null ? "" : object.getSubjects().toString();
            this.setValueAt(CLASS_START_SHEET_INDEX,i+1,columnIndexOfSubjects,subjectsToString);
        }

        // 删除多余行
        this.removeRows(CLASS_START_SHEET_INDEX, rowCountToSave + 4, MAX_CLASS_STUDENTS_COUNT);

        return true;
    }

    /**
     * 修改制作签到表
     *
     * @param data
     *            从数据库中读取到的信息或手动输入的表格中读到的信息，以及用户输入的信息
     * @return
     * @throws IOException
     * @throws ClassTooManyStudentsException
     */
    public boolean writeSignSheet(List<StudentAndClassDetailedWithSubjectsDto> data) throws IOException, ClassTooManyStudentsException {
        // 获得班上学生总人数
        int rowCountToSave = data.size();
        if (rowCountToSave > MAX_CLASS_STUDENTS_COUNT) {
            throw new ClassTooManyStudentsException("班上学生人数超过了" + MAX_CLASS_STUDENTS_COUNT + "！");
        }

        StudentAndClassDetailedWithSubjectsDto dto=new StudentAndClassDetailedWithSubjectsDto();
        if (rowCountToSave>0){
            //取第一个对象为例，填充表格第一行、第二行
            dto=data.get(0);
        }
        // 填表格第一行
        String str1="班号："+dto.getClassId()+"                  班级名称："+dto.getClassName();
        this.setValueAt(SIGN_SHEET_INDEX,0,0,str1);
        // 填表格第二行
        String str2="上课时间："+ dto.getClassSimplifiedTime()+ "         上课教室："+dto.getClassroom()+
                "          教师："+dto.getTeacherName()+"             助教："+dto.getAssistantName();
        this.setValueAt(SIGN_SHEET_INDEX,1,0,str2);

        int startRow=2;
        // 先扫描第startRow行找到"学员编号"、"学员姓名"、"家长联系方式"等信息所在列的位置
        int columnIndexOfStudentId = -7, columnIndexOfStudentName = -9, columnIndexOfStudentPhone = -10;
        int row0ColumnCount = this.getColumnCount(SIGN_SHEET_INDEX, startRow); // 第startRow行的列数
        for (int i = 0; i < row0ColumnCount; i++) {
            String value = this.getValueAt(SIGN_SHEET_INDEX, startRow, i);
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
                default:
            }
        }

        if (columnIndexOfStudentId < 0 || columnIndexOfStudentName < 0 || columnIndexOfStudentPhone < 0) {
            //列属性中有未匹配的属性名
            throw new ExcelColumnNotFoundException("助教工作手册-签到表sheet列属性中有未匹配的属性名");
        }


        for (int i = 0; i < rowCountToSave; i++) {
            StudentAndClassDetailedWithSubjectsDto object=data.get(i);
            //遍历每行要填的学生上课信息对象
            // 填学员编号
            this.setValueAt(SIGN_SHEET_INDEX,i+1,columnIndexOfStudentId,object.getStudentId());
            // 填学员姓名
            this.setValueAt(SIGN_SHEET_INDEX,i+1,columnIndexOfStudentName,object.getStudentName());
            // 填学员联系方式
            this.setValueAt(SIGN_SHEET_INDEX,i+1,columnIndexOfStudentPhone,object.getStudentPhone());
            // 填任课教师要求
        }

        // 删除多余行
        this.removeRows(SIGN_SHEET_INDEX, rowCountToSave + 4 + startRow + 1,
                MAX_CLASS_STUDENTS_COUNT + startRow + 1);

        //根据上课次数删除多余列
        //TODO

        return true;
    }

    /**
     * 修改制作信息回访表
     *
     * @param data 从数据库中读取到的信息或手动输入的表格中读到的信息，以及用户输入的信息
     * @return
     * @throws IOException
     * @throws ClassTooManyStudentsException
     */
    public boolean writeCallbackSheet(List<StudentAndClassDetailedWithSubjectsDto> data) throws IOException, ClassTooManyStudentsException {
        // 获得班上学生总人数
        int rowCountToSave = data.size();
        if (rowCountToSave > MAX_CLASS_STUDENTS_COUNT) {
            throw new ClassTooManyStudentsException("班上学生人数超过了" + MAX_CLASS_STUDENTS_COUNT + "！");
        }

        int startRow=0;
        // 先扫描第startRow行找到"校区"、"班号"、"教师姓名"等信息所在列的位置
        int columnIndexOfCampus = -1, columnIndexOfClassId = -2, columnIndexOfTeacherName = -3, columnIndexOfAssistantName = -4, columnIndexOfStudentId = -7, columnIndexOfStudentName = -9;
        int row0ColumnCount = this.getColumnCount(CALLBACK_SHEET_INDEX, startRow); // 第startRow行的列数
        for (int i = 0; i < row0ColumnCount; i++) {
            String value = this.getValueAt(CALLBACK_SHEET_INDEX, startRow, i);
            switch (value) {
                case CAMPUS_COLUMN:
                    columnIndexOfCampus = i;
                    break;
                case CLASS_ID_COLUMN:
                    columnIndexOfClassId = i;
                    break;
                case TEACHER_NAME_COLUMN:
                    columnIndexOfTeacherName = i;
                    break;
                case ASSISTANT_NAME_COLUMN:
                    columnIndexOfAssistantName = i;
                    break;
                case STUDENT_ID_COLUMN:
                    columnIndexOfStudentId = i;
                    break;
                case STUDENT_NAME_COLUMN:
                    columnIndexOfStudentName = i;
                    break;
                default:
            }
        }

        if (columnIndexOfCampus < 0 || columnIndexOfClassId < 0 || columnIndexOfTeacherName < 0 || columnIndexOfAssistantName < 0
                || columnIndexOfStudentId < 0 || columnIndexOfStudentName < 0 ) {
            //列属性中有未匹配的属性名
            throw new ExcelColumnNotFoundException("助教工作手册-首课回访表sheet列属性中有未匹配的属性名");
        }


        for (int i = 0; i < rowCountToSave; i++) {
            StudentAndClassDetailedWithSubjectsDto object=data.get(i);
            //遍历每行要填的学生上课信息对象
            // 填校区
            this.setValueAt(CALLBACK_SHEET_INDEX,i+1,columnIndexOfCampus,object.getClassCampus());
            // 填班号
            this.setValueAt(CALLBACK_SHEET_INDEX,i+1,columnIndexOfClassId,object.getClassId());
            // 填教师姓名
            this.setValueAt(CALLBACK_SHEET_INDEX,i+1,columnIndexOfTeacherName,object.getTeacherName());
            // 填助教
            this.setValueAt(CALLBACK_SHEET_INDEX,i+1,columnIndexOfAssistantName,object.getAssistantName());
            // 填学员编号
            this.setValueAt(CALLBACK_SHEET_INDEX,i+1,columnIndexOfStudentId,object.getStudentId());
            // 填学员姓名
            this.setValueAt(CALLBACK_SHEET_INDEX,i+1,columnIndexOfStudentName,object.getStudentName());
        }

        // 删除多余行
        this.removeRows(CALLBACK_SHEET_INDEX, rowCountToSave + 4, MAX_CLASS_STUDENTS_COUNT);

        return true;
    }

    /**
     * 修改制作座位表，这里暂时不做处理标准，
     * 若之后有更改要另外操作请使用 {@link SeatTemplate}
     *
     * @param data 从花名册中读取到的信息以及用户输入的信息
     * @return
     * @throws IOException
     */
    public boolean setSeatSheet(List<StudentAndClassDetailedWithSubjectsDto> data) throws IOException {
        // TODO
        return true;
    }

    /**
     * 使用巴啦啦能量！完成对助教工作手册的所有处理（不含开班电话）！
     *
      * @param data 从花名册或数据库中读取到的信息以及用户输入的信息
     * @return
     * @throws IOException
     */
    public boolean writeAssistantTutorialWithoutSeatTable(List<StudentAndClassDetailedWithSubjectsDto> data) throws IOException {
        return writeClassStartSheet(data) && writeSignSheet(data) && writeCallbackSheet(data);
    }
}
