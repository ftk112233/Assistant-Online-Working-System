package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.ModelConstants;
import com.jzy.manager.exception.NoAuthorizationException;
import com.jzy.manager.util.UserUtils;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.UserSearchCondition;
import com.jzy.model.entity.User;
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

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UserAdminController
 * @description 用户管理的控制器，其与UseController不同的是：后者为用户登后对自己的一些请求操作，和一些页面跳转；
 * 后者是用户管理（超管和学管才拥有的权利）中的请求操作
 * @date 2019/11/19 13:19
 **/
@Controller
@RequestMapping("/user/admin")
public class UserAdminController extends AbstractController {
    private final static Logger logger = Logger.getLogger(UserAdminController.class);

    /**
     * 跳转用户管理页面
     *
     * @return
     */
    @RequestMapping("/page")
    public String page(Model model) {
        model.addAttribute(ModelConstants.ROLES_MODEL_KEY, JSON.toJSONString(UserUtils.USER_ROLES));
        return "user/admin/page";
    }


    /**
     * 重定向到编辑用户iframe子页面并返回相应model
     *
     * @param model
     * @param user  当前要被编辑的用户信息
     * @return
     */
    @RequestMapping("/updateForm")
    public String updateForm(Model model, User user) {
        /**
         * 根据角色设置，当前角色可以编辑的角色权限。如，学管只能修改用户角色为[学管, 助教长, 助教，教师，游客]，不能设成管理员;
         *      而管理员可以修改用户角色为[管理员，学管, 助教长, 助教，教师，游客]
         */
        User userSessionInfo = userService.getSessionUserInfo();
        if (UserUtils.USER_ROLES.get(0).equals(userSessionInfo.getUserRole())) {
            //管理员
            model.addAttribute(ModelConstants.ROLES_MODEL_KEY, JSON.toJSONString(UserUtils.USER_ROLES));
        } else if (UserUtils.USER_ROLES.get(1).equals(userSessionInfo.getUserRole())) {
            if (UserUtils.USER_ROLES.get(0).equals(user.getUserRole())) {
                //如果学管编辑了管理员，无权限
                return "tips/noPermissions";
            }
            //学管
            List<String> roles = new ArrayList<>();
            //[学管, 助教长, 助教，教师，游客]
            roles.add(UserUtils.USER_ROLES.get(1));
            roles.add(UserUtils.USER_ROLES.get(2));
            roles.add(UserUtils.USER_ROLES.get(3));
            roles.add(UserUtils.USER_ROLES.get(4));
            roles.add(UserUtils.USER_ROLES.get(5));
            model.addAttribute(ModelConstants.ROLES_MODEL_KEY, JSON.toJSONString(roles));
        } else {
            String msg = userSessionInfo.getId() + "用户突破了权限!";
            logger.error(msg);
            throw new NoAuthorizationException(msg);
        }

        model.addAttribute(ModelConstants.USER_EDIT_MODEL_KEY, user);
        return "user/admin/userForm";
    }


