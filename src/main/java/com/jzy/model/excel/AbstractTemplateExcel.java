package com.jzy.model.excel;

import com.jzy.manager.exception.InvalidFileTypeException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName AbstractTemplateExcel
 * @Author JinZhiyun
 * @Description 待输出的模板表格的抽象类。模板类型的表格，通常不用读取数据。因此也不用对列名进行匹配检测等。
 * @Date 2020/1/12 15:13
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractTemplateExcel extends DefaultExcel{
    private static final long serialVersionUID = 8577982569067369781L;

    public AbstractTemplateExcel() {
    }

    public AbstractTemplateExcel(String inputFile) throws IOException, InvalidFileTypeException {
        super(inputFile);
    }

    public AbstractTemplateExcel(File file) throws IOException, InvalidFileTypeException {
        super(file);
    }

    public AbstractTemplateExcel(InputStream inputStream, ExcelVersionEnum version) throws IOException, InvalidFileTypeException {
        super(inputStream, version);
    }

    public AbstractTemplateExcel(Workbook workbook) {
        super(workbook);
    }
}
