package com.jzy.model.excel;

import com.jzy.manager.exception.InputFileTypeException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Excel 包装类，对poi的二次封装
 *
 * @author zhangyi
 * @version 1.0 2016/01/27
 */
public class Excel implements Serializable, Resettable, ExcelValidity {
    private static final long serialVersionUID = 5628415838137969509L;

    /**
     * 工作簿对象
     */
    @Getter
    protected Workbook workbook;

    /**
     * excel版本枚举对象
     */
    @Getter
    protected ExcelVersionEnum version;

    /**
     * 输入文件路径
     */
    @Getter
    private String inputFilePath;

    /**
     * 输出流
     */
    private OutputStream os;

    /**
     * 日期格式
     */
    @Getter
    @Setter
    private String pattern;

    public Excel() {
    }

    /**
     * 由输入文件路径构造excel对象
     *
     * @param inputFile 输入文件路径
     * @throws IOException
     * @throws InputFileTypeException
     */
    public Excel(String inputFile) throws IOException, InputFileTypeException {
        this(new File(inputFile));
    }

    /**
     * 由一个File构造excel对象
     *
     * @param file 输入文件对象
     * @throws IOException
     * @throws InputFileTypeException
     */
    public Excel(File file) throws IOException, InputFileTypeException {
        String inputFile = file.getName();
        if (inputFile.endsWith(ExcelVersionEnum.VERSION_2003.getSuffix())) {
            version = ExcelVersionEnum.VERSION_2003;
            workbook = new HSSFWorkbook(new FileInputStream(file));
        } else if (inputFile.endsWith(ExcelVersionEnum.VERSION_2007.getSuffix())) {
            version = ExcelVersionEnum.VERSION_2007;
            workbook = new XSSFWorkbook(new FileInputStream(file));
        } else {
            throw new InputFileTypeException("输入文件格式不是.xls或.xlsx");
        }
        this.inputFilePath = inputFile;
    }

    /**
     * 由一个输入流和版本枚举对象构造excel对象
     *
     * @param inputStream 输入流对象
     * @param version     excel版本的枚举对象
     * @throws IOException
     * @throws InputFileTypeException
     */
    public Excel(InputStream inputStream, ExcelVersionEnum version) throws IOException, InputFileTypeException {
        if (version.equals(ExcelVersionEnum.VERSION_2003)) {
            this.version = version;
            workbook = new HSSFWorkbook(inputStream);
        } else if (version.equals(ExcelVersionEnum.VERSION_2007)) {
            this.version = version;
            workbook = new XSSFWorkbook(inputStream);
        } else {
            throw new InputFileTypeException("输入文件格式不是.xls或.xlsx");
        }
    }

