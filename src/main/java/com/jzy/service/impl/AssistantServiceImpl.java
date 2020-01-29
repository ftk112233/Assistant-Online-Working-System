package com.jzy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.dao.AssistantMapper;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.ExcelConstants;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.util.AssistantUtils;
import com.jzy.model.dto.*;
import com.jzy.model.entity.Assistant;
import com.jzy.service.AssistantService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName AssistantServiceImpl
 * @description @Service实现
 * @date 2019/11/14 23:27
 **/
@Service
public class AssistantServiceImpl extends AbstractServiceImpl implements AssistantService {
    private final static Logger logger = LogManager.getLogger(AssistantServiceImpl.class);

    /**
     * 表示工号重复
     */
    private final static String WORK_ID_REPEAT = "workIdRepeat";

    /**
     * 表示姓名重复
     */
    private final static String NAME_REPEAT = "nameRepeat";

    @Autowired
    private AssistantMapper assistantMapper;

    @Override
    public boolean isRepeatedAssistantWorkId(Assistant assistant) {
        if (assistant == null) {
            throw new InvalidParameterException("输入对象不能为空");
        }
        return getAssistantByWorkId(assistant.getAssistantWorkId()) != null;
    }

    @Override
    public boolean isRepeatedAssistantName(Assistant assistant) {
        if (assistant == null) {
            throw new InvalidParameterException("输入对象不能为空");
        }
        return getAssistantByName(assistant.getAssistantName()) != null;
    }

    @Override
    public Assistant getAssistantById(Long id) {
        return id == null ? null : assistantMapper.getAssistantById(id);
    }

    @Override
    public Assistant getAssistantByWorkId(String assistantWorkId) {
        return StringUtils.isEmpty(assistantWorkId) ? null : assistantMapper.getAssistantByWorkId(assistantWorkId);
    }

    @Override
    public Assistant getAssistantByName(String assistantName) {
        return StringUtils.isEmpty(assistantName) ? null : assistantMapper.getAssistantByName(assistantName);
    }

    @Override
    public List<Assistant> listAssistantsByCampus(String campus) {
        if (StringUtils.isEmpty(campus)) {
            return new ArrayList<>();
        }

        return assistantMapper.listAssistantsByCampus(campus);
    }

    @Override
    public List<Assistant> listAssistantsByClassSeasonAndCampus(ClassSeasonDto classSeasonDto, String campus) {
        return assistantMapper.listAssistantsByClassSeasonAndCampus(classSeasonDto, campus);
    }

    @Override
    public UpdateResult insertOneAssistant(Assistant assistant) {
        if (assistant == null) {
            return new UpdateResult(FAILURE);
        }
        //新工号不为空
        if (isRepeatedAssistantWorkId(assistant)) {
            //添加的工号已存在
            return new UpdateResult(WORK_ID_REPEAT);
        }

        return insertAssistantWithUnrepeatedWorkId(assistant);
    }

    /**
     * 插入工号不重复的助教信息
     *
     * @param assistant 新添加助教的信息
     * @return (更新结果 ， 更新记录数)
     * 1."failure"：错误入参等异常
     * 2."nameRepeat"：姓名冲突
     * 3."success": 更新成功
     */
    private UpdateResult insertAssistantWithUnrepeatedWorkId(Assistant assistant) {
        if (assistant == null) {
            return new UpdateResult(FAILURE);
        }
        if (isRepeatedAssistantName(assistant)) {
            //添加的姓名已存在
            return new UpdateResult(NAME_REPEAT);
        }

        if (StringUtils.isEmpty(assistant.getAssistantSex())) {
            assistant.setAssistantSex(null);
        }

        UpdateResult result = new UpdateResult(SUCCESS);
        result.setInsertCount(assistantMapper.insertOneAssistant(assistant));
        return result;
    }

    @Override
    public String updateAssistantInfo(Assistant assistant) {
        if (assistant == null) {
            return FAILURE;
        }
        Assistant originalAssistant = getAssistantById(assistant.getId());
        if (originalAssistant == null) {
            return FAILURE;
        }

        if (!StringUtils.isEmpty(assistant.getAssistantWorkId())) {
            //新工号不为空
            if (isModifiedAndRepeatedAssistantWorkId(originalAssistant, assistant)) {
                return WORK_ID_REPEAT;
            }
        } else {
            assistant.setAssistantWorkId(null);
        }

        if (isModifiedAndRepeatedAssistantName(originalAssistant, assistant)) {
            //姓名修改过了，修改后的姓名已存在
            return NAME_REPEAT;
        }

        if (StringUtils.isEmpty(assistant.getAssistantSex())) {
            assistant.setAssistantSex(null);
        }

        if (assistant.equalsExceptBaseParams(originalAssistant)) {
            //判断输入对象的对应字段是否未做任何修改
            return UNCHANGED;
        }

        assistantMapper.updateAssistantInfo(assistant);
        return SUCCESS;
    }

