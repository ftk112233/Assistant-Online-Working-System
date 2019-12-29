package com.jzy.model.excel;

/**
 * @author JinZhiyun
 * @version 1.0
 * @EnumName ExcelVersionEnum
 * @description Excel版本枚举类
 * @date 2019/10/30 12:55
 **/
public enum ExcelVersionEnum {
    /**
     * Excel 2003 版本
     */
    VERSION_2003(".xls"),

    /**
     * Excel 2007 版本
     */
    VERSION_2007(".xlsx");

    private String suffix;

    ExcelVersionEnum(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    /**
     * 根据输入的文件名返回相应的excel版本枚举对象
     *
     * @param pathname 文件名
     * @return excel版本
     */
    public static ExcelVersionEnum getVersion(String pathname) {
        if (pathname == null) {
            return null;
        }
        if (pathname.endsWith(ExcelVersionEnum.VERSION_2003.getSuffix())) {
            return ExcelVersionEnum.VERSION_2003;
        }
        if (pathname.endsWith(ExcelVersionEnum.VERSION_2007.getSuffix())) {
            return VERSION_2007;
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getVersion("1.xlsx"));
    }
}
