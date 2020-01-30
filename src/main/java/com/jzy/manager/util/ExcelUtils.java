package com.jzy.manager.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import sun.awt.SunHints;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName ExcelUtils
 * @description //TODO
 * @date 2019/12/9 10:26
 **/
public class ExcelUtils {
    public static void main(String[] args) throws Exception {
        // 给定两个初始值，标志出导出区域，两个行列组合的单元格
        int[] fromIndex = {0, 0};
        int[] toIndex = {9, 9};

        int imageWidth = 0;
        int imageHeight = 0;

        Workbook wb = WorkbookFactory.create(new File("C:\\Users\\92970\\Desktop\\补课单.xlsx"));
        Sheet sheet = wb.getSheetAt(0);
        List<CellRangeAddress> rangeAddress = sheet.getMergedRegions(); // 获取整个sheet中合并单元格组合的集合

        // 首先做初步的边界检测，如果指定区域是不合法的则抛出异常
        int rowSum = sheet.getPhysicalNumberOfRows();
        System.out.println(rowSum);
        int colSum = sheet.getRow(0).getPhysicalNumberOfCells();
        System.out.println(colSum);
        if (fromIndex[0] > rowSum || fromIndex[0] > toIndex[0] || toIndex[0] > rowSum) {
            throw new Exception("the rowIndex of the area is wrong!");
        }
        if (fromIndex[1] > colSum || fromIndex[1] > toIndex[1] || toIndex[1] > colSum) {
            throw new Exception("the colIndex of the area is wrong!");
        }

        // 计算实际需要载入内存的二维Cell数组的大小，剔除隐藏行列
        int rowSize = toIndex[0] + 1;
        int colSize = toIndex[1] + 1;

        // 遍历需要扫描的区域

        UserCell[][] cells = new UserCell[rowSize][colSize];
        int[] rowPixPos = new int[rowSize + 1];
        rowPixPos[0] = 0;
        int[] colPixPos = new int[colSize + 1];
        colPixPos[0] = 0;
        for (int i = 0; i < rowSize; i++) {

            for (int j = 0; j < colSize; j++) {

                cells[i][j] = new UserCell();
                cells[i][j].setCell(sheet.getRow(i).getCell(j));
                cells[i][j].setRow(i);
                cells[i][j].setCol(j);
                boolean ifShow = (i >= fromIndex[0]) && (j >= fromIndex[1]);    //首先行列要在指定区域之间
                ifShow = ifShow && !(sheet.isColumnHidden(j) || sheet.getRow(i).getZeroHeight()); //其次行列不可以隐藏
                cells[i][j].setShow(ifShow);

                // 计算所求区域宽度
                float widthPix = !ifShow ? 0 : sheet.getColumnWidthInPixels(j); // 如果该单元格是隐藏的，则置宽度为0
                if (i == fromIndex[0]) {
                    imageWidth += widthPix;
                }

                colPixPos[j + 1] = (int) (widthPix * 1.15 + colPixPos[j]);

            }

            // 计算所求区域高度
            boolean ifShow = (i >= fromIndex[0]);    //行序列在指定区域中间
            ifShow = ifShow && !sheet.getRow(i).getZeroHeight();  //行序列不能隐藏
            float heightPoint = !ifShow ? 0 : sheet.getRow(i).getHeightInPoints(); // 如果该单元格是隐藏的，则置高度为0
            imageHeight += heightPoint;
            rowPixPos[i + 1] = (int) (heightPoint * 96 / 72) + rowPixPos[i];

        }

        imageHeight = imageHeight * 96 / 72;
        imageWidth = imageWidth * 115 / 100;

        wb.close();

        List<Grid> grids = new ArrayList<Grid>();
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                System.out.println(i + "," + j);


                Grid grid = new Grid();
                // 设置坐标和宽高
                grid.setX(colPixPos[j]);
                grid.setY(rowPixPos[i]);
                grid.setWidth(colPixPos[j + 1] - colPixPos[j]);
                grid.setHeight(rowPixPos[i + 1] - rowPixPos[i]);
                grid.setRow(cells[i][j].getRow());
                grid.setCol(cells[i][j].getCol());
                grid.setShow(cells[i][j].isShow());

                // 判断是否为合并单元格
                int[] isInMergedStatus = isInMerged(grid.getRow(), grid.getCol(), rangeAddress);

                if (isInMergedStatus[0] == 0 && isInMergedStatus[1] == 0) {
                    // 此单元格是合并单元格，并且不是第一个单元格，需要跳过本次循环，不进行绘制
                    continue;
                } else if (isInMergedStatus[0] != -1 && isInMergedStatus[1] != -1) {
                    // 此单元格是合并单元格，并且属于第一个单元格，则需要调整网格大小
                    int lastRowPos = isInMergedStatus[0] > rowSize - 1 ? rowSize - 1 : isInMergedStatus[0];
                    int lastColPos = isInMergedStatus[1] > colSize - 1 ? colSize - 1 : isInMergedStatus[1];

                    grid.setWidth(colPixPos[lastColPos + 1] - colPixPos[j]);
                    grid.setHeight(rowPixPos[lastRowPos + 1] - rowPixPos[i]);


                }

                // 单元格背景颜色
                CellStyle cs = cells[i][j].getCell().getCellStyle();
                if (cs.getFillPattern() == CellStyle.SOLID_FOREGROUND)
                    grid.setBgColor(cells[i][j].getCell().getCellStyle().getFillForegroundColorColor());

                // 设置字体
                org.apache.poi.ss.usermodel.Font font = wb.getFontAt(cs.getFontIndex());
                grid.setFont(font);

                // 设置字体前景色
                if (font instanceof XSSFFont) {
                    XSSFFont xf = (XSSFFont) font;

                    grid.setFtColor(xf.getXSSFColor());
                }

                // 设置文本
                String strCell = "";
                switch (cells[i][j].getCell().getCellType()) {
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        strCell = String.valueOf(cells[i][j].getCell().getNumericCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_STRING:
                        strCell = cells[i][j].getCell().getStringCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        strCell = String.valueOf(cells[i][j].getCell().getBooleanCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA:

                        try {
                            strCell = String.valueOf(cells[i][j].getCell().getNumericCellValue());
                        } catch (IllegalStateException e) {
                            strCell = String.valueOf(cells[i][j].getCell().getRichStringCellValue());
                        }
                        break;
                    default:
                        strCell = "";
                }

                if (cells[i][j].getCell().getCellStyle().getDataFormatString().contains("0.00%")) {
                    try {
                        double dbCell = Double.valueOf(strCell);
                        strCell = new DecimalFormat("#.00").format(dbCell * 100) + "%";
                    } catch (NumberFormatException e) {
                    }
                }

                grid.setText(strCell.matches("\\w*\\.0") ? strCell.substring(0, strCell.length() - 2) : strCell);

                grids.add(grid);
            }
        }

        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        // 平滑字体
        g2d.setRenderingHint(SunHints.KEY_ANTIALIASING, SunHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(SunHints.KEY_TEXT_ANTIALIASING, SunHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g2d.setRenderingHint(SunHints.KEY_STROKE_CONTROL, SunHints.VALUE_STROKE_DEFAULT);
        g2d.setRenderingHint(SunHints.KEY_TEXT_ANTIALIAS_LCD_CONTRAST, 140);
        g2d.setRenderingHint(SunHints.KEY_FRACTIONALMETRICS, SunHints.VALUE_FRACTIONALMETRICS_OFF);
        g2d.setRenderingHint(SunHints.KEY_RENDERING, SunHints.VALUE_RENDER_DEFAULT);

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, imageWidth, imageHeight);

        // 绘制表格
        for (Grid g : grids) {
            if (!g.isShow())
                continue;

            // 绘制背景色
            g2d.setColor(g.getBgColor() == null ? Color.white : g.getBgColor());
            g2d.fillRect(g.getX(), g.getY(), g.getWidth(), g.getHeight());

//            // 绘制边框
//            g2d.setColor(Color.black);
//            g2d.setStroke(new BasicStroke(1));
//            g2d.drawRect(g.getX(), g.getY(), g.getWidth(), g.getHeight());

            // 绘制文字,居中显示
            g2d.setColor(g.getFtColor());
            Font font = g.getFont();
            FontMetrics fm = g2d.getFontMetrics(font);
            int strWidth = fm.stringWidth(g.getText());// 获取将要绘制的文字宽度
            g2d.setFont(font);
            g2d.drawString(g.getText(),
                    g.getX() + (g.getWidth() - strWidth) / 2,
                    g.getY() + (g.getHeight() - font.getSize()) / 2 + font.getSize());
        }

        g2d.dispose();
        ImageIO.write(image, "png", new File("C:\\Users\\92970\\Desktop\\test.png"));

        System.out.println("Output to PNG file Success!");
    }

    /**
     * 判断Excel中的单元格是否为合并单元格
     *
     * @param row
     * @param col
     * @param rangeAddress
     * @return 如果不是合并单元格返回{-1,-1},如果是合并单元格并且是一个单元格返回{lastRow,lastCol},
     * 如果是合并单元格并且不是第一个格子返回{0,0}
     */
    private static int[] isInMerged(int row, int col, List<CellRangeAddress> rangeAddress) {
        int[] isInMergedStatus = {-1, -1};
        for (CellRangeAddress cra : rangeAddress) {
            if (row == cra.getFirstRow() && col == cra.getFirstColumn()) {
                isInMergedStatus[0] = cra.getLastRow();
                isInMergedStatus[1] = cra.getLastColumn();
                return isInMergedStatus;
            }
            if (row >= cra.getFirstRow() && row <= cra.getLastRow()) {
                if (col >= cra.getFirstColumn() && col <= cra.getLastColumn()) {
                    isInMergedStatus[0] = 0;
                    isInMergedStatus[1] = 0;
                    return isInMergedStatus;
                }
            }
        }
        return isInMergedStatus;
    }
}

class UserCell implements Comparable<UserCell> {
    private Cell cell;
    private int row;
    private int col;
    private boolean show;
    private String text = "";
    private Color color = null;

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public int compareTo(UserCell uc) {
        try {
            double meDouble = Double.parseDouble(this.getText().replaceAll("%", ""));
            double heDouble = Double.parseDouble(uc.getText().replaceAll("%", ""));
            if (meDouble < heDouble)
                return -1;
            else if (meDouble > heDouble)
                return 1;
        } catch (Exception e) {
        }

        return 0;
    }
}


class Grid {
    private boolean show;
    private int row;    //对应Excel中的row,也可以理解为cells[i][j]的i
    private int col;    //对应Excel中的col,也可以理解为cells[i][j]的j
    private int x;  //x坐标
    private int y;  //y坐标
    private int width;
    private int height;
    private String text;
    private java.awt.Font font = new Font("微软雅黑", Font.PLAIN, 12);
    private java.awt.Color bgColor = null;
    private java.awt.Color ftColor = null;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getBgColor() {
        return bgColor;
    }

