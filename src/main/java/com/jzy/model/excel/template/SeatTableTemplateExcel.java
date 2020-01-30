package com.jzy.model.excel.template;

import com.jzy.manager.exception.InvalidFileTypeException;
import com.jzy.model.dto.StudentAndClassDetailedWithSubjectsDto;
import com.jzy.model.excel.AbstractTemplateExcel;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName SeatTemplate
 * @description 座位表模板的模型类
 * @date 2019/10/30 14:21
 **/
public class SeatTableTemplateExcel extends AbstractTemplateExcel{
    private static final long serialVersionUID = -3764653590834120925L;

    public SeatTableTemplateExcel(String inputFile) throws IOException, InvalidFileTypeException {
        super(inputFile);
    }

    /**
     * 删除除输入classroom外的其他教室的sheet
     *
     * @param classroom 教室号
     * @return 写入成功与否
     * @throws IOException 写excel的io异常
     */
    private boolean deleteOtherSheets(String classroom) throws IOException {
        if (StringUtils.isEmpty(classroom)) {
            return false;
        }
        int start = 0;
        int totalSheetCount = getSheetCount();
        for (int i = 0; i < totalSheetCount; i++) {
            if (getSheetName(start).equals(classroom)) {
                start++;
            } else {
                removeSheetAt(start);
            }
        }
        return true;
    }

    /**
     * 根据输入学生名单列表修改对应教室的座位
     *
     * @param data 从数据库中读取到的信息或手动输入的表格中读到的信息，以及用户输入的信息
     * @return 写入成功与否
     * @throws IOException 写excel的io异常
     */
    public boolean writeSeatTable(List<StudentAndClassDetailedWithSubjectsDto> data) throws IOException {
        StudentAndClassDetailedWithSubjectsDto dto = new StudentAndClassDetailedWithSubjectsDto();
        if (data.size() > 0) {
            //取第一个对象为例，获得教室
            dto = data.get(0);
        }
        String classRoom = dto.getClassroom();

        //先把其他没用的教室删掉
        deleteOtherSheets(classRoom);

        //开始依序填座位表
        int targetSheetIndex = 0; //在第0张sheet找
        int rowCount = getRowCount(targetSheetIndex);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < getColumnCount(targetSheetIndex, i); j++) {
                //遍历表格所有行
                String value = getValueAt(targetSheetIndex, i, j);
                if (StringUtils.isNumeric(value)) {
                    //对所有为数字的单元格（即座位号）填充姓名
                    int index = Integer.parseInt(value) - 1;
                    if (index < data.size()) {
                        //座位号值大于学生数量的座位不填
                        setValueAt(targetSheetIndex, i, j, value+" "+data.get(Integer.parseInt(value) - 1).getStudentName());
                    }
                }
            }
        }
        return true;
    }
}
