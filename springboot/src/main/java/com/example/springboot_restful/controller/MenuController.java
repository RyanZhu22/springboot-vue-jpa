package com.example.springboot_restful.controller;

import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.entity.Menu;
import com.example.springboot_restful.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping
    public ResultBody saveOrUpdate(@RequestBody Menu menu) {
        menuService.saveOrUpdate(menu);
        return ResultBody.success();
    }

    @DeleteMapping("/{id}")
    public ResultBody removeById(@PathVariable Integer id) {
        menuService.removeById(id);
        return ResultBody.success();
    }

    @GetMapping("/{id}")
    public ResultBody findOne(@PathVariable Integer id) {
        Menu Menu = menuService.getById(id);
        return ResultBody.success(Menu);
    }

    @GetMapping("/page")
    public ResultBody findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize) {
        Map<String, Object> res = menuService.findPage(pageNum, pageSize);
        return ResultBody.success(res);
    }

    @GetMapping
    public ResultBody findAll() {
        return ResultBody.success(menuService.findAll());
    }
}
