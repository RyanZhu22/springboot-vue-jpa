package com.example.springboot_restful.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_menu")
public class Menu {

    private Integer id;
    private Integer pid;
    private String menu_name;
    private String menu_path;
    private String icon;
    private String description;
    private List<Menu> children;
}
