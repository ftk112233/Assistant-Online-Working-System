package com.jzy.web.controller;

import com.jzy.model.CampusEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    public List<String> getClassroomsByCampus(@RequestParam("campusName") String campusName) {
        return CampusEnum.getClassroomsByCampusName(campusName);
    }
}
