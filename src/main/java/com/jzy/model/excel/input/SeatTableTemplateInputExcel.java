package com.jzy.model.excel.input;

import com.jzy.manager.exception.ExcelSheetNameInvalidException;
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
 * @ClassName SeatTableTemplateInputExcel
 * @Author JinZhiyun
 * @Description 导入的座位表模板的模型类
 * @Date 2019/11/28 11:34
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SeatTableTemplateInputExcel extends Excel implements Serializable {
    private static final long serialVersionUID = -4498593702973804852L;

    public SeatTableTemplateInputExcel() {
    }

    public SeatTableTemplateInputExcel(String inputFile) throws IOException, InputFileTypeException {
        super(inputFile);
    }

    public SeatTableTemplateInputExcel(File file) throws IOException, InputFileTypeException {
        super(file);
    }

    public SeatTableTemplateInputExcel(InputStream inputStream, ExcelVersionEnum version) throws IOException, InputFileTypeException {
        super(inputStream, version);
    }

    public SeatTableTemplateInputExcel(Workbook workbook) {
        super(workbook);
    }

    /**
     * 从座位表模板中读取的教室门牌号
     */
    private List<String> classrooms;


    /**
     * 根据输入输入的座位表读取教室信息
     *
     * @return
     * @throws IOException
     */
    public List<String> readSeatTable() throws IOException {
        resetParam();

        int sheetCount=this.getSheetCount();
        for (int i = 0; i < sheetCount; i++) {
            String classroom=this.getSheetName(i);
            if (!StringUtils.isNumeric(classroom)){
                //如果sheet名（教室门牌号）不是纯数字
                throw new ExcelSheetNameInvalidException("教室门牌号不是纯数字!");
            }
            classrooms.add(classroom);
        }

        return classrooms;
    }

    @Override
    public void resetParam() {
        classrooms=new ArrayList<>();
    }
}

