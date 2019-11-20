package com.jzy.dao;

import com.jzy.model.entity.Assistant;
import org.apache.ibatis.annotations.Param;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName AssistantMapper
 * @description 助教业务dao接口
 * @date 2019/11/13 16:17
 **/
public interface AssistantMapper {
    Assistant getAssistantByWorkId(@Param("assistantWorkId") String assistantWorkId);
}
