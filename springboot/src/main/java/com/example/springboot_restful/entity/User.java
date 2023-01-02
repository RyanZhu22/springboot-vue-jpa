package com.example.springboot_restful.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Excel(name = "ID", orderNum = "0")
    private Integer id;

    @Excel(name = "username",  width = 20)
    private String username;

    @Excel(name = "password", orderNum = "2")
    private String password;

    @Excel(name = "nickname", orderNum = "3")
    private String nickname;

    @Excel(name = "email", orderNum = "4")
    private String email;

    @Excel(name = "phone", orderNum = "5")
    private String phone;

    @Excel(name = "address", orderNum = "6")
    private String address;

    @Excel(name = "create_time", exportFormat = "yyyy-MM-dd",importFormat="yyyy-MM-dd", orderNum = "7")
    private LocalDateTime create_time;

    @Excel(name = "avatar_url", orderNum = "8")
    private String avatar_url;

    private Integer is_delete;
}
