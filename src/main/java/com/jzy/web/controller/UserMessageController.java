package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.ModelConstants;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.util.FileUtils;
import com.jzy.manager.util.UserMessageUtils;
import com.jzy.model.CampusEnum;
import com.jzy.model.dto.*;
import com.jzy.model.entity.User;
import com.jzy.model.entity.UserMessage;
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
import java.util.*;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UserMessageController
 * @description 用户消息的控制器
 * @date 2019/12/7 9:48
 **/
@Controller
@RequestMapping("/user/message")
public class UserMessageController extends AbstractController {
    private final static Logger logger = LogManager.getLogger(UserMessageController.class);

    /**
     * 跳转用户消息中心。需要计算未读消息的数量，传给model
     *
     * @return
     */
    @RequestMapping("/page")
    public String page(Model model) {
        Long userId = userService.getSessionUserInfo().getId();
        long countUnread = userMessageService.countUserMessagesByUserIdAndRead(userId, false);
        model.addAttribute(ModelConstants.UNREAD_USER_MESSAGE_COUNT_MODEL_KEY, countUnread);
        return "user/message/page";
    }

    /**
     * 跳转用户消息详情
     *
     * @return
     */
    @RequestMapping("/detail")
    public String detail(Model model, @RequestParam("id") Long id) {
        UserMessageDto userMessageDto = userMessageService.getUserMessageDtoById(id);
        if (!userMessageDto.isRead()) {
            //点了消息，将未读状态更新为已读
            userMessageService.updateUserMessageReadById(id);
        }

        if (StringUtils.isEmpty(userMessageDto.getUserFrom())) {
            //匿名信息
            userMessageDto.setUserFrom("匿名用户");
        }
        model.addAttribute(ModelConstants.USER_MESSAGE_DTO_MODEL_KEY, userMessageDto);
        return "user/message/detail";
    }