    /**
     * 由一个工作簿构造excel对象
     *
     * @param workbook 工作簿对象
     */
    public Excel(Workbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public String toString() {
        return "共有 " + getSheetCount() + "个sheet 页！";
    }

    public String toString(int sheetIx) throws IOException {
        return "第 " + (sheetIx + 1) + "个sheet 页，名称： " + getSheetName(sheetIx) + "，共 " + getRowCount(sheetIx) + "行！";
    }

    /**
     * 根据后缀判断是否为 Excel 文件，后缀匹配xls和xlsx
     *
     * @param pathname 输入excel路径
     * @return
     */
    public static boolean isExcel(String pathname) {
        if (pathname == null) {
            return false;
        }
        return pathname.endsWith(".xls") || pathname.endsWith(".xlsx");
    }

    /**
     * 读取 Excel 第一页所有数据
     *
     * @return
     * @throws Exception
     */
    public List<List<String>> read() throws Exception {
        return read(0, 0, getRowCount(0) - 1);
    }

    /**
     * 读取指定sheet 页所有数据
     *
     * @param sheetIx 指定 sheet 页，从 0 开始
     * @return
     * @throws Exception
     */
    public List<List<String>> read(int sheetIx) throws Exception {
        return read(sheetIx, 0, getRowCount(sheetIx) - 1);
    }

    /**
     * 读取指定sheet 页指定行数据
     *
     * @param sheetIx 指定 sheet 页，从 0 开始
     * @param start   指定开始行，从 0 开始
     * @param end     指定结束行，从 0 开始
     * @return
     * @throws Exception
     */
    public List<List<String>> read(int sheetIx, int start, int end) throws Exception {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        List<List<String>> list = new ArrayList<List<String>>();

        if (end > getRowCount(sheetIx)) {
            end = getRowCount(sheetIx);
        }

        int cols = sheet.getRow(0).getLastCellNum(); // 第一行总列数

        for (int i = start; i <= end; i++) {
            List<String> rowList = new ArrayList<String>();
            Row row = sheet.getRow(i);
            for (int j = 0; j < cols; j++) {
                if (row == null) {
                    rowList.add(null);
                    continue;
                }
                rowList.add(getCellValueToString(row.getCell(j)));
            }
            list.add(rowList);
        }

        return list;
    }

    /**
     * 将数据写入到 Excel 默认第一页中，从第1行开始写入
     *
     * @param rowData 数据
     * @return
     * @throws IOException
     */
    public boolean writeRow(List<List<String>> rowData) throws IOException {
        return writeRow(0, rowData, 0);
    }

    /**
     * 将数据写入到 Excel 新创建的 Sheet 页
     *
     * @param rowData   数据
     * @param sheetName 长度为1-31，不能包含后面任一字符: ：\ / ? * [ ]
     * @return
     * @throws IOException
     */
    public boolean writeRow(List<List<String>> rowData, String sheetName, boolean isNewSheet) throws IOException {
        Sheet sheet = null;
        if (isNewSheet) {
            sheet = workbook.createSheet(sheetName);
        } else {
            sheet = workbook.createSheet();
        }
        int sheetIx = workbook.getSheetIndex(sheet);
        return writeRow(sheetIx, rowData, 0);
    }

    /**
     * 将数据追加到sheet页最后
     *
     * @param rowData  数据
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param isAppend 是否追加,true 追加，false 重置sheet再添加
     * @return
     * @throws IOException
     */
    public boolean writeRow(int sheetIx, List<List<String>> rowData, boolean isAppend) throws IOException {
        if (isAppend) {
            return writeRow(sheetIx, rowData, getRowCount(sheetIx));
        } else {// 清空再添加
            clearSheet(sheetIx);
            return writeRow(sheetIx, rowData, 0);
        }
    }

    /**
     * 将数据写入到 Excel 指定 Sheet 页指定开始行中,指定行后面数据向后移动
     *
     * @param rowData  数据
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param startRow 指定开始行，从 0 开始
     * @return
     * @throws IOException
     */
    public boolean writeRow(int sheetIx, List<List<String>> rowData, int startRow) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        int dataSize = rowData.size();
        if (getRowCount(sheetIx) > 0) {// 如果小于等于0，则一行都不存在
            sheet.shiftRows(startRow, getRowCount(sheetIx), dataSize);
        }
        for (int i = 0; i < dataSize; i++) {
            Row row = sheet.createRow(i + startRow);
            for (int j = 0; j < rowData.get(i).size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(rowData.get(i).get(j) + "");
            }
        }
        return true;
    }

    /**
     * 将同一个值value设置在指定区域内的每一个单元格
     *
     * @param sheetIx     sheet号
     * @param value       值
     * @param startColumn 起始列（含）
     * @param endColumn   结束列（不含）
     * @param startRow    起始行（含）
     * @param endRow      结束行（不含）
     * @return
     * @throws IOException
     */
    public boolean setRepeatValueAt(int sheetIx, String value, int startColumn, int endColumn, int startRow, int endRow)
            throws IOException {
        for (int i = startColumn; i < endColumn; i++) {
            for (int j = startRow; j < endRow; j++) {
                this.setValueAt(sheetIx, j, i, value);
            }
        }
        return true;
    }

    /**
     * 将columnData中的列数据填充到指定位置
     *
     * @param sheetIx     sheet号
     * @param columnData  [[第一列数据], [第二列数据], [第三列数据], ....]
     * @param startRow    起始的行位置
     * @param startColumn 起始的列位置
     * @return
     * @throws IOException
     */
    public boolean modifyColumn(int sheetIx, List<List<String>> columnData, int startRow, int startColumn)
            throws IOException {
        int dataColumnCount = columnData.size();
        for (int i = startColumn; i < startColumn + dataColumnCount; i++) {
            int rowCount = columnData.get(i - startColumn).size();
            for (int j = startRow; j < startRow + rowCount; j++) {
                // 修改第j行，第i列元素的值
                this.setValueAt(sheetIx, j, i, columnData.get(i - startColumn).get(j - startRow));
            }
        }
        return true;
    }

