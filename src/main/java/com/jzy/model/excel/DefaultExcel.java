package com.jzy.model.excel;

import com.jzy.manager.exception.ExcelTooManyRowsException;
import com.jzy.manager.exception.InvalidFileTypeException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName DefaultExcel
 * @Author JinZhiyun
 * @Description 默认表格处理类。一般普通的处理表格可以继承此类
 * @Date 2020/1/12 12:53
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class DefaultExcel extends Excel {
    private static final long serialVersionUID = 7769992970075361130L;

    /**
     * 默认的表格最大行数限制
     */
    protected static final int DEFAULT_MAX_ROW_COUNT = 30000;

    public DefaultExcel() {
    }

    public DefaultExcel(String inputFile) throws IOException, InvalidFileTypeException {
        super(inputFile);
    }

    public DefaultExcel(File file) throws IOException, InvalidFileTypeException {
        super(file);
    }

    public DefaultExcel(InputStream inputStream, ExcelVersionEnum version) throws IOException, InvalidFileTypeException {
        super(inputStream, version);
    }

    public DefaultExcel(Workbook workbook) {
        super(workbook);
    }

    @Override
    public boolean testRowCountValidityOfSheet(int sheetIdx) throws ExcelTooManyRowsException {
        return testRowCountValidityOfSheet(sheetIdx, DEFAULT_MAX_ROW_COUNT);
    }

    /**
     * 当前表格指定索引的sheet行数是否合法，不超过maxRowCount
     *
     * @param sheetIdx    sheet索引
     * @param maxRowCount 当前表格所有sheet行数是否合法，不超过maxRowCount
     * @return 不抛异常返回真
     * @throws ExcelTooManyRowsException 行数超过规定值，将规定的上限值和实际值都传给异常对象
     */
    protected boolean testRowCountValidityOfSheet(int sheetIdx, int maxRowCount) throws ExcelTooManyRowsException {
        int rowCount=getRowCount(sheetIdx);
        if (rowCount > maxRowCount){
            throw new ExcelTooManyRowsException(DEFAULT_MAX_ROW_COUNT, rowCount);
        }
        return true;
    }

    @Override
    public boolean testRowCountValidity() throws ExcelTooManyRowsException {
        return testRowCountValidity(DEFAULT_MAX_ROW_COUNT);
    }

    /**
     * 当前表格所有sheet行数是否合法，不超过maxRowCount
     *
     * @param maxRowCount 最大行数限制
     * @return 不抛异常返回真
     * @throws ExcelTooManyRowsException 行数超过规定值，将规定的上限值和实际值都传给异常对象
     */
    protected boolean testRowCountValidity(int maxRowCount) throws ExcelTooManyRowsException {
        for (int sheetIdx = 0; sheetIdx < getSheetCount(); sheetIdx++) {
            int rowCount=getRowCount(sheetIdx);
            System.out.println(rowCount);
            System.out.println(maxRowCount);
            if (rowCount > maxRowCount) {
                throw new ExcelTooManyRowsException(DEFAULT_MAX_ROW_COUNT, rowCount);
            }
        }
        return true;
    }
}
