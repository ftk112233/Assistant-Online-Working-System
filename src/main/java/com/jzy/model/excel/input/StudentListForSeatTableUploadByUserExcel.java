package com.jzy.model.excel.input;

import com.jzy.manager.constant.ExcelConstants;
import com.jzy.manager.exception.ExcelColumnNotFoundException;
import com.jzy.manager.exception.InputFileTypeException;
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
import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName StudentListExcelForSeatTable
 * @description 单独用来制作座位表的用户手动上传的文件
 * @date 2019/10/30 12:49
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentListForSeatTableUploadByUserExcel extends Excel implements Serializable {
    private static final long serialVersionUID = 7525649970044104312L;

    private static final String STUDENT_NAME_COLUMN = ExcelConstants.STUDENT_NAME_COLUMN_3;

    /**
     * 有效信息开始的行
     */
    private static int startRow = 0;


    /**
     * 按顺序存储学员姓名列中的元素，该顺序将于座位表的先后顺序对应
     */
    private List<String> studentNames = new ArrayList<>();

    public StudentListForSeatTableUploadByUserExcel() {
    }

    public StudentListForSeatTableUploadByUserExcel(String inputFile) throws IOException, InputFileTypeException {
        super(inputFile);
    }

    public StudentListForSeatTableUploadByUserExcel(File file) throws IOException, InputFileTypeException {
        super(file);
    }

    public StudentListForSeatTableUploadByUserExcel(InputStream inputStream, ExcelVersionEnum version) throws IOException, InputFileTypeException {
        super(inputStream, version);
    }

    public StudentListForSeatTableUploadByUserExcel(Workbook workbook) {
        super(workbook);
    }

    /**
     * 第一行必须为属性名，在第一行中找到属性名为“学员姓名”的列，将所有姓名存储到map中并返回
     *
     * @return map类型成员变量studentNames
     */
    public List<String> readStudentNames() throws ExcelColumnNotFoundException {
        resetParam();

        int sheetIndex = 0;

        int targetRowColumnCount = this.getColumnCount(sheetIndex, startRow);

        /**
         * 学员姓名所在列
         */
        int columnIndexOfStudentName = -1;
        for (int i = 0; i < targetRowColumnCount; i++) {
            if (STUDENT_NAME_COLUMN.equals(this.getValueAt(sheetIndex, startRow, i))) {
                columnIndexOfStudentName = i;
                break;
            }
        }

        if (columnIndexOfStudentName < 0) {
            //列属性中有未匹配的属性名
            throw new ExcelColumnNotFoundException("名单列属性中有未匹配的属性名");
        }

        for (int i = startRow + 1; i < getRowCount(sheetIndex); i++) {
            // 遍历表格所有行
            String value = this.getValueAt(sheetIndex, i, columnIndexOfStudentName);
            if (!StringUtils.isEmpty(value)) {
                // “学员姓名”列对应行元素非空
                studentNames.add(value);
            }
        }

        return studentNames;
    }

    /**
     * 重置当前工作表对象的临时存储成员变量
     */
    @Override
    public void resetParam() {
        studentNames = new ArrayList<>();
    }

}
