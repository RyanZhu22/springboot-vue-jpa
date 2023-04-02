package com.example.springboot_restful.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {

    private Integer id;
    private String name;
    private String path;
    private String orders;
    private String icon;
    private String page;
    private String auth;
    private Integer pid;
    private Integer type;
    private Integer deleted;
    private Boolean hide;
    private LocalDateTime create_time;
    private LocalDateTime update_time;

    private List<Permission> children;
}
