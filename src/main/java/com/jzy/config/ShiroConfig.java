package com.jzy.config;

import com.jzy.manager.util.SessionUtils;
import com.jzy.manager.util.ShiroUtils;
import com.jzy.web.shiro.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName ShiroConfig
 * @description shiro配置类
 * @date 2019/11/13 18:39
 **/
@Configuration
public class ShiroConfig {
    /**
     * 自定义的加密算法，shiro自动的入参加密
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 使用md5 算法进行加密
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        //true加密用的hex编码，false用的base64编码
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        // 设置散列次数： 意为加密几次
        hashedCredentialsMatcher.setHashIterations(ShiroUtils.HASH_ITERATIONS);
        return hashedCredentialsMatcher;
    }


    /**
     * 配置拦截器
     *
     * 定义拦截URL权限，优先级从上到下
     * 1). anon  : 匿名访问，无需登录
     * 2). authc : 登录后才能访问
     * 3). logout: 登出
     * 4). roles : 角色过滤器
     * 5). user : 使用rememberMe可直接访问
     *
     * URL 匹配风格
     * 1). ?：匹配一个字符，如 /admin? 将匹配 /admin1，但不匹配 /admin 或 /admin/；
     * 2). *：匹配零个或多个字符串，如 /admin* 将匹配 /admin 或/admin123，但不匹配 /admin/1；
     * 2). **：匹配路径中的零个或多个路径，如 /admin/** 将匹配 /admin/a 或 /admin/a/b
     *
     * 配置身份验证成功，失败的跳转路径
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //添加shrio内置过滤器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        /*=======================================================*/
        /**
         * 静态资源匿名访问
         */
        filterChainDefinitionMap.put("/custom/**", "anon");
        filterChainDefinitionMap.put("/plugins/**", "anon");
        /**
         * 允许未登录下的访问路径
         */
        filterChainDefinitionMap.put("/comingSoon", "anon"); //comingSoon
        filterChainDefinitionMap.put("/400", "anon"); //400
        filterChainDefinitionMap.put("/404", "anon"); //404
        filterChainDefinitionMap.put("/500", "anon"); //500
        filterChainDefinitionMap.put("/formRepeatSubmit", "anon"); //表单重复提交页面
        filterChainDefinitionMap.put("/noPermissions","anon"); //用户无权限提示页面

        filterChainDefinitionMap.put("/kaptcha","anon"); //图形验证码
        filterChainDefinitionMap.put("/testIp","anon"); //可疑ip检测
//        filterChainDefinitionMap.put("/sliderTest","anon"); //滑块验证iframe
        filterChainDefinitionMap.put("/", "anon"); //登录页面
        filterChainDefinitionMap.put("/login", "anon"); //登录页面
        filterChainDefinitionMap.put("/loginTest", "anon"); //登录请求ajax交互
        filterChainDefinitionMap.put("/forget", "anon"); //忘记密码页面
        filterChainDefinitionMap.put("/sendVerifyCodeToEmail", "anon"); //发送邮箱验证码请求
        filterChainDefinitionMap.put("/emailVerifyCodeTest", "anon"); //测试邮箱验证码正确与否请求
        filterChainDefinitionMap.put("/resetPassword", "anon"); //忘记密码后重置用户密码
        filterChainDefinitionMap.put("/loginByEmailCode", "anon"); //通过邮箱验证码登录页面
        filterChainDefinitionMap.put("/loginTestByEmailCode", "anon"); //通过邮箱验证码是否成功登录ajax请求
        filterChainDefinitionMap.put("/guestLogin", "anon"); //游客登录页面
        filterChainDefinitionMap.put("/resetLoginQuestion", "anon"); //游客登录页面换问题请求
        filterChainDefinitionMap.put("/loginTestByQuestion", "anon"); //通过问题登入请求验证

