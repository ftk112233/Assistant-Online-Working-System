package com.jzy.web.controller;

import com.jzy.manager.constant.Constants;
import com.jzy.manager.util.MyStringUtils;
import com.jzy.manager.util.SendEmailUtils;
import com.jzy.model.vo.ProblemCollection;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName HomeController
 * @description 主页的一些页面的跳转
 * @date 2019/11/19 12:57
 **/
@Controller
public class HomeController extends AbstractController{
    private final static Logger logger = Logger.getLogger(HomeController.class);

    /**
     * 跳转控制台
     *
     * @return
     */
    @RequestMapping("/console")
    public String console() {
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
     * 问题收集的提交问题
     *
     * @param problemCollection 前台表单参数封装
     * @return
     */
    @RequestMapping("/sendProblem")
    @ResponseBody
    public Map<String, Object> sendProblem(ProblemCollection problemCollection) {
        Map<String, Object> map = new HashMap<>(1);

        if (Constants.ON.equals(problemCollection.getHide())){
            problemCollection.setRealName("匿名");
        }


        if (!StringUtils.isEmpty(problemCollection.getContent())){
            String msgToSend=problemCollection.getContent()+"\n\n姓名: "+problemCollection.getRealName()+"\n邮箱: "+problemCollection.getEmail();

            SendEmailUtils.sendEncryptedEmail(SendEmailUtils.FROM, problemCollection.getTitle(),msgToSend);

            //回访邮件
            if (MyStringUtils.isEmail(problemCollection.getEmail())){
                String msgToSendBack="感谢您的问题反馈，以及长期以来对AWOS-优能助教在线工作平台的支持!酷乐会尽快处理您的问题的~";
                SendEmailUtils.sendEncryptedEmail(problemCollection.getEmail(), "AOWS-回访",msgToSendBack);
            }
        } else {
            String msg = "sendProblem方法错误入参";
            logger.error(msg);
            map.put("data", Constants.FAILURE);
            return map;
        }

        map.put("data", Constants.SUCCESS);
        return map;
    }
}
