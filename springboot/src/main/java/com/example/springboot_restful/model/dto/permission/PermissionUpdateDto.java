package com.example.springboot_restful.model.dto.permission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PermissionUpdateDto {

    private String name;

    private String path;

    private String orders;

    private String icon;

    private String page;

    private String auth;

    private Integer pid;

    private Integer type;

    private Boolean deleted;

    private Boolean hide;
}
