package com.jzy.model.excel;

import com.jzy.manager.exception.ExcelColumnNotFoundException;
import com.jzy.manager.exception.InvalidFileTypeException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName AbstractInputExcel
 * @Author JinZhiyun
 * @Description 待读取的输入表格的抽象类。input类别的表格目的就是为了要从表格中读取信息，封装成对象至成员变量中。
 * @Date 2020/1/12 15:13
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractInputExcel extends DefaultExcel {
    private static final long serialVersionUID = 7458303551368759495L;

    public AbstractInputExcel(String inputFile) throws IOException, InvalidFileTypeException {
        super(inputFile);
    }

    public AbstractInputExcel(File file) throws IOException, InvalidFileTypeException {
        super(file);
    }

    public AbstractInputExcel(InputStream inputStream, ExcelVersionEnum version) throws IOException, InvalidFileTypeException {
        super(inputStream, version);
    }

    public AbstractInputExcel(Workbook workbook) {
        super(workbook);
    }

    public AbstractInputExcel(ExcelVersionEnum version) throws InvalidFileTypeException {
        super(version);
    }

    /**
     * input类别的表格读取信息，封装成对象至成员变量中。此方法重置所有表示读取结果的成员变量
     */
    @Override
    public abstract void resetOutput();

    /**
     * 重置所有表示规定列的索引值的成员变量。
     */
    @Override
    public abstract void resetColumnIndex();

    /**
     * 找到当前sheet指定列名称对应的列的索引
     *
     * @param sheetIx 要处理的sheet的索引
     * @throws ExcelColumnNotFoundException 未找到指定列名称的异常
     */
    protected abstract void findColumnIndexOfSpecifiedName(int sheetIx) throws ExcelColumnNotFoundException;
}
