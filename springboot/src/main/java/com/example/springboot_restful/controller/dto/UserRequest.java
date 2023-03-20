package com.example.springboot_restful.controller.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private String name;
    private String emailCode;
    private String uid;
    private String newPassword;
}
