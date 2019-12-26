package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.ModelConstants;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.util.AssistantUtils;
import com.jzy.model.CampusEnum;
import com.jzy.model.dto.AssistantSearchCondition;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.UpdateResult;
import com.jzy.model.entity.Assistant;
import com.jzy.model.excel.Excel;
import com.jzy.model.excel.ExcelVersionEnum;
import com.jzy.model.excel.input.AssistantInfoExcel;
import com.jzy.model.vo.ResultMap;
import com.jzy.model.vo.Speed;
import com.jzy.model.vo.SqlProceedSpeed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    private final static Logger logger = LogManager.getLogger(AssistantAdminController.class);

    /**
     * 跳转助教管理页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/page")
    public String page(Model model) {
        model.addAttribute(ModelConstants.CAMPUS_NAMES_MODEL_KEY, JSON.toJSONString(CampusEnum.getCampusNamesList()));
        return "assistant/admin/page";
    }

    /**
     * 查询助教信息的ajax交互
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
     * 重定向到编辑助教iframe子页面并返回相应model。
     *  其中被编辑的助教信息中select的元素不能通过layui iframe直接赋值，因此经由后台model传值
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
            String msg = "insert方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }
        map.put("data", assistantService.insertAssistant(assistant).getResult());

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
     * @param assistants 多个助教的json串，用fastjson转换为list
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
     * 条件删除多个助教ajax交互
     *
     * @param condition 输入的查询条件 {@link AssistantSearchCondition}
     * @return
     */
    @RequestMapping("/deleteByCondition")
    @ResponseBody
    public Map<String, Object> deleteByCondition(AssistantSearchCondition condition) {
        Map<String, Object> map = new HashMap(1);
        assistantService.deleteAssistantsByCondition(condition);
        map.put("data", Constants.SUCCESS);
        return map;
    }


    /**
     * 表格导入助教
     *
     * @param file 上传的表格
     * @return [更新结果, [更新条数，速度]]
     */
    @RequestMapping("/import")
    @ResponseBody
    public Map<String, Object> importExcel(@RequestParam(value = "file", required = false) MultipartFile file) {
        Map<String, Object> map2 = new HashMap<>(1);
        Map<String, Object> map = new HashMap<>();
        //返回layui规定的文件上传模块JSON格式
        map.put("code", 0);
        map2.put("src", "");
        map.put("data", map2);

        if (file == null || file.isEmpty()) {
            String msg = "上传文件为空";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }


        if (!Excel.isExcel(file.getOriginalFilename())) {
            String msg = "上传文件不是excel";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        long startTime = System.currentTimeMillis();   //获取开始时间
        int excelEffectiveDataRowCount = 0; //表格有效数据行数
        int databaseUpdateRowCount = 0; //数据库更新记录数
        int databaseInsertRowCount = 0; //数据库插入记录数
        int databaseDeleteRowCount = 0; //数据库删除记录数

        AssistantInfoExcel excel = null;
        try {
            excel = new AssistantInfoExcel(file.getInputStream(), ExcelVersionEnum.getVersion(file.getOriginalFilename()));
            excelEffectiveDataRowCount = excel.readAssistants();
            UpdateResult assistantResult = assistantService.insertAndUpdateAssistantsFromExcel(excel.getAssistants());
            databaseInsertRowCount += (int) assistantResult.getInsertCount();
            databaseUpdateRowCount += (int) assistantResult.getUpdateCount();
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", Constants.FAILURE);
            return map;
        }

        long endTime = System.currentTimeMillis(); //获取结束时间
        Speed speedOfExcelImport = new Speed(excelEffectiveDataRowCount, endTime - startTime);
        SqlProceedSpeed speedOfDatabaseImport = new SqlProceedSpeed(databaseUpdateRowCount, databaseInsertRowCount, databaseDeleteRowCount, endTime - startTime);
        speedOfExcelImport.parseSpeed();
        speedOfDatabaseImport.parseSpeed();


        map.put("excelSpeed", speedOfExcelImport);
        map.put("databaseSpeed", speedOfDatabaseImport);

        map.put("msg", Constants.SUCCESS);
        return map;
    }
}
