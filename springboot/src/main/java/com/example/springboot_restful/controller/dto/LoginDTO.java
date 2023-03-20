package com.example.springboot_restful.controller.dto;

import com.example.springboot_restful.entity.Permission;
import com.example.springboot_restful.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO implements Serializable {

    private User user;
    private String token;
    private List<Permission> menus;
    private List<Permission> auths;
}
