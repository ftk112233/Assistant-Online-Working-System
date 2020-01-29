package com.jzy.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jzy.manager.constant.ModelConstants;
import com.jzy.manager.exception.InvalidParameterException;
import com.jzy.manager.util.RoleAndPermissionUtils;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.RoleAndPermissionSearchCondition;
import com.jzy.model.entity.RoleAndPermission;
import com.jzy.model.entity.User;
import com.jzy.model.vo.ResultMap;
import com.jzy.model.vo.RoleCheckbox;
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
 * @ClassName PermissionController
 * @Author JinZhiyun
 * @Description 角色权限管理的控制器
 * @Date 2019/11/21 15:04
 * @Version 1.0
 **/
@Controller
@RequestMapping("/permission/admin")
public class PermissionAdminController extends AbstractController {
    private final static Logger logger = LogManager.getLogger(PermissionAdminController.class);

    /**
     * 跳转权限管理页面
     *
     * @return
     */
    @RequestMapping("/page")
    public String page(Model model) {
        model.addAttribute(ModelConstants.ROLES_MODEL_KEY, JSON.toJSONString(User.ROLES));
        return "permission/admin/page";
    }


    /**
     * 查询角色权限信息的ajax交互
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return
     */
    @RequestMapping("/getRoleAndPermission")
    @ResponseBody
    public ResultMap<List<RoleAndPermission>> getRoleAndPermission(MyPage myPage, RoleAndPermissionSearchCondition condition) {
        PageInfo<RoleAndPermission> pageInfo = roleAndPermissionService.listRoleAndPerms(myPage, condition);
        return new ResultMap<>(0, "", (int) pageInfo.getTotal(), pageInfo.getList());
    }


    /**
     * 重定向到编辑角色权限iframe子页面并返回相应model
     *  其中被编辑的roleAndPermission信息中select的元素不能通过layui iframe直接赋值，因此经由后台model传值
     * @param model
     * @param roleAndPermission 当前要被编辑的权限信息
     * @return
     */
    @RequestMapping("/updateForm")
    public String updateForm(Model model, RoleAndPermission roleAndPermission) {
        model.addAttribute(ModelConstants.ROLES_MODEL_KEY, JSON.toJSONString(User.ROLES));
        model.addAttribute(ModelConstants.PERMISSION_EDIT_MODEL_KEY, roleAndPermission);
        return "permission/admin/permissionFormEdit";
    }

    /**
     * 重定向到添加角色权限iframe子页面并返回相应model
     *
     * @param model
     * @return
     */
    @RequestMapping("/insertForm")
    public String addForm(Model model) {
        model.addAttribute(ModelConstants.ROLES_MODEL_KEY, JSON.toJSONString(User.ROLES));
        return "permission/admin/permissionFormAdd";
    }


    /**
     * 角色权限管理中的编辑请求，由id修改
     *
     * @param roleAndPermission 修改后的权限信息
     * @return
     */
    @RequestMapping("/updateById")
    @ResponseBody
    public Map<String, Object> updateById(RoleAndPermission roleAndPermission) {
        Map<String, Object> map = new HashMap<>(1);

        if (!RoleAndPermissionUtils.isValidRoleAndPermissionUpdateInfo(roleAndPermission)) {
            String msg = "updateById方法错误入参";
            logger.error(msg);
            throw new InvalidParameterException(msg);
        }
        map.put("data", roleAndPermissionService.updateRoleAndPermissionInfo(roleAndPermission));

        return map;
    }

    /**
     * 角色权限管理中的添加角色权限请求。
     * 从前端接受的角色为checkbox形式，即角色可以选择多个；故需要对每个选中的role封装成一个roleAndPermission对象，后再执行insert
     *
     * @param roleCheckbox      角色checkbox输入 {@link RoleCheckbox}
     * @param roleAndPermission 新添加角色权限的信息
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    public Map<String, Object> insert(RoleCheckbox roleCheckbox, RoleAndPermission roleAndPermission) {
        Map<String, Object> map = new HashMap<>(1);

        List<RoleAndPermission> roleAndPermissions = roleCheckbox.getRoleAndPermissions(roleAndPermission);

        String result = FAILURE;
        for (RoleAndPermission rap : roleAndPermissions) {
            if (!RoleAndPermissionUtils.isValidRoleAndPermissionInsertInfo(rap)) {
                String msg = "insert方法错误入参";
                logger.error(msg);
                throw new InvalidParameterException(msg);
            }
            result = roleAndPermissionService.insertOneRoleAndPermission(rap);
            if ("roleAndPermRepeat".equals(result)) {
                map.put("data", result);
                return map;
            }
        }

        map.put("data", result);
        return map;
    }

    /**
     * 删除一个角色权限ajax交互
     *
     * @param id 被删除角色权限的id
     * @return
     */
    @RequestMapping("/deleteOne")
    @ResponseBody
    public Map<String, Object> deleteOne(@RequestParam("id") Long id) {
        Map<String, Object> map = new HashMap(1);

        roleAndPermissionService.deleteOneRoleAndPermissionById(id);
        map.put("data", SUCCESS);
        return map;
    }

    /**
     * 删除多个角色权限ajax交互
     *
     * @param roleAndPermissions 多个角色权限的json串，用fastjson转换为list
     * @return
     */
    @RequestMapping("/deleteMany")
    @ResponseBody
    public Map<String, Object> deleteMany(@RequestParam("roleAndPermissions") String roleAndPermissions) {
        Map<String, Object> map = new HashMap(1);

        List<RoleAndPermission> roleAndPermissionsParsed = JSON.parseArray(roleAndPermissions, RoleAndPermission.class);
        List<Long> ids = new ArrayList<>();
        for (RoleAndPermission roleAndPermission : roleAndPermissionsParsed) {
            ids.add(roleAndPermission.getId());
        }
        roleAndPermissionService.deleteManyRoleAndPermissionsByIds(ids);
        map.put("data", SUCCESS);
        return map;
    }
}
