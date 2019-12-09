package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.ModelConstants;
import com.jzy.manager.exception.ExcelColumnNotFoundException;
import com.jzy.manager.exception.InputFileTypeException;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.exception.NoAuthorizationException;
import com.jzy.manager.util.UserUtils;
import com.jzy.model.RoleEnum;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.UpdateResult;
import com.jzy.model.dto.UserSearchCondition;
import com.jzy.model.entity.User;
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

import java.io.IOException;
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
    private final static Logger logger = LogManager.getLogger(UserAdminController.class);

    /**
     * 跳转用户管理页面
     *
     * @return
     */
    @RequestMapping("/page")
    public String page(Model model) {
        model.addAttribute(ModelConstants.ROLES_MODEL_KEY, JSON.toJSONString(User.ROLES));
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
    public String updateForm(Model model, User user) throws NoAuthorizationException {
        /**
         * 根据角色设置，当前角色可以编辑的角色权限。如，学管只能修改用户角色为[学管, 助教长, 助教，教师，游客]，不能设成管理员;
         *      而管理员可以修改用户角色为[管理员，学管, 助教长, 助教，教师，游客]
         */
        User userSessionInfo = userService.getSessionUserInfo();
        if (RoleEnum.ADMINISTRATOR.equals(userSessionInfo.getUserRole())) {
            //管理员
            model.addAttribute(ModelConstants.ROLES_MODEL_KEY, JSON.toJSONString(User.ROLES));
        } else if (RoleEnum.ASSISTANT_MANAGER.equals(userSessionInfo.getUserRole())) {
            if (RoleEnum.ADMINISTRATOR.equals(user.getUserRole())) {
                //如果学管编辑了管理员，无权限
                return "tips/noPermissions";
            }
            //学管
            List<String> roles = new ArrayList<>();
            //[学管, 助教长, 助教，教师，游客]
            roles.add(RoleEnum.ASSISTANT_MANAGER.getRole());
            roles.add(RoleEnum.ASSISTANT_MASTER.getRole());
            roles.add(RoleEnum.ASSISTANT.getRole());
            roles.add(RoleEnum.TEACHER.getRole());
            roles.add(RoleEnum.GUEST.getRole());
            model.addAttribute(ModelConstants.ROLES_MODEL_KEY, JSON.toJSONString(roles));
        } else {
            String msg = userSessionInfo.getId() + "用户突破了权限!";
            logger.error(msg);
            throw new NoAuthorizationException(msg);
        }

        model.addAttribute(ModelConstants.USER_EDIT_MODEL_KEY, user);
        return "user/admin/userFormEdit";
    }

    /**
     * 重定向到编辑用户iframe子页面并返回相应model
     *
     * @param model
     * @param user  当前要被编辑的用户信息
     * @return
     */
    @RequestMapping("/insertForm")
    public String insertForm(Model model, User user) throws NoAuthorizationException {
        /**
         * 根据角色设置，当前角色可以编辑的角色权限。如，学管只能修改用户角色为[学管, 助教长, 助教，教师，游客]，不能设成管理员;
         *      而管理员可以修改用户角色为[管理员，学管, 助教长, 助教，教师，游客]
         */
        User userSessionInfo = userService.getSessionUserInfo();
        if (RoleEnum.ADMINISTRATOR.equals(userSessionInfo.getUserRole())) {
            //管理员
            model.addAttribute(ModelConstants.ROLES_MODEL_KEY, JSON.toJSONString(User.ROLES));
        } else if (RoleEnum.ASSISTANT_MANAGER.equals(userSessionInfo.getUserRole())) {
            if (RoleEnum.ADMINISTRATOR.equals(user.getUserRole())) {
                //如果学管编辑了管理员，无权限
                return "tips/noPermissions";
            }
            //学管
            List<String> roles = new ArrayList<>();
            //[学管, 助教长, 助教，教师，游客]
            roles.add(RoleEnum.ASSISTANT_MANAGER.getRole());
            roles.add(RoleEnum.ASSISTANT_MASTER.getRole());
            roles.add(RoleEnum.ASSISTANT.getRole());
            roles.add(RoleEnum.TEACHER.getRole());
            roles.add(RoleEnum.GUEST.getRole());
            model.addAttribute(ModelConstants.ROLES_MODEL_KEY, JSON.toJSONString(roles));
        } else {
            String msg = userSessionInfo.getId() + "用户突破了权限!";
            logger.error(msg);
            throw new NoAuthorizationException(msg);
        }

        model.addAttribute(ModelConstants.USER_EDIT_MODEL_KEY, user);
        return "user/admin/userFormAdd";
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
     * 用户管理中的上传头像
     *
     * @param file
     * @param user
     * @return
     * @throws InvalidParameterException
     */
    @RequestMapping("/uploadUserIcon")
    @ResponseBody
    public Map<String, Object> uploadUserIcon(@RequestParam(value = "file", required = false) MultipartFile file, User user) throws InvalidParameterException {
        Map<String, Object> map2 = new HashMap<>(1);
        Map<String, Object> map = new HashMap<>(3);

        String userIcon = userService.uploadUserIcon(file, user.getId().toString());

        //返回layui规定的文件上传模块JSON格式
        map.put("code", 0);
        map.put("msg", "");
        map2.put("src", userIcon);
        map.put("data", map2);
        return map;
    }

    /**
     * 用户管理中的编辑用户请求，由id修改
     *
     * @param user 修改后的用户信息
     * @return
     */
    @RequestMapping("/updateById")
    @ResponseBody
    public Map<String, Object> updateById(User user) throws InvalidParameterException {
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
    public Map<String, Object> insert(User user) throws InvalidParameterException {
        Map<String, Object> map = new HashMap<>(1);

        if (!UserUtils.isValidUserInsertInfo(user)) {
            String msg = "updateById方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }

        map.put("data", userService.insertUser(user).getResult());

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
        if (RoleEnum.ASSISTANT_MANAGER.equals(userSessionInfo.getUserRole())) {
            if (RoleEnum.ADMINISTRATOR.equals(userRole)) {
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
            if (RoleEnum.ASSISTANT_MANAGER.equals(userSessionInfo.getUserRole())) {
                if (RoleEnum.ADMINISTRATOR.equals(user.getUserRole())) {
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
     * 条件删除多个用户ajax交互
     *
     * @param condition 输入的查询条件
     * @return
     */
    @RequestMapping("/deleteByCondition")
    @ResponseBody
    public Map<String, Object> deleteByCondition(UserSearchCondition condition) {
        Map<String, Object> map = new HashMap(1);
        map.put("data", userService.deleteUsersByCondition(condition));
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
    public Map<String, Object> importExcel(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "type") Integer type) throws InvalidParameterException {
        Map<String, Object> map2 = new HashMap<>(1);
        Map<String, Object> map = new HashMap<>();
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

        long startTime = System.currentTimeMillis();   //获取开始时间
        int excelEffectiveDataRowCount = 0; //表格有效数据行数
        int databaseUpdateRowCount = 0; //数据库更新记录数
        int databaseInsertRowCount = 0; //数据库插入记录数
        int databaseDeleteRowCount = 0; //数据库删除记录数

        if (type != null) {
            AssistantInfoExcel excel = null;
            try {
                excel = new AssistantInfoExcel(file.getInputStream(), ExcelVersionEnum.getVersionByName(file.getOriginalFilename()));
                excelEffectiveDataRowCount=excel.readUsersAndAssistantsFromExcel();
            } catch (IOException e) {
                e.printStackTrace();
                map.put("msg", Constants.FAILURE);
                return map;
            } catch (ExcelColumnNotFoundException e) {
                e.printStackTrace();
                map.put("msg", "excelColumnNotFound");
                return map;
            } catch (InputFileTypeException e) {
                e.printStackTrace();
                map.put("msg", Constants.FAILURE);
                return map;
            }

            if (type.equals(1)) {
                try {
                    UpdateResult useResult=userService.insertAndUpdateUsersFromExcel(excel.getUsers());
                    databaseInsertRowCount += (int) useResult.getInsertCount();
                    databaseUpdateRowCount += (int) useResult.getUpdateCount();
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                }
            } else if (type.equals(2)) {
                try {
                    UpdateResult userResult=userService.insertAndUpdateUsersFromExcel(excel.getUsers());
                    databaseInsertRowCount += (int) userResult.getInsertCount();
                    databaseUpdateRowCount += (int) userResult.getUpdateCount();

                    UpdateResult assistantResult=assistantService.insertAndUpdateAssistantsFromExcel(excel.getAssistants());
                    databaseInsertRowCount += (int) assistantResult.getInsertCount();
                    databaseUpdateRowCount += (int) assistantResult.getUpdateCount();
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("msg", Constants.FAILURE);
                    return map;
                }
            }

            long endTime = System.currentTimeMillis(); //获取结束时间


            Speed speedOfExcelImport = new Speed(excelEffectiveDataRowCount, endTime - startTime);
            SqlProceedSpeed speedOfDatabaseImport = new SqlProceedSpeed(databaseUpdateRowCount, databaseInsertRowCount, databaseDeleteRowCount, endTime - startTime);
            speedOfExcelImport.parseSpeed();
            speedOfDatabaseImport.parseSpeed();


            map.put("excelSpeed",speedOfExcelImport);
            map.put("databaseSpeed",speedOfDatabaseImport);

            map.put("msg", Constants.SUCCESS);
        }


        return map;
    }
}
