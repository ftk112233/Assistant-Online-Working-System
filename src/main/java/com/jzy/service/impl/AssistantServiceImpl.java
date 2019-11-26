package com.jzy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.dao.AssistantMapper;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.util.AssistantUtils;
import com.jzy.model.dto.AssistantSearchCondition;
import com.jzy.model.dto.MyPage;
import com.jzy.model.entity.Assistant;
import com.jzy.service.AssistantService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
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
    private final static Logger logger = Logger.getLogger(AbstractServiceImpl.class);

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
    public String insertAssistant(Assistant assistant) {
        //新工号不为空
        if (getAssistantByWorkId(assistant.getAssistantWorkId()) != null) {
            //添加的工号已存在
            return "workIdRepeat";
        }

        if (getAssistantByName(assistant.getAssistantName()) != null) {
            //添加的姓名已存在
            return "nameRepeat";
        }

        if (StringUtils.isEmpty(assistant.getAssistantSex())) {
            assistant.setAssistantSex(null);
        }
        assistantMapper.insertAssistant(assistant);
        return Constants.SUCCESS;
    }

    @Override
    public String updateAssistantInfo(Assistant assistant) {
        Assistant originalAssistant = getAssistantById(assistant.getId());

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

        assistantMapper.updateAssistantInfo(assistant);
        return Constants.SUCCESS;
    }

    @Override
    public String updateAssistantByWorkId(Assistant assistant) {
        Assistant originalAssistant = getAssistantByWorkId(assistant.getAssistantWorkId());
        return updateAssistantByWorkId(originalAssistant,assistant);
    }

    @Override
    public String updateAssistantByWorkId(Assistant originalAssistant, Assistant newAssistant) {
        if (!newAssistant.getAssistantName().equals(originalAssistant.getAssistantName())) {
            //姓名修改过了，判断是否与已存在的姓名冲突
            if (getAssistantByName(newAssistant.getAssistantName()) != null) {
                //修改后的姓名已存在
                return "nameRepeat";
            }
        }

        assistantMapper.updateAssistantByWorkId(newAssistant);
        return Constants.SUCCESS;
    }

    @Override
    public String insertAndUpdateAssistantsFromExcel(List<Assistant> assistants) throws Exception {
        for (Assistant assistant : assistants) {
            if (AssistantUtils.isValidAssistantInfo(assistant)) {
                insertAndUpdateOneAssistantFromExcel(assistant);
            } else {
                String msg = "表格输入的助教assistant不合法!";
                logger.error(msg);
                throw new InvalidParameterException(msg);
            }
        }
        return Constants.SUCCESS;
    }

    @Override
    public String insertAndUpdateOneAssistantFromExcel(Assistant assistant) throws Exception {
        if (assistant == null) {
            String msg = "insertAndUpdateOneAssistantFromExcel方法输入助教assistant为null!";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }


        Assistant originalAssistant=getAssistantByWorkId(assistant.getAssistantWorkId());
        if (originalAssistant != null) {
            //工号已存在，更新
            updateAssistantByWorkId(originalAssistant,assistant);
        } else {
            //插入
            Assistant tmp = getAssistantByName(assistant.getAssistantName());
            if (tmp != null) {
                //添加的姓名已存在，在后面加一个'1'
                assistant.setAssistantName(tmp.getAssistantName() + "1");
            }

            insertAssistant(assistant);
        }
        return Constants.SUCCESS;
    }

    @Override
    public PageInfo<Assistant> listAssistants(MyPage myPage, AssistantSearchCondition condition) {
        PageHelper.startPage(myPage.getPageNum(), myPage.getPageSize());
        List<Assistant> assistants = assistantMapper.listAssistants(condition);
        return new PageInfo<>(assistants);
    }

    @Override
    public void deleteOneAssistantById(Long id) {
        assistantMapper.deleteOneAssistantById(id);
    }

    @Override
    public void deleteManyAssistantsByIds(List<Long> ids) {
        for (Long id : ids) {
            deleteOneAssistantById(id);
        }
    }
}