    /**
     * 查询用户消息的ajax交互
     *
     * @param myPage 分页{页号，每页数量}
     * @return
     */
    @RequestMapping("/getAllMessageInfo")
    @ResponseBody
    public ResultMap<List<UserMessageDto>> getAllMessageInfo(MyPage myPage) {
        Long userId = userService.getSessionUserInfo().getId();
        UserMessageSearchCondition condition = new UserMessageSearchCondition();
        condition.setUserId(userId);
        PageInfo<UserMessageDto> pageInfo = userMessageService.listUserMessages(myPage, condition);
        return new ResultMap<>(0, "", (int) pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 查询用户未读消息的ajax交互
     *
     * @param myPage 分页{页号，每页数量}
     * @return
     */
    @RequestMapping("/getMessageUnreadInfo")
    @ResponseBody
    public ResultMap<List<UserMessageDto>> getMessageUnreadInfo(MyPage myPage) {
        Long userId = userService.getSessionUserInfo().getId();
        PageInfo<UserMessageDto> pageInfo = userMessageService.listUserMessagesByUserIdAndRead(myPage, userId, false);
        return new ResultMap<>(0, "", (int) pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 通过字节流向前端返回显示消息配图
     *
     * @param request
     * @param response
     * @param message  含消息配图信息
     */
    @RequestMapping("/getPicture")
    public void getPicture(HttpServletRequest request, HttpServletResponse response, UserMessage message) {
        if (message == null || StringUtils.isEmpty(message.getMessagePicture()) || !FileUtils.isImage(message.getMessagePicture())) {
            String msg = "getPicture方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        FileInputStream fis = null;
        OutputStream os = null;

        String fileName = message.getMessagePicture();
        try {
            fis = new FileInputStream(filePathProperties.getUserMessagePictureDirectory() + fileName);
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
     * 对多个消息标记已读的ajax交互
     *
     * @param messages 多个消息的json串，用fastjson转换为list
     * @return
     */
    @RequestMapping("/readMany")
    @ResponseBody
    public Map<String, Object> readMany(@RequestParam("messages") String messages) {
        Map<String, Object> map = new HashMap(1);

        List<UserMessageDto> messagesParsed = JSON.parseArray(messages, UserMessageDto.class);
        List<Long> ids = new ArrayList<>();
        for (UserMessageDto messageDto : messagesParsed) {
            if (!messageDto.isRead()) {
                //当前还是未读状态的消息，标记已读
                ids.add(messageDto.getId());
            }
        }
        userMessageService.updateManyUserMessagesReadByIds(ids);
        map.put("data", Constants.SUCCESS);
        return map;
    }

    /**
     * 对当前用户所有消息标记已读的ajax交互
     *
     * @return
     */
    @RequestMapping("/readAll")
    @ResponseBody
    public Map<String, Object> readAll() {
        Map<String, Object> map = new HashMap(1);
        Long userId = userService.getSessionUserInfo().getId();
        userMessageService.updateManyUserMessagesReadByUserId(userId);
        map.put("data", Constants.SUCCESS);
        return map;
    }

    /**
     * 删除多个消息的ajax交互
     *
     * @param messages 多个消息的json串，用fastjson转换为list
     * @return
     */
    @RequestMapping("/deleteMany")
    @ResponseBody
    public Map<String, Object> deleteMany(@RequestParam("messages") String messages) {
        Map<String, Object> map = new HashMap(1);

        List<UserMessageDto> messagesParsed = JSON.parseArray(messages, UserMessageDto.class);
        List<Long> ids = new ArrayList<>();
        for (UserMessageDto messageDto : messagesParsed) {
            ids.add(messageDto.getId());

        }

        userMessageService.deleteManyUserMessagesByIds(ids);
        map.put("data", Constants.SUCCESS);
        return map;
    }

    /**
     * 跳转发送消息
     *
     * @return
     */
    @RequestMapping("/send")
    public String send(Model model) {
        model.addAttribute(ModelConstants.ROLES_MODEL_KEY, JSON.toJSONString(User.ROLES));
        model.addAttribute(ModelConstants.CAMPUS_NAMES_MODEL_KEY, JSON.toJSONString(CampusEnum.getCampusNamesList()));
        return "user/message/send";
    }

    /**
     * 发送消息页面，用户综合搜索的ajax交互
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    @RequestMapping("/getUserSendTo")
    @ResponseBody
    public ResultMap<List<UserSendTo>> getUserSendTo(MyPage myPage, UserSendToSearchCondition condition) {
        PageInfo<UserSendTo> pageInfo = userService.listUsersSendTo(myPage, condition);
        return new ResultMap<>(0, "", (int) pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 重定向到发送消息页面
     *
     * @param model
     * @param user  发送的用户信息
     * @return
     */
    @RequestMapping("/sendForm")
    public String sendForm(Model model, User user) {
        model.addAttribute(ModelConstants.USER_SEND_TO_MODEL_KEY, user);
        model.addAttribute(ModelConstants.USER_SEND_TO_SHOW_MODEL_KEY, "@" + user.getUserRole() + "-" + user.getUserRealName());
        return "user/message/sendForm";
    }

    /**
     * 上传图片附件
     *
     * @param file
     * @return
     */
    @RequestMapping("/uploadPicture")
    @ResponseBody
    public Map<String, Object> uploadUserIcon(@RequestParam(value = "file", required = false) MultipartFile file) {
        Map<String, Object> map2 = new HashMap<>(1);
        Map<String, Object> map = new HashMap<>(3);

        String picture = userMessageService.uploadPicture(file);

        //返回layui规定的文件上传模块JSON格式
        map.put("code", 0);
        map.put("msg", "");
        map2.put("src", picture);
        map.put("data", map2);
        return map;
    }

    /**
     * 发送消息给用户，插入数据库
     *
     * @param message 新发送的消息
     * @return
     */
    @RequestMapping("/insertSendMessage")
    @ResponseBody
    public Map<String, Object> insertSendMessage(@RequestParam(value = "hide", required = false) String hide, UserMessage message) {
        Map<String, Object> map = new HashMap<>(1);

        if (!Constants.ON.equals(hide)) {
            //未开匿名
            Long userId = userService.getSessionUserInfo().getId();
            message.setUserFromId(userId);
        }
        message.setMessageTime(new Date());

        if (!UserMessageUtils.isValidUserMessageUpdateInfo(message)) {
            String msg = "insertSendMessage方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        map.put("data", userMessageService.insertUserMessage(message));

        return map;
    }

    /**
     * 消息详情页面点击“回复”跳转发送信息页面
     *
     * @param model
     * @param user  发送的用户信息
     * @return
     */
    @RequestMapping("/reply")
    public String reply(Model model, User user) {
        model.addAttribute(ModelConstants.USER_SEND_TO_MODEL_KEY, user);
        model.addAttribute(ModelConstants.USER_SEND_TO_SHOW_MODEL_KEY, "@" + user.getUserRole() + "-" + user.getUserRealName());
        return "user/message/sendForm";
    }

    /**
     * 返回@指定用户的字符串显示结果
     *  如，"@管理员-张三"
     *
     * @param user 用户
     * @return 回显字串
     */
    private String getUserSendToShow(User user){
        if (user == null){
            return "";
        }
        return "@" + user.getUserRole() + "-" + user.getUserRealName()+"; ";
    }


    /**
     * 返回@指定用户的字符串显示结果
     *  如，"@管理员-张三"
     *
     * @param user 用户
     * @return 回显字串
     */
    private String getUserSendToShow(UserSendTo user){
        if (user == null){
            return "";
        }
        return "@" + user.getUserRole() + "-" + user.getUserRealName()+"; ";
    }

    /**
     * 回复时重定向到发送消息页面
     *
     * @param model
     * @param id    要回复的用户id
     * @param title 原消息标题
     * @return
     */
    @RequestMapping("/replyForm")
    public String replyForm(Model model, @RequestParam("id") Long id, @RequestParam(value = "title", required = false) String title) {
        User user = userService.getUserById(id);
        model.addAttribute(ModelConstants.USER_SEND_TO_MODEL_KEY, user);
        model.addAttribute(ModelConstants.USER_SEND_TO_SHOW_MODEL_KEY, getUserSendToShow(user));
        model.addAttribute(ModelConstants.REPLY_TITLE_MODEL_KEY, "回复--" + title);
        return "user/message/sendForm";
    }

    /**
     * 重定向到批量发送消息页面
     *
     * @param model
     * @param users 批量发送的用户信息的json串
     * @return
     */
    @RequestMapping("/many/sendForm")
    public String manySendForm(Model model, @RequestParam("users") String users) {
        List<UserSendTo> usersParsed = JSON.parseArray(users, UserSendTo.class);
        List<Long> ids = new ArrayList<>();
        StringBuffer userSendToShow = new StringBuffer();
        for (UserSendTo userSendTo : usersParsed) {
            ids.add(userSendTo.getId());
            userSendToShow.append(getUserSendToShow(userSendTo));
        }
        model.addAttribute(ModelConstants.USER_SEND_TO_IDS_MODEL_KEY, JSON.toJSONString(ids));
        model.addAttribute(ModelConstants.USER_SEND_TO_SHOW_MODEL_KEY, userSendToShow.toString());
        return "user/message/manySendForm";
    }

    /**
     * 批量发送消息给用户，插入数据库
     *
     * @param message 新发送的消息
     * @return
     */
    @RequestMapping("/many/insertSendMessage")
    @ResponseBody
    public Map<String, Object> manyInsertSendMessage(@RequestParam(value = "hide", required = false) String hide, @RequestParam("userIds") String ids, UserMessage message) {
        Map<String, Object> map = new HashMap<>(1);

        Long userId = null;
        if (!Constants.ON.equals(hide)) {
            //未开匿名
            userId = userService.getSessionUserInfo().getId();
//            message.setUserFromId(userId);
        }
//        message.setMessageTime(new Date());

        List<Long> idsParsed = JSON.parseArray(ids, Long.class);

        List<UserMessage> userMessages = new ArrayList<>(idsParsed.size());
        for (Long id : idsParsed) {
            UserMessage userMessage = new UserMessage();
            userMessage.setUserId(id);
            userMessage.setUserFromId(userId);
            userMessage.setMessageTitle(message.getMessageTitle());
            userMessage.setMessageContent(message.getMessageContent());
            userMessage.setMessagePicture(message.getMessagePicture());
            userMessage.setMessageTime(new Date());
            userMessage.setMessageRemark(message.getMessageRemark());

            if (!UserMessageUtils.isValidUserMessageUpdateInfo(userMessage)) {
                String msg = "insertSendMessage方法错误入参";
                logger.error(msg);
                throw new InvalidParameterException(msg);
            }
            userMessages.add(userMessage);

        }
        try {
            map.put("data", userMessageService.insertManyUserMessages(userMessages));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("data", Constants.FAILURE);
        }

        return map;
    }

    /**
     * 重定向到发送全体消息页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/all/sendForm")
    public String allSendForm(Model model) {
        model.addAttribute(ModelConstants.USER_SEND_TO_SHOW_MODEL_KEY, "@全体用户");
        return "user/message/allSendForm";
    }

    /**
     * 发送全体消息给用户，插入数据库
     *
     * @param message 新发送的消息
     * @return
     */
    @RequestMapping("/all/insertSendMessage")
    @ResponseBody
    public Map<String, Object> allInsertSendMessage(@RequestParam(value = "hide", required = false) String hide, UserMessage message) {
        Map<String, Object> map = new HashMap<>(1);

        Long userId = null;
        if (!Constants.ON.equals(hide)) {
            //未开匿名
            userId = userService.getSessionUserInfo().getId();
//            message.setUserFromId(userId);
        }
//        message.setMessageTime(new Date());

        List<User> users = userService.listAllUsers();

        List<UserMessage> userMessages = new ArrayList<>(users.size());
        for (User user : users) {
            UserMessage userMessage = new UserMessage();
            userMessage.setUserId(user.getId());
            userMessage.setUserFromId(userId);
            userMessage.setMessageTitle(message.getMessageTitle());
            userMessage.setMessageContent(message.getMessageContent());
            userMessage.setMessagePicture(message.getMessagePicture());
            userMessage.setMessageTime(new Date());
            userMessage.setMessageRemark(message.getMessageRemark());

            if (!UserMessageUtils.isValidUserMessageUpdateInfo(userMessage)) {
                String msg = "insertSendMessage方法错误入参";
                logger.error(msg);
                throw new InvalidParameterException(msg);
            }
            userMessages.add(userMessage);

        }
        try {
            map.put("data", userMessageService.insertManyUserMessages(userMessages));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("data", Constants.FAILURE);
        }

        return map;
    }
}
