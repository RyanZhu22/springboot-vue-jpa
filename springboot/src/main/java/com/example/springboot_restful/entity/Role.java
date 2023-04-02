package com.example.springboot_restful.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private Integer id;

    private String name;
    private String flag;
    private Integer deleted;

    private List<Integer> permissionIds;
}
