package com.jzy.model.excel.input;

import com.jzy.manager.constant.ExcelConstants;
import com.jzy.manager.exception.ExcelColumnNotFoundException;
import com.jzy.manager.exception.InputFileTypeException;
import com.jzy.model.dto.ClassDetailedDto;
import com.jzy.model.entity.Teacher;
import com.jzy.model.excel.Excel;
import com.jzy.model.excel.ExcelVersionEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName ClassArrangementExcel
 * @Author JinZhiyun
 * @Description 处理输排班表的模型类
 * @Date 2019/11/23 9:15
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ClassArrangementExcel extends Excel {
    private static final long serialVersionUID = -877510467520953391L;

    private static final String TEACHER_NAME_COLUMN = ExcelConstants.TEACHER_NAME_COLUMN;

    private static final String ASSISTANT_NAME_COLUMN = ExcelConstants.ASSISTANT_NAME_COLUMN;

    private static final String CLASS_ID_COLUMN = ExcelConstants.CLASS_ID_COLUMN;

    private static final String CLASS_NAME_COLUMN = ExcelConstants.CLASS_NAME_COLUMN;

    private static final String CLASS_TIME_COLUMN = ExcelConstants.CLASS_TIME_COLUMN;

    private static final String CLASSROOM_COLUMN = ExcelConstants.CLASSROOM_COLUMN;

    public ClassArrangementExcel() {
    }

    public ClassArrangementExcel(String inputFile) throws IOException, InputFileTypeException {
        super(inputFile);
    }

    public ClassArrangementExcel(File file) throws IOException, InputFileTypeException {
        super(file);
    }

    public ClassArrangementExcel(InputStream inputStream, ExcelVersionEnum version) throws IOException, InputFileTypeException {
        super(inputStream, version);
    }

    public ClassArrangementExcel(Workbook workbook) {
        super(workbook);
    }

    /**
     * 有效信息开始的行
     */
    private static int startRow = 0;

    /**
     * 读取到的信息封装成Teacher对象
     */
    private Set<Teacher> teachers;

    /**
     * 读取到的信息封装成ClassDetailedDto对象
     */
    private List<ClassDetailedDto> classDetailedDtos;

    /**
     * 从学生花名册表中读取信息
     *  学生Student对象直接读出，教师teacher对象也直接读出，班级信息整体和学生、助教、教师封装成StudentAndClassDto
     *
     * @return 返回表格有效数据的行数
     * @throws ExcelColumnNotFoundException
     */
    public int readClassDetailFromExcel() throws ExcelColumnNotFoundException {
        resetParam();
        int sheetIx = 0;

        // 先扫描第startRow行找到"班级编码"、"班级名次"等信息所在列的位置
        int columnIndexOfTeacherName = -7, columnIndexOfAssistantName = -9, columnIndexOfClassId = -10, columnIndexOfClassName = -11, columnIndexOfClassTime = -12, columnIndexOfClassroom = -13;
        int row0ColumnCount = this.getColumnCount(sheetIx, startRow); // 第startRow行的列数
        for (int i = 0; i < row0ColumnCount; i++) {
            String value = this.getValueAt(sheetIx, startRow, i);
            switch (value) {
                case TEACHER_NAME_COLUMN:
                    columnIndexOfTeacherName = i;
                    break;
                case ASSISTANT_NAME_COLUMN:
                    columnIndexOfAssistantName = i;
                    break;
                case CLASS_ID_COLUMN:
                    columnIndexOfClassId = i;
                    break;
                case CLASS_NAME_COLUMN:
                    columnIndexOfClassName = i;
                    break;
                case CLASS_TIME_COLUMN:
                    columnIndexOfClassTime = i;
                    break;
                case CLASSROOM_COLUMN:
                    columnIndexOfClassroom = i;
                    break;
                default:
            }
        }

        if (columnIndexOfTeacherName < 0 || columnIndexOfAssistantName < 0 || columnIndexOfClassId < 0 || columnIndexOfClassName < 0
                || columnIndexOfClassTime < 0 || columnIndexOfClassroom < 0) {
            //列属性中有未匹配的属性名
            throw new ExcelColumnNotFoundException("助教排班表列属性中有未匹配的属性名");
        }

        int effectiveDataRowCount=0;

        int rowCount = this.getRowCount(sheetIx); // 表的总行数
        for (int i = startRow + 1; i < rowCount; i++) {
            if (StringUtils.isEmpty(this.getValueAt(sheetIx, i, columnIndexOfClassId))) {
                //当前行班级编码为空，跳过
                continue;
            } else {
                effectiveDataRowCount++;
            }

            String teacherName = this.getValueAt(sheetIx, i, columnIndexOfTeacherName);
            String assistantName = this.getValueAt(sheetIx, i, columnIndexOfAssistantName);
            String classId = this.getValueAt(sheetIx, i, columnIndexOfClassId);
            String className = this.getValueAt(sheetIx, i, columnIndexOfClassName);
            String classTime = this.getValueAt(sheetIx, i, columnIndexOfClassTime);
            String classroom = this.getValueAt(sheetIx, i, columnIndexOfClassroom);


            //先封装teacher
            Teacher teacher = new Teacher();
            teacher.setTeacherName(teacherName);
            teachers.add(teacher);

            //封装classDetailedDto
            ClassDetailedDto classDetailedDto = new ClassDetailedDto();
            classDetailedDto.setAssistantName(assistantName);
            classDetailedDto.setTeacherName(teacherName);
            classDetailedDto.setParsedClassId(classId);
            classDetailedDto.setClassName(className);
            classDetailedDto.setParsedClassroom(classroom);
            classDetailedDto.setParsedClassTime(classTime);
            classDetailedDtos.add(classDetailedDto);
        }

        return effectiveDataRowCount;
    }

    @Override
    public void resetParam() {
        teachers = new HashSet<>();
        classDetailedDtos = new ArrayList<>();
    }

    public static void main(String[] args) throws IOException, ExcelColumnNotFoundException, InputFileTypeException {
        ClassArrangementExcel excel = new ClassArrangementExcel("D:\\aows_resources\\toolbox\\example\\曹杨秋季助教排班.xlsx");
        excel.readClassDetailFromExcel();
        for (Teacher teacher : excel.getTeachers()) {
            System.out.println(teacher);
        }
        for (ClassDetailedDto classDetailedDto : excel.getClassDetailedDtos()) {
            System.out.println(classDetailedDto);
        }
    }
}
