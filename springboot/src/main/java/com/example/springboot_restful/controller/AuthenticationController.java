package com.example.springboot_restful.controller;

import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.dto.*;
import com.example.springboot_restful.service.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl service;

    @PostMapping("/register")
    public ResultBody register(@RequestBody RegisterRequest request) {
        return ResultBody.success(service.register(request));
    }

    @PostMapping("/login")
    public ResultBody login(@RequestBody LoginRequest request) {
        LoginResponse res = service.authenticate(request);
        return ResultBody.success(res);
    }

    @PostMapping("/authenticate")
    public ResultBody authenticate(@RequestBody LoginRequest request) {
        return ResultBody.success(service.authenticate(request));
    }

}