    /**
     * 查询用户信息的ajax交互
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public ResultMap<List<User>> getUserInfo(MyPage myPage, UserSearchCondition condition) {
        PageInfo<User> pageInfo = userService.listUsers(myPage, condition);
        return new ResultMap<>(0, "", (int) pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 用户管理中的编辑用户请求，由id修改
     *
     * @param user 修改后的用户信息
     * @return
     */
    @RequestMapping("/updateById")
    @ResponseBody
    public Map<String, Object> updateById(User user) {
        Map<String, Object> map = new HashMap<>(1);

        if (!UserUtils.isValidUserUpdateInfo(user)) {
            String msg = "updateById方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }
        map.put("data", userService.updateUserInfo(user));

        return map;
    }

    /**
     * 用户管理中的添加用户请求
     *
     * @param user 新添加用户的信息
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    public Map<String, Object> insert(User user) {
        Map<String, Object> map = new HashMap<>(1);

        if (!UserUtils.isValidUserInsertInfo(user)) {
            String msg = "updateById方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }
        map.put("data", userService.insertUser(user));

        return map;
    }

    /**
     * 删除一个用户ajax交互
     *
     * @param id       被删除用户的id
     * @param userRole 被删除用户的角色
     * @return
     */
    @RequestMapping("/deleteOne")
    @ResponseBody
    public Map<String, Object> deleteOne(@RequestParam("id") Long id, @RequestParam("userRole") String userRole) {
        Map<String, Object> map = new HashMap(1);
        /**
         * 根据角色设置，当前角色可以编辑的角色权限。如，学管只能修改用户角色为[学管, 助教长, 助教，教师，游客]，不能设成管理员;
         *      而管理员可以修改用户角色为[管理员，学管, 助教长, 助教，教师，游客]
         */
        User userSessionInfo = userService.getSessionUserInfo();
        if (UserUtils.USER_ROLES.get(1).equals(userSessionInfo.getUserRole())) {
            if (UserUtils.USER_ROLES.get(0).equals(userRole)) {
                //如果学管尝试删除管理员，无权限
                map.put("data", "noPermissions");
                return map;
            }
        }
        if (userSessionInfo.getId().equals(id)) {
            //不能删除自己
            map.put("data", "noPermissionsToDeleteYourself");
            return map;
        }
        userService.deleteOneUserById(id);
        map.put("data", Constants.SUCCESS);
        return map;
    }

    /**
     * 删除多个用户ajax交互
     *
     * @param users 多个用户的json串，用fastjson转换为list<User>
     * @return
     */
    @RequestMapping("/deleteMany")
    @ResponseBody
    public Map<String, Object> deleteMany(@RequestParam("users") String users) {
        Map<String, Object> map = new HashMap(1);
        /**
         * 根据角色设置，当前角色可以编辑的角色权限。如，学管只能修改用户角色为[学管, 助教长, 助教，教师，游客]，不能设成管理员;
         *      而管理员可以修改用户角色为[管理员，学管, 助教长, 助教，教师，游客]
         */
        User userSessionInfo = userService.getSessionUserInfo();

        List<User> usersParsed = JSON.parseArray(users, User.class);
        List<Long> ids = new ArrayList<>();
        for (User user : usersParsed) {
            if (userSessionInfo.getId().equals(user.getId())) {
                //不能删除自己
                map.put("data", "noPermissionsToDeleteYourself");
                return map;
            }
            if (UserUtils.USER_ROLES.get(1).equals(userSessionInfo.getUserRole())) {
                if (UserUtils.USER_ROLES.get(0).equals(user.getUserRole())) {
                    //如果学管尝试删除管理员，无权限
                    map.put("data", "noPermissions");
                    return map;
                }
            }
            ids.add(user.getId());
        }
        userService.deleteManyUsersByIds(ids);
        map.put("data", Constants.SUCCESS);
        return map;
    }


    /**
     * 导入用户和助教
     *
     * @param file 上传的表格
     * @param type 1表示仅导入用户
     *             2表示导入用户和助教
     * @return
     */
    @RequestMapping("/import")
    @ResponseBody
    public Map<String, Object> importExcel(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "type") Integer type) {
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

        if (type != null) {
            AssistantInfoExcel excel = null;
            try {
                excel = new AssistantInfoExcel(file.getInputStream(), ExcelVersionEnum.getVersionByName(file.getOriginalFilename()));
                excel.readUsersAndAssistantsFromExcel();
            } catch (IOException e) {
                e.printStackTrace();
                map.put("msg", Constants.FAILURE);
                return map;
            }
            if (type.equals(1)) {
                try {
                    userService.insertAndUpdateUsersFromExcel(excel.getUsers());
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                }
            } else if (type.equals(2)) {
                try {
                    userService.insertAndUpdateUsersFromExcel(excel.getUsers());
                    System.out.println(excel.getAssistants()+"???????");
                    assistantService.insertAndUpdateAssistantsFromExcel(excel.getAssistants());
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                }
            }
            map.put("msg", Constants.SUCCESS);
        }

        return map;
    }
}
