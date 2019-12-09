package com.jzy.web.shiro;

import com.jzy.manager.constant.Constants;
import com.jzy.manager.constant.SessionConstants;
import com.jzy.manager.util.ShiroUtils;
import com.jzy.model.entity.User;
import com.jzy.model.vo.UserLoginResult;
import com.jzy.service.RoleAndPermissionService;
import com.jzy.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

import java.util.List;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName UserRealm
 * @description shiro的realm
 * @date 2019/11/13 18:41
 **/
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleAndPermissionService roleAndPermissionService;

    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    @Autowired
    protected HashOperations<String, String, Object> hashOps;

    @Autowired
    protected ValueOperations<String, Object> valueOps;

    @Autowired
    protected ListOperations<String, Object> listOps;

    @Autowired
    protected SetOperations<String, Object> setOps;

    @Autowired
    protected ZSetOperations<String, Object> zSetOps;

    /**
     * 授权逻辑
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = userService.getSessionUserInfo();
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 用户的角色集合
//        Set<String> roles = new HashSet<>();
//        roles.add(user.getUserRole());
//        info.setRoles(roles);
//         用户的角色对应的所有权限，如果只使用角色定义访问权限，下面可以不要
//         只有角色并没有颗粒度到每一个按钮 或 是操作选项  PERMISSIONS 是可选项
        List<String> permissions = roleAndPermissionService.listPermsByRole(user.getUserRole());
        info.addStringPermissions(permissions);
        return info;
    }

    /**
     * 认证逻辑
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String loginWithoutPasswordSuccess = (String) ShiroUtils.getSession().getAttribute(SessionConstants.LOGIN_WITHOUT_PASSWORD_SESSION_KEY);
        if (Constants.SUCCESS.equals(loginWithoutPasswordSuccess)) {
            //通过邮箱证码等手段免密登录成功
            return new SimpleAuthenticationInfo(new User(), ShiroUtils.FINAL_PASSWORD_CIPHER_TEXT,
                    ByteSource.Util.bytes(ShiroUtils.FINAL_PASSWORD_SALT), getName());
        }


        String userNameInput = token.getUsername();
        User user = userService.getUserByNameOrEmailOrPhoneOrIdCard(userNameInput);
        if (user == null) {
            //账号（用户名或身份证或邮箱或手机号）不存在
            return null; //shiro抛出UnknownAccountException
        }

        String key = UserLoginResult.getUserLoginFailKey(userNameInput);
        if (redisTemplate.hasKey(key)) {
            int wrongTimes = Integer.parseInt(valueOps.get(key).toString());
            if (wrongTimes == UserLoginResult.DEFAULT_WRONG_TIMES) {
                //检查当前用户名是否处于冻结状态
                throw new LockedAccountException("用户被锁定！");
            }
        }


        ByteSource salt = ByteSource.Util.bytes(user.getUserSalt());
        //new SimpleAuthenticationInfo(返回的数据, 数据库中的密码, 盐, realm的名称)
        return new SimpleAuthenticationInfo(user, user.getUserPassword(), salt, getName());
    }
}
