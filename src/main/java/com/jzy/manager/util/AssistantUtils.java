package com.jzy.manager.util;

import com.jzy.manager.constant.Constants;
import com.jzy.model.CampusEnum;
import com.jzy.model.entity.Assistant;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName AssistantUtils
 * @Author JinZhiyun
 * @Description 助教的工具类 {@link com.jzy.model.entity.Assistant}
 *   对助教的增删改入参对象字段的校验，服务端的校验应该与前端js保持一致，且必须严格于数据库列属性要求的标准
 * @Date 2019/11/20 19:54
 * @Version 1.0
 **/
public class AssistantUtils {
    private AssistantUtils(){}

    public static boolean isValidAssistantWorkId(String assistantWorkId) {
        return assistantWorkId == null || assistantWorkId.length() <= 32;
    }

    public static boolean isValidAssistantName(String assistantName) {
        return !StringUtils.isEmpty(assistantName) && assistantName.length() <= 50;
    }

    public static boolean isValidAssistantSex(String assistantSex) {
        return StringUtils.isEmpty(assistantSex) || Constants.SEX.contains(assistantSex);
    }

    public static boolean isValidAssistantDepart(String assistantDepart) {
        return StringUtils.isEmpty(assistantDepart) || assistantDepart.length()<=20;
    }

    public static boolean isValidAssistantCampus(String assistantCampus) {
        return StringUtils.isEmpty(assistantCampus) || (CampusEnum.hasCampusName(assistantCampus) && assistantCampus.length()<=20);
    }

    public static boolean isValidAssistantPhone(String assistantPhone) {
        return StringUtils.isEmpty(assistantPhone) || MyStringUtils.isPhone(assistantPhone);
    }

    public static boolean isValidAssistantRemark(String assistantRemark) {
        return assistantRemark == null || assistantRemark.length() <= 500;
    }

    /**
     * 输入的assistant是否合法
     *
     * @param assistant 输入的assistant对象
     * @return
     */
    public static boolean isValidAssistantInfo(Assistant assistant){
        return assistant!=null && isValidAssistantWorkId(assistant.getAssistantWorkId()) && isValidAssistantName(assistant.getAssistantName())
                && isValidAssistantSex(assistant.getAssistantSex()) && isValidAssistantDepart(assistant.getAssistantDepart())
                && isValidAssistantCampus(assistant.getAssistantCampus()) && isValidAssistantPhone(assistant.getAssistantPhone())
                && isValidAssistantRemark(assistant.getAssistantRemark());
    }

    /**
     * 输入的assistant是否合法
     *
     * @param assistant 输入的assistant对象
     * @return
     */
    public static boolean isValidAssistantUpdateInfo(Assistant assistant){
        return isValidAssistantInfo(assistant);
    }
}
