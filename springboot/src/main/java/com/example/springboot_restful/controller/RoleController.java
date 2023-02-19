package com.example.springboot_restful.controller;

import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.entity.Role;
import com.example.springboot_restful.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @PostMapping
    public ResultBody saveOrUpdate(@RequestBody Role role) {
        roleService.saveOrUpdate(role);
        return ResultBody.success();
    }

    @DeleteMapping("/{id}")
    public ResultBody removeById(@PathVariable Integer id) {
        roleService.removeById(id);
        return ResultBody.success();
    }

    @GetMapping("/{id}")
    public ResultBody findOne(@PathVariable Integer id) {
        Role role = roleService.getById(id);
        return ResultBody.success(role);
    }

    @GetMapping("/page")
    public ResultBody findPage(@RequestParam(required = false) String role_name,
                               @RequestParam Integer pageNum,
                               @RequestParam Integer pageSize) {
        Map<String, Object> res = roleService.findPage(role_name, pageNum, pageSize);
        return ResultBody.success(res);
    }
}