    /**
     * 判断当前要更新的newAssistant的工号是否修改过且重复。
     * 只有相较于originalAssistant修改过且与数据库中重复才返回false
     *
     * @param originalAssistant 用来比较的原来的助教
     * @param newAssistant      要更新的助教
     * @return 工号是否修改过且重复
     */
    private boolean isModifiedAndRepeatedAssistantWorkId(Assistant originalAssistant, Assistant newAssistant) {
        if (!newAssistant.getAssistantWorkId().equals(originalAssistant.getAssistantWorkId())) {
            //工号修改过了，判断是否与已存在的工号冲突
            if (isRepeatedAssistantWorkId(newAssistant)) {
                //修改后的工号已存在
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前要更新的newAssistant的姓名是否修改过且重复。
     * 只有相较于originalAssistant修改过且与数据库中重复才返回false
     *
     * @param originalAssistant 用来比较的原来的助教
     * @param newAssistant      要更新的助教
     * @return 姓名是否修改过且重复
     */
    private boolean isModifiedAndRepeatedAssistantName(Assistant originalAssistant, Assistant newAssistant) {
        if (!newAssistant.getAssistantName().equals(originalAssistant.getAssistantName())) {
            //姓名修改过了，判断是否与已存在的姓名冲突
            if (isRepeatedAssistantName(newAssistant)) {
                //修改后的姓名已存在
                return true;
            }
        }
        return false;
    }

    @Override
    public UpdateResult updateAssistantByWorkId(Assistant assistant) {
        if (assistant == null) {
            return new UpdateResult(FAILURE);
        }
        Assistant originalAssistant = getAssistantByWorkId(assistant.getAssistantWorkId());
        return updateAssistantByWorkId(originalAssistant, assistant);
    }

    @Override
    public UpdateResult updateAssistantByWorkId(Assistant originalAssistant, Assistant newAssistant) {
        if (originalAssistant == null || newAssistant == null) {
            return new UpdateResult(FAILURE);
        }

        if (isModifiedAndRepeatedAssistantName(originalAssistant, newAssistant)) {
            //姓名修改过了，修改后的姓名已存在
            return new UpdateResult(NAME_REPEAT);
        }

        UpdateResult result = new UpdateResult(SUCCESS);
        result.setUpdateCount(assistantMapper.updateAssistantByWorkId(newAssistant));
        return result;
    }

    @Override
    public DefaultFromExcelUpdateResult insertAndUpdateAssistantsFromExcel(List<Assistant> assistants) {
        if (assistants == null) {
            String msg = "insertAndUpdateAssistantsFromExcel方法输入助教assistant为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }
        DefaultFromExcelUpdateResult result = new DefaultFromExcelUpdateResult(SUCCESS);
        String userRealNameKeyword = ExcelConstants.REAL_NAME_COLUMN;
        InvalidData invalidData = new InvalidData(userRealNameKeyword);
        for (Assistant assistant : assistants) {
            if (AssistantUtils.isValidAssistantInfo(assistant)) {
                result.add(insertAndUpdateOneAssistantFromExcel(assistant));
            } else {
                String msg = "表格输入的助教assistant不合法!";
                logger.error(msg);
                result.setResult(Constants.EXCEL_INVALID_DATA);
                invalidData.putValue(userRealNameKeyword, assistant.getAssistantName());
            }
        }
        result.setInvalidData(invalidData);

        return result;
    }

    /**
     * 根据从excel中读取到的assistant信息，更新插入一个。根据工号判断：
     * if 当前工号不存在
     * 执行插入
     * else
     * 根据工号更新
     * 这里对于非法的入参采取抛出异常的方式，而不是返回"failure"，这是便于控制层捕获做进一步地异常处理
     *
     * @param assistant 输入的助教
     * @return (更新结果 ， 更新记录数)
     * @throws InvalidParameterException 不合法输入助教
     */
    private UpdateResult insertAndUpdateOneAssistantFromExcel(Assistant assistant) throws InvalidParameterException {
        if (assistant == null) {
            String msg = "insertAndUpdateOneAssistantFromExcel方法输入助教assistant为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        UpdateResult result = new UpdateResult();

        Assistant originalAssistant = getAssistantByWorkId(assistant.getAssistantWorkId());
        if (originalAssistant != null) {
            //工号已存在，更新
            if (!originalAssistant.equalsExceptBaseParams(assistant)) {
                //相比原来记录有字段更新
                result.add(updateAssistantByWorkId(originalAssistant, assistant));
            }
        } else {
            //插入
            /*
                不再做重名检查，认为重名情况很少出现,出现了的后果仅仅是，查自己班级的时候会把那个重名的人的班一起查出来。
                这个代价是可以接受的，因此采用“鸵鸟算法”。——2020/1/11
             */
//            Assistant tmp = getAssistantByName(assistant.getAssistantName());
//            while (tmp != null) {
//                //添加的姓名已存在，在后面加一个'1'
//                assistant.setAssistantName(tmp.getAssistantName() + "1");
//                tmp = getAssistantByName(assistant.getAssistantName());
//            }
            result.add(insertAssistantWithUnrepeatedWorkId(assistant));
        }
        result.setResult(SUCCESS);
        return result;
    }

    @Override
    public PageInfo<Assistant> listAssistants(MyPage myPage, AssistantSearchCondition condition) {
        PageHelper.startPage(myPage.getPageNum(), myPage.getPageSize());
        List<Assistant> assistants = assistantMapper.listAssistants(condition);
        return new PageInfo<>(assistants);
    }

    @Override
    public long deleteOneAssistantById(Long id) {
        if (id == null) {
            return 0;
        }
        return assistantMapper.deleteOneAssistantById(id);
    }

    @Override
    public long deleteManyAssistantsByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return 0;
        }
        return assistantMapper.deleteManyAssistantsByIds(ids);
    }

    @Override
    public long deleteAssistantsByCondition(AssistantSearchCondition condition) {
        if (condition == null) {
            return 0;
        }
        return assistantMapper.deleteAssistantsByCondition(condition);
    }
}
