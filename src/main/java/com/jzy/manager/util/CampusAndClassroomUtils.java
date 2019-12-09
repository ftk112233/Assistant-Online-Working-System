package com.jzy.manager.util;

import com.jzy.model.CampusEnum;
import com.jzy.model.entity.CampusAndClassroom;
import org.apache.commons.lang3.StringUtils;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName CampusAndClassroomUtils
 * @description 助教的工具类 {@link com.jzy.model.entity.CampusAndClassroom}
 * 对校区-教室的增删改入参对象字段的校验，服务端的校验应该与前端js保持一致，且必须严格于数据库列属性要求的标准
 * @date 2019/12/5 18:26
 **/
public class CampusAndClassroomUtils {
    private CampusAndClassroomUtils(){}

    public static boolean isValidCampus(String campus) {
        return !StringUtils.isEmpty(campus) && CampusEnum.hasCampusName(campus) && campus.length() <= 20;
    }

    public static boolean isValidClassroom(String classroom) {
        return !StringUtils.isEmpty(classroom) && classroom.length() <= 10;
    }

    public static boolean isValidClassroomCapacity(Integer capacity) {
        return true;
    }

    public static boolean isValidRemark(String remark) {
        return remark == null || remark.length() <= 500;
    }

    /**
     * 输入的CampusAndClassroom是否合法
     *
     * @param campusAndClassroom 输入的CampusAndClassroom对象
     * @return
     */
    public static boolean isValidCampusAndClassroomInfo(CampusAndClassroom campusAndClassroom) {
        return campusAndClassroom != null && isValidCampus(campusAndClassroom.getCampus()) && isValidClassroom(campusAndClassroom.getClassroom())
               && isValidClassroomCapacity(campusAndClassroom.getClassroomCapacity()) && isValidRemark(campusAndClassroom.getRemark());
    }

    /**
     * 输入的CampusAndClassroom是否合法
     *
     * @param campusAndClassroom 输入的CampusAndClassroom对象
     * @return
     */
    public static boolean isValidCampusAndClassroomUpdateInfo(CampusAndClassroom campusAndClassroom) {
        return isValidCampusAndClassroomInfo(campusAndClassroom);
    }
}
