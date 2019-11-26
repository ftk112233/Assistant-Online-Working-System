package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.ModelConstants;
import com.jzy.manager.exception.InvalidParamterException;
import com.jzy.manager.util.ClassUtils;
import com.jzy.manager.util.FileUtils;
import com.jzy.model.CampusEnum;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName ExcelController
 * @Author JinZhiyun
 * @Description 表格处理的控制器
 * @Date 2019/11/21 12:22
 * @Version 1.0
 **/
@Controller
@RequestMapping("/toolbox")
public class ToolboxController extends AbstractController {
    private final static Logger logger = Logger.getLogger(ToolboxController.class);

    /**
     * 跳转助教制作开班多件套页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/assistant/startClassExcel")
    public String startClassExcel(Model model) {
        model.addAttribute(ModelConstants.CAMPUS_NAMES_MODEL_KEY, JSON.toJSONString(CampusEnum.getCampusNamesList()));
        return "toolbox/assistant/startClassExcel";
    }

    /**
     * 跳转助教制作补课单页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/assistant/missLessonStudentExcel")
    public String missLessonStudentExcel(Model model) {
        model.addAttribute(ModelConstants.CAMPUS_NAMES_MODEL_KEY, JSON.toJSONString(CampusEnum.getCampusNamesList()));
        return "toolbox/assistant/missLessonStudentExcel";
    }

    /**
     * 跳转助教制作开班多件套页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/assistantAdministrator/infoImport")
    public String infoImport(Model model) {
        model.addAttribute(ModelConstants.CAMPUS_NAMES_MODEL_KEY, JSON.toJSONString(CampusEnum.getCampusNamesList()));
        model.addAttribute(ModelConstants.SEASONS_MODEL_KEY, JSON.toJSONString(ClassUtils.SEASONS));
        return "toolbox/assistantAdministrator/infoImport";
    }

    /**
     * 学管工具箱中导入信息表格的范例下载
     *
     * @param request
     * @param response
     * @param type     不同的type对应不同的文件 {@link FileUtils}
     * @return
     */
    @RequestMapping("/assistantAdministrator/downloadExample/{type}")
    public String downloadExample(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer type) {
        if (type <= 0) {
            String msg = "downloadExample方法入参错误!";
            logger.error(msg);
            throw new InvalidParamterException(msg);
        }

        try {
            String filePathAndNameToRead = filePathProperties.getToolboxExamplePathAndNameByKey(type);
            String downloadFileName = FileUtils.FILE_NAMES.get(type);
            //下载文件
            FileUtils.downloadFile(request, response, filePathAndNameToRead, downloadFileName);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "downloadExample下载文件失败";
            logger.error(msg);
            return Constants.FAILURE;
        }
        return Constants.SUCCESS;
    }
}
