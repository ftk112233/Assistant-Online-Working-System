package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.ModelConstants;
import com.jzy.manager.constant.RedisConstants;
import com.jzy.manager.util.MyStringUtils;
import com.jzy.manager.util.MyTimeUtils;
import com.jzy.manager.util.SendEmailUtils;
import com.jzy.model.vo.ProblemCollection;
import com.jzy.model.vo.echarts.EchartsFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName HomeController
 * @description 主页的一些页面的跳转
 * @date 2019/11/19 12:57
 **/
@Controller
public class HomeController extends AbstractController {
    private final static Logger logger = LogManager.getLogger(HomeController.class);

    /**
     * 跳转控制台。在模型中添加常用信息的所有归属，以便前端的渲染。
     *
     * @param model
     * @return
     */
    @RequestMapping("/console")
    public String console(Model model) {
        List<String> belongsTo = usefulInformationService.listAllBelongTo();
        model.addAttribute(ModelConstants.USEFUL_INFORMATION_BELONG_TO_MODEL_KEY, JSON.toJSONString(belongsTo));
        return "home/console";
    }

    /**
     * 跳转问题收集页面
     *
     * @return
     */
    @RequestMapping("/problemCollection")
    public String problemCollection() {
        return "home/problemCollection";
    }


    /**
     * 问题收集的提交问题。
     * 1. 是否匿名发送？
     * 2. 向开发者的邮箱发送提交问题
     * 3. 如果用户绑定了邮箱，向用户邮箱发送回访邮件
     *
     * @param problemCollection 前台表单参数封装 {@link ProblemCollection}
     * @return
     */
    @RequestMapping("/sendProblem")
    @ResponseBody
    public Map<String, Object> sendProblem(ProblemCollection problemCollection) {
        Map<String, Object> map = new HashMap<>(1);

        if (Constants.ON.equals(problemCollection.getHide())) {
            problemCollection.setRealName("匿名");
        }


        if (!StringUtils.isEmpty(problemCollection.getContent())) {
            String msgToSend = problemCollection.getContent() + "\n\n姓名: " + problemCollection.getRealName() + "\n邮箱: " + problemCollection.getEmail();

            SendEmailUtils.sendConcurrentEncryptedEmail(SendEmailUtils.FROM, problemCollection.getTitle(), msgToSend);

            //回访邮件
            if (MyStringUtils.isEmail(problemCollection.getEmail())) {
                String msgToSendBack = "感谢您的问题反馈，以及长期以来对AWOS-优能助教在线工作平台的支持!酷乐会尽快处理您的问题的~";
                SendEmailUtils.sendConcurrentEncryptedEmail(problemCollection.getEmail(), "AOWS-回访", msgToSendBack);
            }
        } else {
            String msg = "sendProblem方法错误入参";
            logger.error(msg);
            map.put("data", FAILURE);
            return map;
        }

        map.put("data", SUCCESS);
        return map;
    }

    /**
     * 跳转访客量统计页面
     *
     * @return
     */
    @RequestMapping("/visitorStatistics")
    public String visitorStatistics() {
        return "home/visitorStatistics";
    }

    /**
     * 近三十天的访客量统计，返回echarts图标所需json
     *
     * @return
     */
    @RequestMapping("/getRecentVisitorStatistics")
    @ResponseBody
    public Map<String, Object> getRecentVisitorStatistics() {
        Map<String, Object> map = new HashMap<>();
        List<String> xAxisData2 = new ArrayList<>(24);
        for (int hour = 0; hour <= 23; hour++) {
            xAxisData2.add(hour + "点");
        }
        long[] seriesData2 = new long[24];

        List<String> legendData = Arrays.asList("主页访客", "信息管理区访客", "百宝箱区访客");
        int daysSize = 30;
        List<String> xAxisData = new ArrayList<>(daysSize);
        List<Date> days = MyTimeUtils.getPastDays(new Date(), daysSize - 1);
        for (Date day : days) {
            xAxisData.add(MyTimeUtils.dateToStringYMD(day));
        }

        List<Object> series = new ArrayList<>(legendData.size());
        for (String name : legendData) {
            //各类别访客数列表
            List<Long> seriesData = new ArrayList<>(daysSize);
            for (Date day : days) {
                Long visitTimesOfDay = Constants.ZERO;
                if ("主页访客".equals(name)) {
                    String key = RedisConstants.getIndexVisitorStatisticsKey(day);
                    for (int hour = 0; hour <= 23; hour++) {
                        Long visitTimesOfHour = Constants.ZERO;
                        String hourKey = hour + "";
                        if (hashOps.hasKey(key, hourKey)) {
                            visitTimesOfHour = ((Integer) hashOps.get(key, hourKey)).longValue();
                        }
                        visitTimesOfDay += visitTimesOfHour;

                        seriesData2[hour] += visitTimesOfHour;
                    }
                } else {
                    String key;
                    if ("信息管理区访客".equals(name)) {
                        key = RedisConstants.getInfoManagementVisitorStatisticsKey(day);
                    } else {
                        //百宝箱区访客
                        key = RedisConstants.getToolboxVisitorStatisticsKey(day);
                    }
                    if (redisTemplate.hasKey(key)) {
                        visitTimesOfDay = ((Integer) valueOps.get(key)).longValue();
                    }
                }
                seriesData.add(visitTimesOfDay);
            }
            series.add(EchartsFactory.getLineStackSeries(name, true, seriesData));
        }

        map.put("legendData", legendData);
        map.put("xAxisData2", xAxisData2);
        map.put("xAxisData", xAxisData);
        map.put("series", series);
        map.put("seriesData2", seriesData2);
        return map;
    }
}