        /*=======================================================*/
        /**
         * 授权拦截
         */
        Map<String, String> permissions = new LinkedHashMap<>();
        permissions.put("/user/admin/import*", "perms[user:admin:import]"); //用户信息导入，管理员和学管和助教长有
        permissions.put("/user/showIcon", "perms[user:showIcon]"); //游客只有获取头像接口的权限
        permissions.put("/user/admin/**", "perms[user:admin]"); //用户管理，管理员和学管有
        permissions.put("/user/message/many/**", "perms[user:message:many]"); //批量发消息，管理员和学管和助教长有
        permissions.put("/user/message/all/**", "perms[user:message:all]"); //发全体消息，管理员和学管有
        permissions.put("/user/**", "perms[user:basic]"); //基本用户信息的操作，除游客外都有权限
        permissions.put("/permission/admin/**", "perms[permission:admin]"); //角色权限的权限，管理员才有
        permissions.put("/assistant/admin/**", "perms[assistant:admin]"); //管理助教的权限，助教长以上级别才有
        permissions.put("/teacher/admin/**", "perms[teacher:admin]"); //管理教师的权限，助教长以上级别才有
        permissions.put("/class/admin/update*", "perms[class:admin:update]"); //编辑班级的权限，助教长以上级别才有
        permissions.put("/class/admin/insert*", "perms[class:admin:insert]"); //添加班级的权限，助教长以上级别才有
        permissions.put("/class/admin/delete*", "perms[class:admin:delete]"); //删除班级的权限，助教长以上级别才有
        permissions.put("/class/admin/import*", "perms[class:admin:import]"); //excel导入班级的权限，助教长以上级别才有
        permissions.put("/studentAndClass/admin/update*", "perms[studentAndClass:admin:update]"); //编辑学员上课信息的权限，助教长以上级别才有
        permissions.put("/studentAndClass/admin/insert*", "perms[studentAndClass:admin:insert]"); //添加学员上课信息的权限，助教长以上级别才有
        permissions.put("/studentAndClass/admin/delete*", "perms[studentAndClass:admin:delete]"); //删除学员上课信息的权限，助教长以上级别才有
        permissions.put("/student/admin/update*", "perms[student:admin:update]"); //编辑学员个人信息的权限，助教长以上级别才有
        permissions.put("/student/admin/insert*", "perms[student:admin:insert]"); //添加学员个人信息的权限，助教长以上级别才有
        permissions.put("/student/admin/delete*", "perms[student:admin:delete]"); //删除学员个人信息的权限，助教长以上级别才有
        permissions.put("/student/admin/import*", "perms[student:admin:import]"); //excel导入学员个人信息的权限，助教长以上级别才有
        permissions.put("/missLessonStudent/admin/update*", "perms[missLessonStudent:admin:update]"); //编辑补课学员信息的权限，助教长以上级别才有
        permissions.put("/missLessonStudent/admin/insert*", "perms[missLessonStudent:admin:insert]"); //添加补课学员信息的权限，助教长以上级别才有
        permissions.put("/missLessonStudent/admin/delete*", "perms[missLessonStudent:admin:delete]"); //删除补课学员信息的权限，助教长以上级别才有
        permissions.put("/question/admin/update*", "perms[question:admin:update]"); //编辑登录问题的权限，助教长以上级别才有
        permissions.put("/question/admin/insert*", "perms[question:admin:insert]"); //添加登录问题的权限，助教以上级别才有
        permissions.put("/question/admin/delete*", "perms[question:admin:delete]"); //删除登录问题的权限，助教长以上级别才有
        permissions.put("/usefulInformation/admin/update*", "perms[usefulInformation:admin:update]"); //编辑常用信息的权限，助教长以上级别才有
        permissions.put("/usefulInformation/admin/insert*", "perms[usefulInformation:admin:insert]"); //添加常用信息的权限，助教长以上级别才有
        permissions.put("/usefulInformation/admin/delete*", "perms[usefulInformation:admin:delete]"); //删除常用信息的权限，助教长以上级别才有

        permissions.put("/toolbox/assistant/**", "perms[toolbox:assistant]"); //百宝箱助教区的权限，教师助教以上级别才有
        permissions.put("/toolbox/assistantAdministrator/**", "perms[toolbox:assistantAdministrator]"); //百宝箱学管区的权限，助教长以上级别才有

        permissions.put("/system/**", "perms[system:admin]"); //推送公告等系统设置，只能管理员

        filterChainDefinitionMap.putAll(permissions);

        /*=======================================================*/
        filterChainDefinitionMap.put("/logout", "logout");  // 用户退出，只需配置logout即可实现该功能

        /*=======================================================*/
//        filterChainDefinitionMap.put("/**", "user");   //rememberMe后可以访问的路径

        /*=======================================================*/
        filterChainDefinitionMap.put("/**", "authc");       // 其他路径均需要身份认证，一般位于最下面，优先级最低

        /*=======================================================*/
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilterFactoryBean.setLoginUrl("/login");       // 登录的路径
        shiroFilterFactoryBean.setSuccessUrl("/index"); // 登录成功后跳转的路径
        shiroFilterFactoryBean.setUnauthorizedUrl("/noPermissions");  // 未授权后跳转的路径
        return shiroFilterFactoryBean;
    }

    /**
     * session管理器
     *
     * @return
     */
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 设置session过期时间2*3600s
        sessionManager.setGlobalSessionTimeout(SessionUtils.SESSION_EXPIRE_TIME);
        //是否删除过期session
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }

    /**
     * cookie对象;
     * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //System.out.println("ShiroConfiguration.rememberMeCookie()");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间7天 ,单位秒;-->
        simpleCookie.setMaxAge(ShiroUtils.SHIRO_REMEMBER_ME_COOKIE_TIME);
        return simpleCookie;
    }

    /**
     * cookie管理对象;
     * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    /**
     * shiro核心安全管理器
     *
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
//        securityManager.setRememberMeManager(rememberMeManager());
        securityManager.setSessionManager(sessionManager());
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    @Bean
    public UserRealm userRealm(){
        UserRealm userRealm=new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }
}
