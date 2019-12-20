package com.jzy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.dao.AssistantMapper;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.util.AssistantUtils;
import com.jzy.model.dto.AssistantSearchCondition;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.UpdateResult;
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

    @Autowired
    private AssistantMapper assistantMapper;

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
        } else {
            return assistantMapper.listAssistantsByCampus(campus);
        }

    }

    @Override
    public UpdateResult insertAssistant(Assistant assistant) {
        if (assistant == null) {
            return new UpdateResult(Constants.FAILURE);
        }
        //新工号不为空
        if (getAssistantByWorkId(assistant.getAssistantWorkId()) != null) {
            //添加的工号已存在
            return new UpdateResult("workIdRepeat");
        }

        return insertAssistantWithUnrepeatedWorkId(assistant);
    }

    /**
     * 插入工号不重复的助教信息
     *
     * @param assistant
     * @return
     */
    private UpdateResult insertAssistantWithUnrepeatedWorkId(Assistant assistant) {
        if (assistant == null) {
            return new UpdateResult(Constants.FAILURE);
        }
        if (getAssistantByName(assistant.getAssistantName()) != null) {
            //添加的姓名已存在
            return new UpdateResult("nameRepeat");
        }

        if (StringUtils.isEmpty(assistant.getAssistantSex())) {
            assistant.setAssistantSex(null);
        }

        UpdateResult result = new UpdateResult(Constants.SUCCESS);
        result.setInsertCount(assistantMapper.insertAssistant(assistant));
        return result;
    }

    @Override
    public String updateAssistantInfo(Assistant assistant) {
        if (assistant == null) {
            return Constants.FAILURE;
        }
        Assistant originalAssistant = getAssistantById(assistant.getId());
        if (originalAssistant == null) {
            return Constants.FAILURE;
        }

        if (!StringUtils.isEmpty(assistant.getAssistantWorkId())) {
            //新工号不为空
            if (!assistant.getAssistantWorkId().equals(originalAssistant.getAssistantWorkId())) {
                //工号修改过了，判断是否与已存在的工号冲突
                if (getAssistantByWorkId(assistant.getAssistantWorkId()) != null) {
                    //修改后的工号已存在
                    return "workIdRepeat";
                }
            }
        } else {
            assistant.setAssistantWorkId(null);
        }

        if (!assistant.getAssistantName().equals(originalAssistant.getAssistantName())) {
            //姓名修改过了，判断是否与已存在的姓名冲突
            if (getAssistantByName(assistant.getAssistantName()) != null) {
                //修改后的姓名已存在
                return "nameRepeat";
            }
        }

        if (StringUtils.isEmpty(assistant.getAssistantSex())) {
            assistant.setAssistantSex(null);
        }

        if (assistant.equalsExceptBaseParams(originalAssistant)) {
            //判断输入对象的对应字段是否未做任何修改
            return Constants.UNCHANGED;
        }

        assistantMapper.updateAssistantInfo(assistant);
        return Constants.SUCCESS;
    }

    @Override
    public UpdateResult updateAssistantByWorkId(Assistant assistant) {
        if (assistant == null) {
            return new UpdateResult(Constants.FAILURE);
        }
        Assistant originalAssistant = getAssistantByWorkId(assistant.getAssistantWorkId());
        return updateAssistantByWorkId(originalAssistant, assistant);
    }

    @Override
    public UpdateResult updateAssistantByWorkId(Assistant originalAssistant, Assistant newAssistant) {
        if (originalAssistant == null || newAssistant == null) {
            return new UpdateResult(Constants.FAILURE);
        }

        if (!newAssistant.getAssistantName().equals(originalAssistant.getAssistantName())) {
            //姓名修改过了，判断是否与已存在的姓名冲突
            if (getAssistantByName(newAssistant.getAssistantName()) != null) {
                //修改后的姓名已存在
                return new UpdateResult("nameRepeat");
            }
        }

        UpdateResult result = new UpdateResult(Constants.SUCCESS);
        result.setUpdateCount(assistantMapper.updateAssistantByWorkId(newAssistant));
        return result;
    }

    @Override
    public UpdateResult insertAndUpdateAssistantsFromExcel(List<Assistant> assistants) throws InvalidParameterException {
        if (assistants == null) {
            String msg = "insertAndUpdateAssistantsFromExcel方法输入助教assistant为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }
        UpdateResult result = new UpdateResult();
        for (Assistant assistant : assistants) {
            if (AssistantUtils.isValidAssistantInfo(assistant)) {
                result.add(insertAndUpdateOneAssistantFromExcel(assistant));
            } else {
                String msg = "表格输入的助教assistant不合法!";
                logger.error(msg);
                throw new InvalidParameterException(msg);
            }
        }
        result.setResult(Constants.SUCCESS);
        return result;
    }

    @Override
    public UpdateResult insertAndUpdateOneAssistantFromExcel(Assistant assistant) throws InvalidParameterException {
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
            Assistant tmp = getAssistantByName(assistant.getAssistantName());
            while (tmp != null) {
                //添加的姓名已存在，在后面加一个'1'
                assistant.setAssistantName(tmp.getAssistantName() + "1");
                tmp = getAssistantByName(assistant.getAssistantName());
            }
            result.add(insertAssistantWithUnrepeatedWorkId(assistant));
        }
        result.setResult(Constants.SUCCESS);
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
    public String deleteAssistantsByCondition(AssistantSearchCondition condition) {
        if (condition == null) {
            return Constants.FAILURE;
        }
        assistantMapper.deleteAssistantsByCondition(condition);
        return Constants.SUCCESS;
    }
}
