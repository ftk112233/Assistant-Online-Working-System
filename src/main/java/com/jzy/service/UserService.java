package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.model.dto.EmailVerifyCode;
import com.jzy.model.dto.MyPage;
import com.jzy.model.dto.UserSearchCondition;
import com.jzy.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @IntefaceName UserService
 * @description 用户业务
 * @date 2019/11/13 22:12
 **/
public interface UserService {
    /**
     * 根据用户表自增主键id查询出用户信息
     *
     * @param id 用户表自增主键
     * @return 查询到的用户对象
     */
    User getUserById(Long id);

    /**
     * 根据用户名查询出用户信息
     *
     * @param userName 用户名
     * @return 查询到的用户对象
     */
    User getUserByName(String userName);

    /**
     * 根据用户工号查询出用户信息
     *
     * @param userWorkId 用户工号
     * @return
     */
    User getUserByWorkId(String userWorkId);

    /**
     * 根据用户身份证查询出用户信息
     *
     * @param userIdCard 身份证
     * @return
     */
    User getUserByIdCard(String userIdCard);

    /**
     * 根据用户邮箱查询出用户信息
     *
     * @param userEmail 用户邮箱
     * @return
     */
    User getUserByEmail(String userEmail);

    /**
     * 根据用户电话查询出用户信息
     *
     * @param userPhone 用户电话
     * @return
     */
    User getUserByPhone(String userPhone);

    /**
     * 前台用户名/密码登录请求的业务逻辑，根据输入的用户名————这里有四种可能：
     *  1、用户名userName
     *  2、用户工号userWorkId
     *  3、用户邮箱userEmail
     *  4、用户电话userPhone
     *
     * 根据不同的情况调用相应业务方法，返回查询到的user信息
     *
     * @param userName 用户名，四种可能，如上文
     * @return 查询到的user信息
     */
    User getUserByNameOrEmailOrPhoneOrIdCard(String userName);

    /**
     * 返回当前session中的登录用户信息
     *
     * @return
     */
    User getSessionUserInfo();


    /**
     * 当用户修改过资料后，更新当前session中的登录用户信息（通过自增代理主键id更新）
     *
     * @return
     */
    User updateSessionUserInfo();

    /**
     * 发送邮箱验证码业务
     *
     * @param userEmail 向哪个邮箱法发
     * @return 发送的邮箱验证码的信息封装对象
     * @throws Exception
     */
    EmailVerifyCode sendVerifyCodeToEmail(String userEmail) throws Exception;

    /**
     * 验证邮箱验证码正确与否，是否失效
     *
     * @param emailVerifyCode 输入验证码信息封装对象
     * @return
     */
    boolean ifValidEmailVerifyCode(EmailVerifyCode emailVerifyCode);

    /**
     * 忘记密码时用邮箱验证，重置密码
     *
     * @param userEmail 用户邮箱
     * @param userPassword 用户新密码（明文）
     */
    void updatePasswordByEmail(String userEmail, String userPassword);


    /**
     * 上传用户头像
     *
     * @param file 上传得到的文件
     * @return 保存文件的名称，用来传回前端，用以之后修改用户信息中的头像路径
     */
    String uploadUserIcon(MultipartFile file);

    /**
     * 用户自己修改用户基本资料，注意这里不是用户管理中的更改
     *
     * @param user 用户修改后的信息
     * @return
     */
    String updateOwnInfo(User user);

    /**
     * 根据id修改用户邮箱
     *
     * @param id 用户id
     * @param userEmail 新安全邮箱
     */
    void updateEmailById(Long id, String userEmail);

    /**
     * 根据id修改用户手机
     *
     * @param id 用户id
     * @param userPhone 新安全手机
     */
    void updatePhoneById(Long id, String userPhone);

    /**
     * 根据id修改用户密码，这里密码是明文，加密后交给dao层更新
     *
     * @param id 用户id
     * @param salt 用户密码的盐
     * @param userPasswordNotEncrypted 用明文密码
     */
    void updatePasswordById(Long id, String salt, String userPasswordNotEncrypted);

    /**
     * 返回符合条件的学生信息分页结果
     *
     * @param myPage 分页{页号，每页数量}
     * @param condition  查询条件入参
     * @return
     */
    PageInfo<User> listUsers(MyPage myPage, UserSearchCondition condition);

    /**
     * 用户管理中修改用户信息由主键修改
     *
     * @param user 修改用户信息
     * @return 返回字串代表，更新的结果。如更新成功，用户名已存在等等...
     */
    String updateUserInfo(User user);

    /**
     * 用户管理中的添加用户
     *
     * @param user 新添加用户的信息
     * @return
     */
    String insertUser(User user);

    /**
     * 根据id删除一个用户
     *
     * @param id 用户id
     */
    void deleteOneUserById(Long id);

    /**
     * 根据id删除多个用户
     *
     * @param ids 用户id的list
     */
    void deleteManyUsersByIds(List<Long> ids);
}
