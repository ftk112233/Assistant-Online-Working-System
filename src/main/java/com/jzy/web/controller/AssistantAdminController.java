package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.ModelConstants;
import com.jzy.manager.util.AssistantUtils;
import com.jzy.model.CampusEnum;
import com.jzy.model.dto.AssistantSearchCondition;
import com.jzy.model.dto.MyPage;
import com.jzy.model.entity.Assistant;
import com.jzy.model.excel.Excel;
import com.jzy.model.excel.ExcelVersionEnum;
import com.jzy.model.excel.input.AssistantInfoExcel;
import com.jzy.model.vo.ResultMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AssistantController
 * @Author JinZhiyun
 * @Description 助教管理业务控制器
 * @Date 2019/11/21 22:19
 * @Version 1.0
 **/
@Controller
@RequestMapping("/assistant/admin")
public class AssistantAdminController extends AbstractController {
    private final static Logger logger = Logger.getLogger(AssistantAdminController.class);

    /**
     * 跳转助教管理页面
     *
     * @return
     */
    @RequestMapping("/page")
    public String console(Model model) {
        model.addAttribute(ModelConstants.CAMPUS_NAMES_MODEL_KEY, JSON.toJSONString(CampusEnum.getCampusNamesList()));
        return "assistant/admin/page";
    }

    /**
     * 查询角色权限信息的ajax交互
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    @RequestMapping("/getAssistantInfo")
    @ResponseBody
    public ResultMap<List<Assistant>> getAssistantInfo(MyPage myPage, AssistantSearchCondition condition) {
        PageInfo<Assistant> pageInfo = assistantService.listAssistants(myPage, condition);
        return new ResultMap<>(0, "", (int) pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 重定向到编辑助教权限iframe子页面并返回相应model
     *
     * @param model
     * @param assistant 当前要被编辑的助教信息
     * @return
     */
    @RequestMapping("/updateForm")
    public String updateForm(Model model, Assistant assistant) {
        model.addAttribute(ModelConstants.CAMPUS_NAMES_MODEL_KEY, JSON.toJSONString(CampusEnum.getCampusNamesList()));
        model.addAttribute(ModelConstants.ASSISTANT_EDIT_MODEL_KEY, assistant);
        return "assistant/admin/assistantForm";
    }

    /**
     * 助教管理中的编辑助教请求，由id修改
     *
     * @param assistant 修改后的助教信息
     * @return
     */
    @RequestMapping("/updateById")
    @ResponseBody
    public Map<String, Object> updateById(Assistant assistant) {
        Map<String, Object> map = new HashMap<>(1);

        if (!AssistantUtils.isValidAssistantUpdateInfo(assistant)) {
            String msg = "updateById方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        map.put("data", assistantService.updateAssistantInfo(assistant));

        return map;
    }

    /**
     * 助教管理中的添加助教请求
     *
     * @param assistant 新添加助教的信息
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    public Map<String, Object> insert(Assistant assistant) {
        Map<String, Object> map = new HashMap<>(1);

        if (!AssistantUtils.isValidAssistantUpdateInfo(assistant)) {
            String msg = "updateById方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }
        map.put("data", assistantService.insertAssistant(assistant));

        return map;
    }

    /**
     * 删除一个助教ajax交互
     *
     * @param id 被删除助教的id
     * @return
     */
    @RequestMapping("/deleteOne")
    @ResponseBody
    public Map<String, Object> deleteOne(@RequestParam("id") Long id) {
        Map<String, Object> map = new HashMap(1);

        assistantService.deleteOneAssistantById(id);
        map.put("data", Constants.SUCCESS);
        return map;
    }

    /**
     * 删除多个助教ajax交互
     *
     * @param assistants 多个角色权限的json串，用fastjson转换为list
     * @return
     */
    @RequestMapping("/deleteMany")
    @ResponseBody
    public Map<String, Object> deleteMany(@RequestParam("assistants") String assistants) {
        Map<String, Object> map = new HashMap(1);

        List<Assistant> assistantsParsed = JSON.parseArray(assistants, Assistant.class);
        List<Long> ids = new ArrayList<>();
        for (Assistant assistant : assistantsParsed) {
            ids.add(assistant.getId());
        }
        assistantService.deleteManyAssistantsByIds(ids);
        map.put("data", Constants.SUCCESS);
        return map;
    }

    /**
     * 导入助教
     *
     * @param file 上传的表格
     * @return
     */
    @RequestMapping("/import")
    @ResponseBody
    public Map<String, Object> importExcel(@RequestParam(value = "file", required = false) MultipartFile file) {
        Map<String, Object> map2 = new HashMap<>(1);
        Map<String, Object> map = new HashMap<>(3);
        //返回layui规定的文件上传模块JSON格式
        map.put("code", 0);
        map2.put("src", "");
        map.put("data", map2);

        if (file.isEmpty()) {
            String msg = "上传文件为空";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }


        if (!Excel.isExcel(file.getOriginalFilename())) {
            String msg = "上传文件不是excel";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        AssistantInfoExcel excel = null;
        try {
            excel = new AssistantInfoExcel(file.getInputStream(), ExcelVersionEnum.getVersionByName(file.getOriginalFilename()));
            assistantService.insertAndUpdateAssistantsFromExcel(excel.readAssistants());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", Constants.FAILURE);
            return map;
        }

        map.put("msg", Constants.SUCCESS);
        return map;
    }
}
