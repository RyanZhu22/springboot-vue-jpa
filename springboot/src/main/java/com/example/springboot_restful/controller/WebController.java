package com.example.springboot_restful.controller;

import cn.hutool.core.util.StrUtil;
import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.controller.dto.LoginDTO;
import com.example.springboot_restful.controller.dto.UserDTO;
import com.example.springboot_restful.controller.dto.UserRequest;
import com.example.springboot_restful.service.RolePermissionService;
import com.example.springboot_restful.service.RoleService;
import com.example.springboot_restful.service.UserService;
import com.example.springboot_restful.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "List of unprivileged interfaces", description = "APIs for No login status")
@RestController
public class WebController {

    @Autowired
    private UserService userService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private RoleService roleService;

    @Operation(summary = "User Login")
    @PostMapping("/login")
    public ResultBody login(@RequestBody UserRequest user) {
        LoginDTO res = userService.login(user);
        return ResultBody.success(res);
    }

    @Operation(summary = "User Register")
    @PostMapping("/register")
    public ResultBody register(@RequestBody UserRequest user) {
        userService.register(user);
        return ResultBody.success();
    }
}
