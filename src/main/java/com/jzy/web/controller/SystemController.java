package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.ModelConstants;
import com.jzy.manager.constant.RedisConstants;
import com.jzy.model.CampusEnum;
import com.jzy.model.dto.CurrentClassSeason;
import com.jzy.model.entity.Class;
import com.jzy.model.entity.User;
import com.jzy.model.vo.Announcement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SystemController
 * @Author JinZhiyun
 * @Description 系统管理的控制器
 * @Date 2019/11/28 23:04
 * @Version 1.0
 **/
@Controller
@RequestMapping("/system")
public class SystemController extends AbstractController {
    private final static Logger logger = LogManager.getLogger(SystemController.class);

    /**
     * 跳转公告推送
     *
     * @return
     */
    @RequestMapping("/announcement")
    public String announcement(Model model) {
        Announcement announcement = (Announcement) hashOps.get(RedisConstants.ANNOUNCEMENT_SYSTEM_KEY, Constants.ZERO.toString());
        model.addAttribute(ModelConstants.ANNOUNCEMENT_EDIT_MODEL_KEY, announcement == null ? new Announcement() : announcement);
        return "system/announcement";
    }

    /**
     * 发布推送
     *
     * @param announcement 推送的信息
     * @return
     */
    @RequestMapping("/pushAnnouncement")
    @ResponseBody
    public Map<String, Object> pushAnnouncement(@RequestParam(value = "clearIfRead", required = false) String clearIfRead, Announcement announcement) {
        Map<String, Object> map = new HashMap<>(1);

        List<User> users = userService.listAllUsers();

        if (!Constants.ON.equals(clearIfRead)) {
            //是否永久有效
            announcement.setPermanent(true);
        }
        announcement.setRead(false);
        announcement.parse();

        //推游客的公告
        hashOps.put(RedisConstants.ANNOUNCEMENT_SYSTEM_KEY, Constants.GUEST_ID.toString(), announcement);
        //推id为0的公告，即缓存公告
        hashOps.put(RedisConstants.ANNOUNCEMENT_SYSTEM_KEY, Constants.ZERO.toString(), announcement);
        for (User user : users) {
            hashOps.put(RedisConstants.ANNOUNCEMENT_SYSTEM_KEY, user.getId().toString(), announcement);
        }

        map.put("data", Constants.SUCCESS);
        return map;
    }

    /**
     * 清除推送
     *
     * @return
     */
    @RequestMapping("/deleteAnnouncement")
    @ResponseBody
    public Map<String, Object> deleteAnnouncement() {
        Map<String, Object> map = new HashMap<>(1);

        List<User> users = userService.listAllUsers();

        for (User user : users) {
            hashOps.delete(RedisConstants.ANNOUNCEMENT_SYSTEM_KEY, user.getId().toString());
        }

        map.put("data", Constants.SUCCESS);
        return map;
    }

    /**
     * 跳转智能校历
     *
     * @return
     */
    @RequestMapping("/intelliClassSeason/page")
    public String intelliClassSeason(Model model) {
        model.addAttribute(ModelConstants.CAMPUS_NAMES_MODEL_KEY, JSON.toJSONString(CampusEnum.getCampusNamesList()));
        model.addAttribute(ModelConstants.SEASONS_MODEL_KEY, JSON.toJSONString(Class.SEASONS));
        model.addAttribute(ModelConstants.SUB_SEASONS_MODEL_KEY, JSON.toJSONString(Class.SUB_SEASONS));

        model.addAttribute(ModelConstants.CURRENT_ClASS_SEASON_MODEL_KEY, classService.getCurrentClassSeason());
        return "system/intelliClassSeason";
    }

    /**
     * 修改智能校历
     *
     * @param classSeason 修改后的校历信息
     * @return
     */
    @RequestMapping("/intelliClassSeason/update")
    @ResponseBody
    public Map<String, Object> updateIntelliClassSeason(CurrentClassSeason classSeason) {
        Map<String, Object> map = new HashMap<>(1);

        classService.updateCurrentClassSeason(classSeason);

        map.put("data", Constants.SUCCESS);
        return map;
    }

    /**
     * 清除智能校历
     *
     * @return
     */
    @RequestMapping("/intelliClassSeason/delete")
    @ResponseBody
    public Map<String, Object> deleteIntelliClassSeason() {
        Map<String, Object> map = new HashMap<>(1);

        classService.deleteCurrentClassSeason();

        map.put("data", Constants.SUCCESS);
        return map;
    }
}
