package com.jzy.service;

import com.github.pagehelper.PageInfo;
import com.jzy.manager.exception.InvalidEmailException;
import com.jzy.manager.exception.InvalidFileInputException;
import com.jzy.model.dto.*;
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
     * 查询出所有的用户
     *
     * @return 所有用户集合
     */
    List<User> listAllUsers();

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
     * @return 查询到的用户对象
     */
    User getUserByWorkId(String userWorkId);

    /**
     * 根据用户身份证查询出用户信息
     *
     * @param userIdCard 身份证
     * @return 查询到的用户对象
     */
    User getUserByIdCard(String userIdCard);

    /**
     * 根据用户邮箱查询出用户信息
     *
     * @param userEmail 用户邮箱
     * @return 查询到的用户对象
     */
    User getUserByEmail(String userEmail);

    /**
     * 根据用户电话查询出用户信息
     *
     * @param userPhone 用户电话
     * @return 查询到的用户对象
     */
    User getUserByPhone(String userPhone);

    /**
     * 前台用户名/密码登录请求的业务逻辑，根据输入的用户名————这里有四种可能：
     * 1、用户名userName
     * 2、用户身份证userIdCard
     * 3、用户邮箱userEmail
     * 4、用户电话userPhone
     * <p>
     * 根据不同的情况调用相应业务方法，返回查询到的user信息。
     *
     * @param userName 用户名，四种可能，如上文
     * @return 查询到的user信息
     */
    User getUserByNameOrEmailOrPhoneOrIdCard(String userName);

    /**
     * 返回当前session中的登录用户信息
     *
     * @return 查询到的用户对象
     */
    User getSessionUserInfo();

    /**
     * 当用户修改过资料后，更新当前session中的登录用户信息（通过自增代理主键id更新）
     *
     * @return 会话中的用户信息
     */
    User updateSessionUserInfo();

    /**
     * 想指定邮箱发送邮箱验证码
     *
     * @param userEmail 向哪个邮箱法发
     * @return 发送的邮箱验证码的信息封装对象（目标邮箱，验证码）
     * @throws InvalidEmailException 不合法的用户邮箱输入
     */
    EmailVerifyCode sendVerifyCodeToEmail(String userEmail) throws InvalidEmailException;

    /**
     * 验证邮箱验证码正确与否；或是否失效
     *
     * @param emailVerifyCode 输入验证码信息封装对象
     * @return 不失效且正确=true
     */
    boolean isValidEmailVerifyCode(EmailVerifyCode emailVerifyCode);

    /**
     * 根据用户邮箱修改密码。此处入参userPassword为明文，加密后传给dao接口更新数据库
     *
     * @param userEmail    用户邮箱
     * @param userPassword 用户新密码（明文）
     * @return 更新记录数
     */
    long updatePasswordByEmail(String userEmail, String userPassword);

    /**
     * 上传用户头像
     *
     * @param file 上传得到的文件
     * @return 保存文件的名称，用来传回前端，用以之后修改用户信息中的头像路径
     * @throws InvalidFileInputException 不合法的文件入参
     */
    String uploadUserIcon(MultipartFile file) throws InvalidFileInputException;

    /**
     * 上传用户头像
     *
     * @param file 上传得到的文件
     * @param id   用户id
     * @return 保存文件的名称，用来传回前端，用以之后修改用户信息中的头像路径
     * @throws InvalidFileInputException 不合法的文件入参
     */
    String uploadUserIcon(MultipartFile file, String id) throws InvalidFileInputException;

    /**
     * 用户自己修改用户基本资料，注意这里不是用户管理中的更改
     *
     * @param user 用户修改后的信息
     * @return 1."failure"：错误入参等异常
     * 2. "idCardRepeat"：身份证重复
     * 3. "nameRepeat"：姓名重复
     * 4."unchanged": 对比数据库原记录未做任何修改
     * 5."success": 更新成功
     */
    String updateOwnInfo(User user);

    /**
     * 根据id修改用户邮箱
     *
     * @param id        用户id
     * @param userEmail 新安全邮箱
     * @return 更新记录数
     */
    long updateEmailById(Long id, String userEmail);

    /**
     * 根据id修改用户手机
     *
     * @param id        用户id
     * @param userPhone 新安全手机
     * @return 更新记录数
     */
    long updatePhoneById(Long id, String userPhone);

    /**
     * 根据id修改用户密码，这里密码是明文，加密后交给dao层更新
     *
     * @param id                       用户id
     * @param salt                     用户密码的盐
     * @param userPasswordNotEncrypted 用明文密码
     * @return 更新记录数
     */
    long updatePasswordById(Long id, String salt, String userPasswordNotEncrypted);

    /**
     * 返回符合条件的用户信息分页结果
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return 分页结果
     */
    PageInfo<User> listUsers(MyPage myPage, UserSearchCondition condition);

    /**
     * 用户管理中修改用户信息由主键修改
     *
     * @param user 修改用户信息
     * @return 1."failure"：错误入参等异常
     * 2."workIdRepeat"：工号重复
     * 3. "idCardRepeat"：身份证重复
     * 4. "nameRepeat"：姓名重复
     * 5. "emailRepeat"：邮箱重复
     * 6. "phoneRepeat"：电话重复
     * 7."unchanged": 对比数据库原记录未做任何修改
     * 8."success": 更新成功
     */
    String updateUserInfo(User user);

    /**
     * 用户管理中的添加用户。
     * 如果更新成功，需要向新更新的用户发送欢迎消息。
     *
     * @param user 新添加用户的信息
     * @return 1."failure"：错误入参等异常
     * 2."workIdRepeat"：工号重复
     * 3. "idCardRepeat"：身份证重复
     * 4. "nameRepeat"：姓名重复
     * 5. "emailRepeat"：邮箱重复
     * 6. "phoneRepeat"：电话重复
     * 7."success": 更新成功
     */
    UpdateResult insertOneUser(User user);

    /**
     * 根据id删除一个用户。
     * 注意删除用户的同时需要删除用户的头像，如果头像不是默认头像
     *
     * @param id 用户id
     * @return 更新记录数
     */
    long deleteOneUserById(Long id);

    /**
     * 根据id删除多个用户
     * 注意删除用户的同时需要删除用户的头像，如果头像不是默认头像
     *
     * @param ids 用户id的list
     * @return 更新记录数
     */
    long deleteManyUsersByIds(List<Long> ids);

    /**
     * 根据从excel中读取到的user信息，更新插入多个。根据工号判断：
     * if 当前工号不存在
     * 执行插入
     * else
     * 根据工号更新
     *
     * @param users 要更新的用户信息集合
     * @return 更新结果
     */
    DefaultFromExcelUpdateResult insertAndUpdateUsersFromExcel(List<User> users);

    /**
     * 根据工号更新用户信息
     *
     * @param user 新的用户信息
     * @return 1."failure"：错误入参等异常
     * 2. "idCardRepeat"：身份证重复
     * 3. "nameRepeat"：姓名重复
     * 4. "emailRepeat"：邮箱重复
     * 5. "phoneRepeat"：电话重复
     * 6."unchanged": 对比数据库原记录未做任何修改
     * 7."success": 更新成功
     */
    String updateUserByWorkId(User user);

    /**
     * 根据工号更新用户信息
     *
     * @param originalUser 原来用户信息
     * @param newUser      新的用户信息
     * @return 1."failure"：错误入参等异常
     * 2. "idCardRepeat"：身份证重复
     * 3. "nameRepeat"：姓名重复
     * 4. "emailRepeat"：邮箱重复
     * 5. "phoneRepeat"：电话重复
     * 6."unchanged": 对比数据库原记录未做任何修改
     * 7."success": 更新成功
     */
    String updateUserByWorkId(User originalUser, User newUser);

    /**
     * 根据输入条件删除指定的users
     *
     * @param condition 输入条件封装
     * @return 1."failure"：错误入参等异常
     * 2."noPermissionsToDeleteYourself"：不能删除自己
     * 3. "noPermissions"：没有权限删除
     * 4."success": 更新成功
     */
    String deleteUsersByCondition(UserSearchCondition condition);

    /**
     * 发送消息页面，用户综合查询
     *
     * @param myPage    分页{页号，每页数量}
     * @param condition 查询条件入参
     * @return 分页结果
     */
    PageInfo<UserSendTo> listUsersSendTo(MyPage myPage, UserSendToSearchCondition condition);
}
