package com.example.springboot_restful.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_permission")
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
    private LocalDateTime create_time;
    private LocalDateTime update_time;

    @TableField(exist = false)
    private List<Permission> children;
}
