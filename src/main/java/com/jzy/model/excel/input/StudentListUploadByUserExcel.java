package com.jzy.model.excel.input;

import com.jzy.manager.constant.ExcelConstants;
import com.jzy.manager.exception.ExcelColumnNotFoundException;
import com.jzy.manager.exception.InvalidFileTypeException;
import com.jzy.model.dto.StudentAndClassDetailedWithSubjectsDto;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName StudentListExcel
 * @description 学生花名册用户手动上传（未开启黑魔法时）
 * @date 2019/11/1 11:07
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentListUploadByUserExcel extends Excel implements Serializable {
    private static final long serialVersionUID = 5477415462970462167L;

    private static final String STUDENT_ID_COLUMN = ExcelConstants.STUDENT_ID_COLUMN;

    private static final String STUDENT_NAME_COLUMN = ExcelConstants.STUDENT_NAME_COLUMN;

    private static final String STUDENT_PHONE_COLUMN = ExcelConstants.STUDENT_PHONE_COLUMN;

    private static final String CLASS_ID_COLUMN = ExcelConstants.CLASS_ID_COLUMN_2;

    private static final String CLASS_NAME_COLUMN = ExcelConstants.CLASS_NAME_COLUMN_2;

    /**
     * 有效信息开始的行
     */
    private static int startRow = 0;

    /**
     * 读取到的信息封装成StudentAndClassDto对象
     */
    private List<StudentAndClassDetailedWithSubjectsDto> output;

    public StudentListUploadByUserExcel() {
    }

    public StudentListUploadByUserExcel(String inputFile) throws IOException, InvalidFileTypeException {
        super(inputFile);
    }

    public StudentListUploadByUserExcel(File file) throws IOException, InvalidFileTypeException {
        super(file);
    }

    public StudentListUploadByUserExcel(InputStream inputStream, ExcelVersionEnum version) throws IOException, InvalidFileTypeException {
        super(inputStream, version);
    }

    public StudentListUploadByUserExcel(Workbook workbook) {
        super(workbook);
    }

    /**
     * 输入班级编号筛选班级下的所有学生有用信息，如：学员编号、学员姓名、学员联系方式
     *
     * @param classId 编号
     * @throws ExcelColumnNotFoundException 列属性中有未匹配的属性名
     */
    public List<StudentAndClassDetailedWithSubjectsDto> readStudentAndClassInfoByClassIdFromExcel(String classId) throws ExcelColumnNotFoundException {
        resetParam();

        if (StringUtils.isEmpty(classId)) {
            return output;
        }

        int sheetIx = 0;

        // 先扫描第startRow行找到"学员号"、"姓名"、"手机"等信息所在列的位置
        int columnIndexOfStudentId = -1, columnIndexOfStudentName = -2, columnIndexOfStudentPhone = -3, columnIndexOfClassId = -15, columnIndexOfClassName = -16;
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
                case CLASS_ID_COLUMN:
                    columnIndexOfClassId = i;
                    break;
                case CLASS_NAME_COLUMN:
                    columnIndexOfClassName = i;
                    break;
                default:
            }
        }

        if (columnIndexOfStudentId < 0 || columnIndexOfStudentName < 0 || columnIndexOfStudentPhone < 0 || columnIndexOfClassId < 0 || columnIndexOfClassName < 0) {
            //列属性中有未匹配的属性名
            throw new ExcelColumnNotFoundException("上传的学生花名册列属性中有未匹配的属性名");
        }

        int rowCount = this.getRowCount(sheetIx); // 表的总行数
        for (int i = startRow + 1; i < rowCount; i++) {
            if (classId.equals(this.getValueAt(sheetIx, i, columnIndexOfClassId))) { // 找到班级编码匹配的行
                StudentAndClassDetailedWithSubjectsDto tmp = new StudentAndClassDetailedWithSubjectsDto();
                tmp.setClassId(classId);
                tmp.setClassName(this.getValueAt(sheetIx, i, columnIndexOfClassName));
                tmp.setStudentId(this.getValueAt(sheetIx, i, columnIndexOfStudentId));
                tmp.setStudentName(this.getValueAt(sheetIx, i, columnIndexOfStudentName));
                tmp.setStudentPhone(this.getValueAt(sheetIx, i, columnIndexOfStudentPhone));
                output.add(tmp);
            }
        }

        return output;
    }

    @Override
    public void resetParam() {
        output = new ArrayList<>();
    }

    public static void main(String[] args) throws IOException {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "a");
        map.put(1, "2");
        map.remove(1);
        System.out.println(map.get(1));
    }
}
