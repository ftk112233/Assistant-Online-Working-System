package com.jzy.model.entity;

import com.jzy.manager.util.ShiroUtils;
import com.jzy.model.RoleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName User
 * @description 用户实体类
 * @date 2019/11/13 16:11
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class User extends BaseEntity {
    private static final long serialVersionUID = 3383159835719833836L;

    /**
     * 用户的所有角色
     */
    public static List<String> ROLES = RoleEnum.getRolesList();

    /**
     * 用户默认头像
     */
    public static final String USER_ICON_DEFAULT = "user_icon_default.jpg";

    /**
     * 用户的工号，即助教的工号，唯一，长度不超过32可以为空
     */
    private String userWorkId;

    /**
     * 用户的身份证，可以为空
     */
    private String userIdCard;

    /**
     * 用户名，唯一，可以自定义，6~20位(数字、字母、下划线)以字母开头
     */
    private String userName;

    /**
     * 用户密码，非空，6~20个字符(数字、字母、下划线)
     */
    private String userPassword;

    /**
     * 用户密码加密所用的盐
     */
    private String userSalt;

    /**
     * 用户的真实姓名，非空，不超过50个字符
     */
    private String userRealName;

    /**
     * 用户身份,"管理员","学管", "助教长", "助教", "教师", "游客"
     */
    private String userRole;

    /**
     * 用户的头像存储的地址路径，空或者长度小于等于100
     */
    private String userIcon;

    /**
     * 用户邮箱，唯一，空或者长度于等于100
     */
    private String userEmail;

    /**
     * 用户手机，唯一，空或者11位数字
     */
    private String userPhone;

    /**
     * 用户备注，空或者长度小于等于500
     */
    private String userRemark;

    /**
     * 为新插入或修改过的user配置默认的密码和盐
     */
    public void setDefaultUserPasswordAndSalt(){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        this.setUserSalt(uuid);
        if (StringUtils.isEmpty(this.getUserPassword())) {
            //若密码为空
            if (!StringUtils.isEmpty(this.getUserIdCard())) {
                //若身份证不为空,默认密码设为身份证
                this.setUserPassword(ShiroUtils.encryptUserPassword(this.getUserIdCard(), uuid));
            } else if (!StringUtils.isEmpty(this.getUserPhone())) {
                //若手机号不为空,默认密码设为手机号
                this.setUserPassword(ShiroUtils.encryptUserPassword(this.getUserPhone(), uuid));
            } else {
                //否则设置用户名为默认密码
                this.setUserPassword(ShiroUtils.encryptUserPassword(this.getUserName(), uuid));
            }
        } else {
            //若密码不为空
            this.setUserPassword(ShiroUtils.encryptUserPassword(this.getUserPassword(), uuid));
        }
    }

    /**
     * 为新插入或修改过的user配置默认的头像。如果当前user对象icon字段为空才设置，这是与setDefaultUserIcon()的区别。
     */
    public void setNewDefaultUserIcon(){
        if (StringUtils.isEmpty(this.getUserIcon())) {
            //用户头像为空
            setDefaultUserIcon();
        }
    }

    /**
     * 把当前用户头像设为默认头像
     */
    public void setDefaultUserIcon(){
        this.setUserIcon(USER_ICON_DEFAULT);
    }

    /**
     * 把当前用户头像设为默认头像
     */
    public boolean isDefaultUserIcon(){
        return USER_ICON_DEFAULT.equals(this.userIcon);
    }
}
