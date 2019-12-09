package com.jzy.manager.util;

import com.jzy.model.CampusEnum;
import com.jzy.model.entity.UsefulInformation;
import org.apache.commons.lang3.StringUtils;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UsefulInformationUtils
 * @description 有用信息的工具类 {@link com.jzy.model.entity.UsefulInformation}
 * 删改入参对象字段的校验，服务端的校验应该与前端js保持一致，且必须严格于数据库列属性要求的标准
 * @date 2019/12/5 21:44
 **/
public class UsefulInformationUtils {
    private UsefulInformationUtils() {
    }

    /**
     * 默认的信息所有者，所有校区公有
     */
    private static final String DEFAULT_BELONG_TO = UsefulInformation.DEFAULT_BELONG_TO;

    /**
     * 每个类别能添加的最多信息条数
     */
    private static final long MAX_COUNT = 20;

    /**
     * 每个类别的记录顺序相邻sequence的默认差值
     */
    private static final long SEQUENCE_INTERVAL = UsefulInformation.DEFAULT_SEQUENCE_INTERVAL;

    public static boolean isValidTitle(String title) {
        return !StringUtils.isEmpty(title) && title.length() <= 100;
    }

    public static boolean isValidContent(String content) {
        return !StringUtils.isEmpty(content) && content.length() <= 200;
    }

    public static boolean isValidImage(String image) {
        return StringUtils.isEmpty(image) || (FileUtils.isImage(image) && image.length() <= 100);
    }

    public static boolean isValidBelongTo(String belongTo) {
        return !StringUtils.isEmpty(belongTo) && (DEFAULT_BELONG_TO.equals(belongTo) || CampusEnum.hasCampusName(belongTo))
                && belongTo.length() <= 50;
    }

    public static boolean isValidSequence(Long sequence) {
        return sequence != null;
    }

    public static boolean isValidRemark(String remark) {
        return remark == null || remark.length() <= 500;
    }

    /**
     * UsefulInformation是否合法
     *
     * @param information 输入的UsefulInformation对象
     * @return
     */
    public static boolean isValidUsefulInformationInfo(UsefulInformation information) {
        return information != null && isValidTitle(information.getTitle()) && isValidContent(information.getContent()) && isValidImage(information.getImage())
                && isValidBelongTo(information.getBelongTo()) && isValidSequence(information.getSequence()) && isValidRemark(information.getRemark());
    }

    /**
     * UsefulInformation是否合法
     *
     * @param information 输入的UsefulInformation对象
     * @return
     */
    public static boolean isValidUsefulInformationUpdateInfo(UsefulInformation information) {
        return isValidUsefulInformationInfo(information);
    }
}