    /**
     * 设置cell 样式
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param colIndex 指定列，从 0 开始
     * @return
     * @throws IOException
     */
    public boolean setStyle(int sheetIx, int rowIndex, int colIndex, CellStyle style) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        // sheet.autoSizeColumn(colIndex, true);// 设置列宽度自适应
        sheet.setColumnWidth(colIndex, 4000);

        Cell cell = sheet.getRow(rowIndex).getCell(colIndex);
        cell.setCellStyle(style);

        return true;
    }

    /**
     *
     * 设置样式
     *
     * @param type
     *            1：标题 2：第一行
     * @return
     */
    // public CellStyle makeStyle(int type) {
    // CellStyle style = workbook.createCellStyle();
    //
    // DataFormat format = workbook.createDataFormat();
    // style.setDataFormat(format.getFormat("@"));// // 内容样式 设置单元格内容格式是文本
    // style.setAlignment(CellStyle.ALIGN_CENTER);// 内容居中
    //
    // // style.setBorderTop(CellStyle.BORDER_THIN);// 边框样式
    // // style.setBorderRight(CellStyle.BORDER_THIN);
    // // style.setBorderBottom(CellStyle.BORDER_THIN);
    // // style.setBorderLeft(CellStyle.BORDER_THIN);
    //
    // Font font = workbook.createFont();// 文字样式
    //
    // if (type == 1) {
    // // style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);//颜色样式
    // // 前景颜色
    // // style.setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);//背景色
    // // style.setFillPattern(CellStyle.ALIGN_FILL);// 填充方式
    // font.setBold(true);
    // font.setFontHeight((short) 500);
    // }
    //
    // if (type == 2) {
    // font.setBold(true);
    // font.setFontHeight((short) 300);
    // }
    //
    // style.setFont(font);
    //
    // return style;
    // }

    /**
     * 合并单元格
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param firstRow 开始行
     * @param lastRow  结束行
     * @param firstCol 开始列
     * @param lastCol  结束列
     */
    public void region(int sheetIx, int firstRow, int lastRow, int firstCol, int lastCol) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    /**
     * 指定行是否为空
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定开始行，从 0 开始
     * @return true 不为空，false 不行为空
     * @throws IOException
     */
    public boolean isRowNull(int sheetIx, int rowIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        return sheet.getRow(rowIndex) == null;
    }

    /**
     * 创建行，若行存在，则清空
     *
     * @param sheetIx  指定 sheet 页，从 0 开始
     * @param rowIndex 指定创建行，从 0 开始
     * @return
     * @throws IOException
     */
    public boolean createRow(int sheetIx, int rowIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        sheet.createRow(rowIndex);
        return true;
    }

    /**
     * 指定单元格是否为空
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定开始行，从 0 开始
     * @param colIndex 指定开始列，从 0 开始
     * @return true 行不为空，false 行为空
     * @throws IOException
     */
    public boolean isCellNull(int sheetIx, int rowIndex, int colIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        if (!isRowNull(sheetIx, rowIndex)) {
            return false;
        }
        Row row = sheet.getRow(rowIndex);
        return row.getCell(colIndex) == null;
    }

    /**
     * 创建单元格
     *
     * @param sheetIx  指定 sheet 页，从 0 开始
     * @param rowIndex 指定行，从 0 开始
     * @param colIndex 指定创建列，从 0 开始
     * @return true 列为空，false 行不为空
     * @throws IOException
     */
    public boolean createCell(int sheetIx, int rowIndex, int colIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        Row row = sheet.getRow(rowIndex);
        row.createCell(colIndex);
        return true;
    }

    /**
     * 返回sheet 中的行数
     *
     * @param sheetIx 指定 Sheet 页，从 0 开始
     * @return
     */
    public int getRowCount(int sheetIx) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        if (sheet.getPhysicalNumberOfRows() == 0) {
            return 0;
        }
        return sheet.getLastRowNum() + 1;

    }

    /**
     * 返回所在行的列数
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定行，从0开始
     * @return 返回-1 表示所在行为空
     */
    public int getColumnCount(int sheetIx, int rowIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        Row row = sheet.getRow(rowIndex);
        return row == null ? -1 : row.getLastCellNum();

    }

    /**
     * 设置row 和 column 位置的单元格值
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定行，从0开始
     * @param colIndex 指定列，从0开始
     * @param value    值
     * @return
     * @throws IOException
     */
    public boolean setValueAt(int sheetIx, int rowIndex, int colIndex, String value) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        Cell cell=sheet.getRow(rowIndex).getCell(colIndex);
        if (cell == null){
            this.createCell(sheetIx,rowIndex,colIndex);
            cell=sheet.getRow(rowIndex).getCell(colIndex);
        }
        cell.setCellValue(value);
        return true;
    }

    /**
     * 返回 row 和 column 位置的单元格值
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定行，从0开始
     * @param colIndex 指定列，从0开始
     * @return
     */
    public String getValueAt(int sheetIx, int rowIndex, int colIndex) {
        if (rowIndex < 0 || colIndex < 0) {
            return null;
        }
        Sheet sheet = workbook.getSheetAt(sheetIx);
        return getCellValueToString(sheet.getRow(rowIndex).getCell(colIndex));
    }

    /**
     * 重置指定行的值
     *
     * @param rowData  数据
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定行，从0开始
     * @return
     * @throws IOException
     */
    public boolean setRowValue(int sheetIx, List<String> rowData, int rowIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        Row row = sheet.getRow(rowIndex);
        for (int i = 0; i < rowData.size(); i++) {
            row.getCell(i).setCellValue(rowData.get(i));
        }
        return true;
    }

    /**
     * 返回指定行的值的集合
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定行，从0开始
     * @return
     */
    public List<String> getRowValue(int sheetIx, int rowIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        Row row = sheet.getRow(rowIndex);
        List<String> list = new ArrayList<String>();
        if (row == null) {
            list.add(null);
        } else {
            for (int i = 0; i < row.getLastCellNum(); i++) {
                list.add(getCellValueToString(row.getCell(i)));
            }
        }
        return list;
    }

    /**
     * 返回列的值的集合
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定行，从0开始
     * @param colIndex 指定列，从0开始
     * @return
     */
    public List<String> getColumnValue(int sheetIx, int rowIndex, int colIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        List<String> list = new ArrayList<String>();
        for (int i = rowIndex; i < getRowCount(sheetIx); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                list.add(null);
                continue;
            }
            list.add(getCellValueToString(sheet.getRow(i).getCell(colIndex)));
        }
        return list;
    }

    /**
     * 获取excel 中sheet 总页数
     *
     * @return
     */
    public int getSheetCount() {
        return workbook.getNumberOfSheets();
    }

    public Sheet createSheet() {
        return workbook.createSheet();
    }

    public Sheet createSheet(String sheetName) {
        return workbook.createSheet(sheetName);
    }

    /**
     * 设置sheet名称，长度为1-31，不能包含后面任一字符: ：\ / ? * [ ]
     *
     * @param sheetIx 指定 Sheet 页，从 0 开始，//
     * @param name
     * @return
     * @throws IOException
     */
    public boolean setSheetName(int sheetIx, String name) throws IOException {
        workbook.setSheetName(sheetIx, name);
        return true;
    }

    /**
     * 获取 sheet名称
     *
     * @param sheetIx 指定 Sheet 页，从 0 开始
     * @return
     * @throws IOException
     */
    public String getSheetName(int sheetIx) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        return sheet.getSheetName();
    }

    /**
     * 获取sheet的索引，从0开始
     *
     * @param name sheet 名称
     * @return -1表示该未找到名称对应的sheet
     */
    public int getSheetIndex(String name) {
        return workbook.getSheetIndex(name);
    }

    /**
     * 删除指定sheet
     *
     * @param sheetIx 指定 Sheet 页，从 0 开始
     * @return
     * @throws IOException
     */
    public boolean removeSheetAt(int sheetIx) throws IOException {
        workbook.removeSheetAt(sheetIx);
        return true;
    }

    /**
     * @return boolean
     * @author JinZhiyun
     * @description 删除指定名称的sheet
     * @date 15:25 2019/10/30
     * @Param [sheetName]
     **/
    public boolean removeSheetByName(String sheetName) throws IOException {
        workbook.removeSheetAt(getSheetIndex(sheetName));
        return true;
    }

    /**
     * 删除指定sheet中行，改变该行之后行的索引
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定行，从0开始
     * @return
     * @throws IOException
     */
    public boolean removeRow(int sheetIx, int rowIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        int lastRowNum = sheet.getLastRowNum();
        if (rowIndex >= 0 && rowIndex < lastRowNum) {
            sheet.shiftRows(rowIndex + 1, lastRowNum, -1);// 将行号为rowIndex+1一直到行号为lastRowNum的单元格全部上移一行，以便删除rowIndex行
        }
        if (rowIndex == lastRowNum) {
            Row removingRow = sheet.getRow(rowIndex);
            if (removingRow != null) {
                sheet.removeRow(removingRow);
            }
        }
        return true;
    }

    /**
     * 删除指定sheet中rowIndexStart（含）到rowIndexEnd（不含）的行，改变该行之后行的索引
     *
     * @param sheetIx       指定 Sheet 页，从 0 开始
     * @param rowIndexStart 起始行（含）
     * @param rowIndexEnd   结束行（不含）
     * @return
     * @throws IOException
     */
    public boolean removeRows(int sheetIx, int rowIndexStart, int rowIndexEnd) throws IOException {
        for (int i = rowIndexEnd - 1; i >= rowIndexStart; i--) {
            this.removeRow(sheetIx, i);
        }
        return true;
    }

    /**
     * 设置sheet 页的索引
     *
     * @param sheetName Sheet 名称
     * @param sheetIx   Sheet 索引，从0开始
     */
    public void setSheetOrder(String sheetName, int sheetIx) {
        workbook.setSheetOrder(sheetName, sheetIx);
    }

    /**
     * 清空指定sheet页（先删除后添加并指定sheetIx）
     *
     * @param sheetIx 指定 Sheet 页，从 0 开始
     * @return
     * @throws IOException
     */
    public boolean clearSheet(int sheetIx) throws IOException {
        String sheetName = getSheetName(sheetIx);
        removeSheetAt(sheetIx);
        workbook.createSheet(sheetName);
        setSheetOrder(sheetName, sheetIx);
        return true;
    }

    /**
     * 将当前修改保存覆盖至输入文件inputFilePath中
     *
     * @throws IOException
     */
    public void submitWrite() throws IOException {
        if (!StringUtils.isEmpty(inputFilePath)) {
            os = new FileOutputStream(new File(inputFilePath));
            submitWrite(os);
        }
    }


    /**
     * 将当前修改保存到输出流
     *
     * @param outputStream 输出流
     * @throws IOException
     */
    public void submitWrite(OutputStream outputStream) throws IOException {
        this.workbook.write(outputStream);
    }

    /**
     * 将当前修改保存到outputPath对应的文件中
     *
     * @param outputPath 输出文件的路径
     * @throws IOException
     */
    public void submitWrite(String outputPath) throws IOException {
        os = new FileOutputStream(new File(outputPath));
        submitWrite(os);
    }

    /**
     * 关闭流
     *
     * @throws IOException
     */
    public void close() throws IOException {
        if (os != null) {
            os.close();
        }
        workbook.close();
    }

    /**
     * 转换单元格的类型为String 默认的 <br>
     * 默认的数据类型：CELL_TYPE_BLANK(3), CELL_TYPE_BOOLEAN(4), CELL_TYPE_ERROR(5),CELL_TYPE_FORMULA(2), CELL_TYPE_NUMERIC(0),
     * CELL_TYPE_STRING(1)
     *
     * @param cell
     * @return
     */
    private String getCellValueToString(Cell cell) {
        String strCell = "";
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    if (pattern != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        strCell = sdf.format(date);
                    } else {
                        strCell = date.toString();
                    }
                    break;
                }
                // 不是日期格式，则防止当数字过长时以科学计数法显示
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                strCell = cell.toString();
                break;
            case Cell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            default:
                break;
        }
        return strCell;
    }
}
