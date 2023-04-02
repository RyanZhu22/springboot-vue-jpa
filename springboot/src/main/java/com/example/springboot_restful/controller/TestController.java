package com.example.springboot_restful.controller;

import com.example.springboot_restful.common.ResultBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/user")
    public ResultBody helloUser() {
        return ResultBody.success("Hello User");
    }

    @GetMapping("/admin")
    public ResultBody helloAdmin() {
        return ResultBody.success("Hello Admin");
    }
}
