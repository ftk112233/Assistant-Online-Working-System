package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jzy.manager.constant.ModelConstants;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.util.FileUtils;
import com.jzy.manager.util.UsefulInformationUtils;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.UsefulInformationSearchCondition;
import com.jzy.model.entity.UsefulInformation;
import com.jzy.model.vo.ResultMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UsefulInformationAdminController
 * @description 常用信息控制器
 * @date 2019/12/6 9:51
 **/
@Controller
@RequestMapping("/usefulInformation/admin")
public class UsefulInformationAdminController extends AbstractController {
    private final static Logger logger = LogManager.getLogger(UsefulInformationAdminController.class);

    /**
     * 查询对应类别的常用信息
     *
     * @param belongTo 信息所属
     * @return
     */
    @RequestMapping("/getByBelongTo")
    @ResponseBody
    public List<UsefulInformation> getByBelongTo(@RequestParam(value = "belongTo", required = false) String belongTo) {
        if (!StringUtils.isEmpty(belongTo) && !UsefulInformationUtils.isValidBelongTo(belongTo)) {
            String msg = "getByBelongTo方法belongTo入参错误";
            logger.error(msg);
            return new ArrayList<>();
        }

        return usefulInformationService.listUsefulInformationWithPublicByBelongTo(belongTo);
    }

    /**
     * 跳转常用信息管理页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/page")
    public String page(Model model) {
        List<String> belongsTo = usefulInformationService.listAllBelongTo();
        model.addAttribute(ModelConstants.USEFUL_INFORMATION_BELONG_TO_MODEL_KEY, JSON.toJSONString(belongsTo));
        return "usefulInformation/admin/page";
    }

    /**
     * 查询常用信息的ajax交互
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    @RequestMapping("/getUsefulInformationInfo")
    @ResponseBody
    public ResultMap<List<UsefulInformation>> getUsefulInformationInfo(MyPage myPage, UsefulInformationSearchCondition condition) {
        PageInfo<UsefulInformation> pageInfo = usefulInformationService.listUsefulInformation(myPage, condition);
        return new ResultMap<>(0, "", (int) pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 通过字节流向前端返回显示配图。
     * 注意对图片的处理，如果没有为空，给默认的配图
     *
     * @param request
     * @param response
     * @param information 含配图信息
     */
    @RequestMapping("/getImage")
    public void showIcon(HttpServletRequest request, HttpServletResponse response, UsefulInformation information) {
        FileInputStream fis = null;
        OutputStream os = null;

        String fileName;
        if (information != null) {
            fileName = information.getImage();
            if (StringUtils.isEmpty(fileName) || !FileUtils.isImage(fileName)) {
                //为空，或不合法的格式，直接给默认配图
                fileName = UsefulInformation.DEFAULT_IMAGE;
            }
        } else {
            fileName = UsefulInformation.DEFAULT_IMAGE;
        }

        try {
            fis = new FileInputStream(filePathProperties.getUsefulInformationImageDirectory() + fileName);
            os = response.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((count = fis.read(buffer)) != -1) {
                os.write(buffer, 0, count);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fis.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重定向到编辑常用信息iframe子页面并返回相应model
     *
     * @param model
     * @param information 当前要被编辑的常用信息
     * @return
     */
    @RequestMapping("/updateForm")
    public String updateForm(Model model, UsefulInformation information) {
        List<String> belongsTo = usefulInformationService.listAllBelongTo();
        model.addAttribute(ModelConstants.USEFUL_INFORMATION_BELONG_TO_MODEL_KEY, JSON.toJSONString(belongsTo));

        model.addAttribute(ModelConstants.USEFUL_INFORMATION_EDIT_MODEL_KEY, information);
        return "usefulInformation/admin/informationEdit";
    }

    /**
     * 根据当前的所属类别获得推荐的序号值。
     *
     * @param belongTo 所属类别
     * @return
     */
    @RequestMapping("/getRecommendedSequence")
    @ResponseBody
    public Map<String, Object> getRecommendedSequence(@RequestParam(value = "belongTo", required = false) String belongTo) {
        Map<String, Object> map = new HashMap<>(1);

        if (!StringUtils.isEmpty(belongTo) && !UsefulInformationUtils.isValidBelongTo(belongTo)) {
            String msg = "getRecommendedSequence方法belongTo入参错误";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        map.put("data", usefulInformationService.getRecommendedSequence(belongTo));

        return map;
    }

    /**
     * 上传配图
     *
     * @param file 配图文件
     * @return
     */
    @RequestMapping("/updateUploadImage")
    @ResponseBody
    public Map<String, Object> updateUploadImage(@RequestParam(value = "file", required = false) MultipartFile file) {
        Map<String, Object> map2 = new HashMap<>(1);
        Map<String, Object> map = new HashMap<>(3);

        String image = usefulInformationService.uploadImage(file);

        //返回layui规定的文件上传模块JSON格式
        map.put("code", 0);
        map.put("msg", "");
        map2.put("src", image);
        map.put("data", map2);
        return map;
    }


    /**
     * 常用信息管理中的编辑常用信息请求，由id修改
     *
     * @param information 修改后的常用信息
     * @return
     */
    @RequestMapping("/updateById")
    @ResponseBody
    public Map<String, Object> updateById(UsefulInformation information) {
        Map<String, Object> map = new HashMap<>(1);

        if (!UsefulInformationUtils.isValidUsefulInformationUpdateInfo(information)) {
            String msg = "updateById方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        map.put("data", usefulInformationService.updateUsefulInformationInfo(information));

        return map;
    }


    /**
     * 常用信息管理中的添加常用信息请求
     *
     * @param information 新添加常用信息
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    public Map<String, Object> insert(UsefulInformation information) {
        Map<String, Object> map = new HashMap<>(1);

        if (!UsefulInformationUtils.isValidUsefulInformationUpdateInfo(information)) {
            String msg = "insert方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        map.put("data", usefulInformationService.insertOneUsefulInformation(information));

        return map;
    }

    /**
     * 删除一个常用信息ajax交互
     *
     * @param id 被删除常用信息的id
     * @return
     */
    @RequestMapping("/deleteOne")
    @ResponseBody
    public Map<String, Object> deleteOne(@RequestParam("id") Long id) {
        Map<String, Object> map = new HashMap(1);

        usefulInformationService.deleteOneUsefulInformationById(id);
        map.put("data", SUCCESS);
        return map;
    }


    /**
     * 删除多个常用信息ajax交互
     *
     * @param allUsefulInformation 多个常用信息的json串，用fastjson转换为list
     * @return
     */
    @RequestMapping("/deleteMany")
    @ResponseBody
    public Map<String, Object> deleteMany(@RequestParam("allUsefulInformation") String allUsefulInformation) {
        Map<String, Object> map = new HashMap(1);

        List<UsefulInformation> allUsefulInformationParsed = JSON.parseArray(allUsefulInformation, UsefulInformation.class);
        List<Long> ids = new ArrayList<>();
        for (UsefulInformation information : allUsefulInformationParsed) {
            ids.add(information.getId());
        }
        usefulInformationService.deleteManyUsefulInformationByIds(ids);
        map.put("data", SUCCESS);
        return map;
    }
}
