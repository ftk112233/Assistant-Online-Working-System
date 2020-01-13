package com.jzy.other;

import com.jzy.BaseTest;
import com.jzy.model.CampusEnum;
import com.jzy.model.RoleEnum;
import com.jzy.model.entity.User;
import com.jzy.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName MyTest
 * @Author JinZhiyun
 * @Description //TODO
 * @Date 2019/12/11 21:30
 * @Version 1.0
 **/
public class MyTest extends BaseTest {
    @Autowired
    private UserService userService;

    @Test
    public void setCampusAccounts(){
        for (CampusEnum campusEnum : CampusEnum.values()) {
            String campus=campusEnum.toString().toLowerCase();
            User user=new User();
            user.setUserName(campus);
            user.setDefaultUserPasswordAndSalt();
            user.setUserRealName(campusEnum.getName()+"校区公共账号");
            user.setUserRole(RoleEnum.ASSISTANT_MANAGER.getRole());
            user.setNewDefaultUserIcon();
            user.setDefaultUserIcon();
            user.setUserRemark(campusEnum.getName()+"校区公共账号");
            userService.insertUser(user);
        }
    }

    public static void main(String[] args) {
        String st=null;
        if (st!=null) {
            switch (st) {
                case "aaa":
                    System.out.println(st);
                    break;

            }
        }
    }
}