    /**
     * 将poi.ss.usermodel.Color 转换成  java.awt.Color
     * <a href="http://home.cnblogs.com/u/309701/" target="_blank">@param</a> color
     */
    public void setBgColor(org.apache.poi.ss.usermodel.Color color) {
        this.bgColor = poiColor2awtColor(color);
    }

    public void setBgColor(java.awt.Color color) {
        this.bgColor = color;
    }

    public Color getFtColor() {
        return ftColor;
    }

    public void setFtColor(org.apache.poi.ss.usermodel.Color color) {
        this.ftColor = poiColor2awtColor(color);
    }

    public Font getFont() {
        return font;
    }

    public void setFont(org.apache.poi.ss.usermodel.Font font) {
        if (font != null) {
            this.font = new java.awt.Font(font.getFontName(), Font.BOLD, font.getFontHeight() / 20 + 2);
        }
    }


    private java.awt.Color poiColor2awtColor(org.apache.poi.ss.usermodel.Color color) {
        java.awt.Color awtColor = null;
        if (color instanceof XSSFColor) {     //.xlsx
            XSSFColor xc = (XSSFColor) color;
            String rgbHex = xc.getARGBHex();
            if (rgbHex != null) {
                awtColor = new Color(Integer.parseInt(rgbHex.substring(2), 16));
            }
        } else if (color instanceof HSSFColor) {   //.xls
            HSSFColor hc = (HSSFColor) color;
            short[] s = hc.getTriplet();
            if (s != null) {
                awtColor = new Color(s[0], s[1], s[2]);
            }
        }
        return awtColor;
    }
}
