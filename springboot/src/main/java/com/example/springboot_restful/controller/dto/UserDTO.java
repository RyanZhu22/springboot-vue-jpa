package com.example.springboot_restful.controller.dto;


import lombok.Data;

/**
 *  接收前端登录请求的参数
 *  nickname, avatarUrl作登陆后头像
 */
@Data
public class UserDTO {

    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
}
