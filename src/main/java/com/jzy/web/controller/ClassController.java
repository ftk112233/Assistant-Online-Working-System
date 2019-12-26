package com.jzy.web.controller;

import com.jzy.model.entity.Class;
import com.jzy.model.vo.ClassIdSearchResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

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
    private final static Logger logger = LogManager.getLogger(ClassController.class);

    /**
     * 查询校区下的所有教室
     *
     * @param campusName 校区名称
     * @return
     */
    @RequestMapping("/listClassroomsByCampus")
    @ResponseBody
    public List<String> listClassroomsByCampus(@RequestParam(value = "campusName", required = false) String campusName) {
        return campusAndClassroomService.listClassroomsByCampus(campusName);
    }

    /**
     * 根据输入班级编码解析班级校区季度班型等信息，返回解析后的class对象
     *
     * @param classId 班级编码
     * @return
     */
    @RequestMapping("/getParsedClassByParsingClassId")
    @ResponseBody
    public Class getParsedClassByParsingClassId(@RequestParam(value = "classId", required = false) String classId) {
        Class clazz = new Class();
        clazz.setParsedClassId(classId);
        return clazz;
    }

    /**
     * 根据输入班级编码解析班级校区季度班型等信息，返回解析后的class对象
     *
     * @param classId 班级编码
     * @return
     */
    @RequestMapping("/getClassesLikeClassId")
    @ResponseBody
    public Map<String , Object> getClassesLikeClassId(@RequestParam(value = "keywords", required = false) String classId) {
        Map<String , Object> map=new HashMap<>(3);
        map.put("code", 0);
        map.put("msg", "");

        classId = StringUtils.upperCase(classId);
        List<Class> classes=classService.listClassesLikeClassId(classId);
        List<ClassIdSearchResult> results=new ArrayList<>();
        //按常识上的开课时间有近至远排序
        Collections.sort(classes, Class.CLASS_YEAR_SEASON_SUB_SEASON_COMPARATOR_DESC);

        for (Class clazz:classes){
            clazz.setParsedClassYear();
            if (clazz.getClassYear() == null){
                clazz.setClassYear("");
            }
            if (clazz.getClassSeason() == null){
                clazz.setClassSeason("");
            }
            if (clazz.getClassSubSeason() == null){
                clazz.setClassSubSeason("");
            }

            String name=clazz.getClassYear()+clazz.getClassSeason()+clazz.getClassSubSeason()+'_'+clazz.getClassName();
            ClassIdSearchResult result=new ClassIdSearchResult(clazz.getClassId(), name);
            results.add(result);
        }
        map.put("data", results);
        return map;
    }
}
