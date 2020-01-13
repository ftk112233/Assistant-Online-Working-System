package com.jzy.model.excel.input;

import com.jzy.manager.constant.ExcelConstants;
import com.jzy.manager.exception.ExcelColumnNotFoundException;
import com.jzy.manager.exception.ExcelTooManyRowsException;
import com.jzy.manager.exception.InvalidFileTypeException;
import com.jzy.model.dto.StudentAndClassDetailedWithSubjectsDto;
import com.jzy.model.excel.AbstractInputExcel;
import com.jzy.model.excel.ExcelVersionEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
public class StudentListUploadByUserExcel extends AbstractInputExcel {
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

    /**
     * 规定名称的列的索引位置，初始值为-1无效值，即表示还没找到
     */
    private int columnIndexOfStudentId = -1;
    private int columnIndexOfStudentName = -1;
    private int columnIndexOfStudentPhone = -1;
    private int columnIndexOfClassId = -1;
    private int columnIndexOfClassName = -1;


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
     * @throws ExcelTooManyRowsException    行数超过规定值，将规定的上限值和实际值都传给异常对象
     */
    public List<StudentAndClassDetailedWithSubjectsDto> readStudentAndClassInfoByClassIdFromExcel(String classId) throws ExcelColumnNotFoundException, ExcelTooManyRowsException {
        resetOutput();

        if (StringUtils.isEmpty(classId)) {
            return output;
        }

        int sheetIx = 0;

        testRowCountValidityOfSheet(sheetIx);

        // 先扫描第startRow行找到"学员号"、"姓名"、"手机"等信息所在列的位置
        findColumnIndexOfSpecifiedName(sheetIx);

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
    public void resetOutput() {
        output = new ArrayList<>();
    }

    @Override
    public void resetColumnIndex() {
        columnIndexOfStudentId = -1;
        columnIndexOfStudentName = -1;
        columnIndexOfStudentPhone = -1;
        columnIndexOfClassId = -1;
        columnIndexOfClassName = -1;
    }

    @Override
    protected void findColumnIndexOfSpecifiedName(int sheetIx) throws ExcelColumnNotFoundException {
        resetColumnIndex();

        int row0ColumnCount = this.getColumnCount(sheetIx, startRow); // 第startRow行的列数
        for (int i = 0; i < row0ColumnCount; i++) {
            String value = this.getValueAt(sheetIx, startRow, i);
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
                    case CLASS_ID_COLUMN:
                        columnIndexOfClassId = i;
                        break;
                    case CLASS_NAME_COLUMN:
                        columnIndexOfClassName = i;
                        break;
                    default:
                }
            }
        }

        testColumnNameValidity();
    }

    public static void main(String[] args) throws IOException {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "a");
        map.put(1, "2");
        map.remove(1);
        System.out.println(map.get(1));
    }

    @Override
    public boolean testColumnNameValidity() throws ExcelColumnNotFoundException {
        if (columnIndexOfStudentId < 0) {
            throw new ExcelColumnNotFoundException(null, STUDENT_ID_COLUMN);
        }
        if (columnIndexOfStudentName < 0) {
            throw new ExcelColumnNotFoundException(null, STUDENT_NAME_COLUMN);
        }
        if (columnIndexOfStudentPhone < 0) {
            throw new ExcelColumnNotFoundException(null, STUDENT_PHONE_COLUMN);
        }
        if (columnIndexOfClassId < 0) {
            throw new ExcelColumnNotFoundException(null, CLASS_ID_COLUMN);
        }
        if (columnIndexOfClassName < 0) {
            throw new ExcelColumnNotFoundException(null, CLASS_NAME_COLUMN);
        }

        return true;
    }
}
