package com.example.springboot_restful.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;

    private String username;

    private String password;

    private String nickname;

    private String email;

    private String phone;

    private String address;

    private LocalDateTime create_time;

    private String avatar_url;
}