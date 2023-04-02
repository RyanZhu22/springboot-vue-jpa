package com.example.springboot_restful.controller;

import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.controller.dto.LoginDTO;
import com.example.springboot_restful.controller.dto.UserRequest;
import com.example.springboot_restful.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "List of unprivileged interfaces", description = "APIs for No login status")
@RestController
public class WebController {

    @Autowired
    private UserService userService;


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

    @Operation(summary = "User Logout")
    @GetMapping("/logout/{uid}")
    public ResultBody logout(@PathVariable String uid) {
        userService.logout(uid);
        return ResultBody.success();
    }

    @Operation(summary = "Password Reset")
    @PostMapping("/password/reset/")
    public ResultBody passwordReset(@RequestBody UserRequest user) {
        String newPwd = userService.passwordReset(user);
        return ResultBody.success(newPwd);
    }

//    @Operation(summary = "Password Change")
//    @PostMapping("/password/change/")
//    public ResultBody passwordChange(@RequestBody UserRequest user) {
//        userService.passwordChange(user);
//        return ResultBody.success();
//    }

}
