package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jzy.manager.constant.ModelConstants;
import com.jzy.model.LogLevelEnum;
import com.jzy.model.dto.ImportantLogDetailedDto;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.search.ImportantLogSearchCondition;
import com.jzy.model.entity.User;
import com.jzy.model.vo.ResultMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ImportantLogController
 * @Author JinZhiyun
 * @Description 持久化到数据库的重要日志的控制器
 * @Date 2020/1/31 9:34
 * @Version 1.0
 **/
@Controller
@RequestMapping("/importantLog/admin")
public class ImportantLogAdminController extends AbstractController {
    private final static Logger logger = LogManager.getLogger(ImportantLogAdminController.class);

    /**
     * 跳转助教管理页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/page")
    public String page(Model model) {
        model.addAttribute(ModelConstants.LEVELS_MODEL_KEY, JSON.toJSONString(LogLevelEnum.getLevelsList()));
        model.addAttribute(ModelConstants.ROLES_MODEL_KEY, JSON.toJSONString(User.ROLES));
        return "importantLog/admin/page";
    }

    /**
     * 查询日志信息的ajax交互
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return 分页结果
     */
    @RequestMapping("/getImportantLogInfo")
    @ResponseBody
    public ResultMap<List<ImportantLogDetailedDto>> getAssistantInfo(MyPage myPage, ImportantLogSearchCondition condition) {
        PageInfo<ImportantLogDetailedDto> pageInfo = importantLogService.listImportantLog(myPage, condition);
        return new ResultMap<>(0, "", (int) pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 删除多条日志记录ajax交互
     *
     * @param allLog 多个日志记录的json串，用fastjson转换为list
     * @return
     */
    @RequestMapping("/deleteMany")
    @ResponseBody
    public Map<String, Object> deleteMany(@RequestParam("allLog") String allLog) {
        Map<String, Object> map = new HashMap(1);

        List<ImportantLogDetailedDto> allLogParsed = JSON.parseArray(allLog, ImportantLogDetailedDto.class);
        List<Long> ids = new ArrayList<>();
        for (ImportantLogDetailedDto log : allLogParsed) {
            ids.add(log.getId());
        }
        importantLogService.deleteManyImportantLogByIds(ids);
        map.put("data", SUCCESS);
        return map;
    }

    /**
     * 条件删除多条日志记录ajax交互
     *
     * @param condition 输入的查询条件
     * @return
     */
    @RequestMapping("/deleteByCondition")
    @ResponseBody
    public Map<String, Object> deleteByCondition(ImportantLogSearchCondition condition) {
        Map<String, Object> map = new HashMap(1);
        importantLogService.deleteImportantLogByCondition(condition);
        map.put("data", SUCCESS);
        return map;
    }
}
