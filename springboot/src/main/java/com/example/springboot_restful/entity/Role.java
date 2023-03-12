package com.example.springboot_restful.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
public class Role {
    private Integer id;

    private String name;
    private String flag;
    private Integer deleted;

    @TableField(exist = false)
    private List<Integer> permissionIds;
}
