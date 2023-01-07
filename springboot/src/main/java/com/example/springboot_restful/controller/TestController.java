package com.example.springboot_restful.controller;

import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.common.handler.MsgException;
import com.example.springboot_restful.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    //测试自定义异常
    @PostMapping("/test")
    public boolean add(User user) {
        System.out.println("开始新增....");
        if (user.getUsername() == null) {
            //抛出自定义异常
            throw new MsgException("-1", "用户姓名不能为空！");
        }
        return true;
    }

    //测试空指针异常
    @DeleteMapping("/test/{id}")
    public boolean delete(@PathVariable("id") Integer id) {
        System.out.println("开始删除....");
        //抛出空指针
        String str = null;
        str.equals("123");
        return true;
    }

    //测试其他异常
    @PutMapping("/test/{id}")
    public boolean update(@PathVariable("id") Integer id, User user) {
        System.out.println("开始更新....");
        //程序自己出错
        Integer.parseInt("abc123");
        return true;
    }

    @GetMapping("/test/{id}")
    public ResultBody query(@PathVariable("id") Integer id) {
        System.out.println("开始查询....");
        return ResultBody.success(null);
    }

}
