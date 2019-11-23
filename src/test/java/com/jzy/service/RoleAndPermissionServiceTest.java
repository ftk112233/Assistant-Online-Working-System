package com.jzy.service;

import com.jzy.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class RoleAndPermissionServiceTest extends BaseTest {
    @Autowired
    private RoleAndPermissionService roleAndPermissionService;

    @Test
    public void listPermsByRole() {
        System.out.println(roleAndPermissionService.listPermsByRole("学管"));
    }
}