package com.jzy.model.excel.input;

import com.jzy.manager.constant.ExcelConstants;
import com.jzy.manager.exception.ExcelColumnNotFoundException;
import com.jzy.manager.exception.InputFileTypeException;
import com.jzy.manager.util.CodeUtils;
import com.jzy.model.entity.Assistant;
import com.jzy.model.entity.User;
import com.jzy.model.excel.Excel;
import com.jzy.model.excel.ExcelVersionEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @ClassName AssistantInfoExcel
 * @Author JinZhiyun
 * @Description 处理输入助教信息表的模型类
 * @Date 2019/11/20 17:31
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class AssistantInfoExcel extends Excel {
    private static final long serialVersionUID = 7188139549224217001L;

    private static final String DEPART_COLUMN = ExcelConstants.DEPART_COLUMN;

    private static final String CAMPUS_COLUMN = ExcelConstants.CAMPUS_COLUMN;

    private static final String REAL_NAME_COLUMN = ExcelConstants.REAL_NAME_COLUMN;

    private static final String WORK_ID_COLUMN = ExcelConstants.WORK_ID_COLUMN;

    private static final String ID_CARD_COLUMN = ExcelConstants.ID_CARD_COLUMN;

    private static final String PHONE_COLUMN = ExcelConstants.PHONE_COLUMN;

    private static final String REMARK_COLUMN = ExcelConstants.REMARK_COLUMN;

    /**
     * 有效信息开始的行
     */
    private static int startRow = 2;

    /**
     * 读取到的信息封装成user对象
     */
    private List<User> users;

    /**
     * 读取到的信息封装成assistant对象
     */
    private List<Assistant> assistants;

    public AssistantInfoExcel() {
    }

    public AssistantInfoExcel(String inputFile) throws IOException, InputFileTypeException {
        super(inputFile);
    }

    public AssistantInfoExcel(File file) throws IOException, InputFileTypeException {
        super(file);
    }

    public AssistantInfoExcel(InputStream inputStream, ExcelVersionEnum version) throws IOException, InputFileTypeException {
        super(inputStream, version);
    }

    public AssistantInfoExcel(Workbook workbook) {
        super(workbook);
    }

    /**
     * 从助教信息表中读取信息，封装成user对象和assistant对象添加到成员变量列表中
     *
     * @return 返回表格有效数据的行数
     * @throws ExcelColumnNotFoundException
     */
    public int readUsersAndAssistantsFromExcel() throws ExcelColumnNotFoundException {
        resetParam();

        int sheetIx = 0;

        // 先扫描第startRow行找到"工号"、"姓名"、"手机"等信息所在列的位置
        int columnIndexOfDepart = -1, columnIndexOfCampusName = -2, columnIndexOfRealName = -3, columnIndexOfWorkId = -4, columnIndexOfIdCard = -7, columnIndexOfPhone = -9, columnIndexOfRemark = -10;
        int row0ColumnCount = this.getColumnCount(sheetIx, startRow); // 第startRow行的列数
        for (int i = 0; i < row0ColumnCount; i++) {
            String value = this.getValueAt(sheetIx, startRow, i);
            switch (value) {
                case DEPART_COLUMN:
                    columnIndexOfDepart = i;
                    break;
                case CAMPUS_COLUMN:
                    columnIndexOfCampusName = i;
                    break;
                case REAL_NAME_COLUMN:
                    columnIndexOfRealName = i;
                    break;
                case WORK_ID_COLUMN:
                    columnIndexOfWorkId = i;
                    break;
                case ID_CARD_COLUMN:
                    columnIndexOfIdCard = i;
                    break;
                case PHONE_COLUMN:
                    columnIndexOfPhone = i;
                    break;
                case REMARK_COLUMN:
                    columnIndexOfRemark = i;
                    break;
                default:
            }
        }

        if (columnIndexOfCampusName < 0 || columnIndexOfRealName < 0 || columnIndexOfWorkId < 0
                || columnIndexOfIdCard < 0 || columnIndexOfPhone < 0) {
            //这里未校验部门和备注
            //列属性中有未匹配的属性名
            throw new ExcelColumnNotFoundException("助教信息列属性中有未匹配的属性名");
        }

        int effectiveDataRowCount=0;

        int rowCount = this.getRowCount(sheetIx); // 表的总行数
        for (int i = startRow + 1; i < rowCount; i++) {
            User user = new User();
            Assistant assistant = new Assistant();

            if (StringUtils.isEmpty(this.getValueAt(sheetIx, i, columnIndexOfWorkId))) {
                //当前行工号为空，跳过
                continue;
            } else {
                effectiveDataRowCount++;
            }

            String depart = this.getValueAt(sheetIx, i, columnIndexOfDepart);
            assistant.setAssistantDepart(depart);

            String campusName = this.getValueAt(sheetIx, i, columnIndexOfCampusName);
            assistant.setAssistantCampus(campusName);

            String realName = this.getValueAt(sheetIx, i, columnIndexOfRealName);
            user.setUserRealName(realName);
            assistant.setAssistantName(realName);

            String workId = this.getValueAt(sheetIx, i, columnIndexOfWorkId);
            user.setUserWorkId(workId);
            assistant.setAssistantWorkId(workId);

            String idCard = this.getValueAt(sheetIx, i, columnIndexOfIdCard);
            user.setUserIdCard(idCard);

            String phone = this.getValueAt(sheetIx, i, columnIndexOfPhone);
            user.setUserPhone(phone);
            assistant.setAssistantPhone(phone);

            String remark = this.getValueAt(sheetIx, i, columnIndexOfRemark);
            user.setUserRemark(remark);
            assistant.setAssistantRemark(remark);


            //用户名默认身份证
            String userName = StringUtils.isEmpty(idCard) ? "a_" + CodeUtils.randomCodes() + CodeUtils.randomCodes() : "a_" + idCard;
            user.setUserName(userName);
            user.setUserRole("助教");


            users.add(user);
            assistants.add(assistant);
        }

        return effectiveDataRowCount;
    }

    /**
     * 从助教信息表中读取信息，封装成user对象
     *
     * @return 返回表格有效数据的行数
     */
    public int readUsers() throws ExcelColumnNotFoundException {
        return readUsersAndAssistantsFromExcel();
    }

    /**
     * 从助教信息表中读取信息，封装成assistant对象
     *
     * @return 返回表格有效数据的行数
     */
    public int readAssistants() throws ExcelColumnNotFoundException {
        return readUsersAndAssistantsFromExcel();
    }

    @Override
    public void resetParam() {
        users = new ArrayList<>();
        assistants = new ArrayList<>();
    }
}
