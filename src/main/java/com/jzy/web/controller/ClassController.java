package com.jzy.web.controller;

import com.jzy.manager.util.AssistantUtils;
import com.jzy.manager.util.ClassUtils;
import com.jzy.model.CampusEnum;
import com.jzy.model.dto.ClassDetailedDto;
import com.jzy.model.entity.Assistant;
import com.jzy.model.entity.Class;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ClassController
 * @Author JinZhiyun
 * @Description 班级业务的控制器
 * @Date 2019/11/22 12:23
 * @Version 1.0
 **/
@Controller
@RequestMapping("/class")
public class ClassController extends AbstractController {
    /**
     * 查询校区下的所有教室
     *
     * @param campusName 校区名称
     * @return
     */
    @RequestMapping("/getClassroomsByCampus")
    @ResponseBody
    public List<String> getClassroomsByCampus(@RequestParam(value = "campusName",required = false) String campusName) {
        return CampusEnum.getClassroomsByCampusName(campusName);
    }

    /**
     * 根据输入班级编码解析班级校区季度班型等信息，返回解析后的class对象
     *
     * @param classId 班级编码
     * @return
     */
    @RequestMapping("/getParsedClassByParsingClassId")
    @ResponseBody
    public Class getParsedClassByParsingClassId(@RequestParam(value = "classId",required = false) String classId) {
        Class clazz=new Class();
        clazz.setParsedClassId(classId);
        return clazz;
    }
}
