package com.example.springboot_restful.controller;

import com.example.springboot_restful.entity.User;
import com.example.springboot_restful.mapper.UserMapper;
import com.example.springboot_restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> findAll() {
        List<User> all = userMapper.findAll();
        return all;
    }

    @PostMapping
    public Integer save(@RequestBody User user) {
        // 新增或更新
        return userService.save(user);
    }

    // 真删除接口
    @DeleteMapping(value = "/{id}")
    public Integer deleteByIdT(@PathVariable("id") Integer id) {
        return userMapper.deleteByIdT(id);
    }

    // 假删除接口
    @PostMapping(value = "/{id}")
    public Integer deleteByIdF(@PathVariable("id") Integer id) {
        return userMapper.deleteByIdF(id);
    }

    // 分页查询接口
    // 接口路径： /user/page?pageNum=1&pageSize=10
    // LIMIT (pageNum - 1) * pageSize
    @GetMapping("/page")
    public Map<String, Object> findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(defaultValue = "") String username,
                                        @RequestParam(defaultValue = "") String email,
                                        @RequestParam(defaultValue = "") String address,
                                        @RequestParam("is_delete") Integer is_delete) {
        pageNum = (pageNum - 1) * pageSize;
        List<User> data = userMapper.selectPage(pageNum, pageSize, username, email, address, is_delete);
        Integer total = userMapper.selectTotal(username, email, address, is_delete);
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return res;
    }

}
