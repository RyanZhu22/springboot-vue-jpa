package com.example.springboot_restful.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.entity.Role;
import com.example.springboot_restful.entity.User;
import com.example.springboot_restful.model.page.UserPage;
import com.example.springboot_restful.model.page.UserPage;
import com.example.springboot_restful.service.UserService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResultBody count() {
        Long all = userService.count();
        return ResultBody.success(all);
    }

    @GetMapping("/{id}")
    public ResultBody findOne(@PathVariable(value = "id") Integer id) {
        Optional<User> user = userService.findById(id);
        return ResultBody.success(user);
    }

    @PostMapping("/update")
    public ResultBody update(@RequestBody User user) {
        User updateUser = userService.update(user);
        return ResultBody.success(updateUser);
    }

    @PostMapping("/update/avatar")
    public ResultBody updateAvatar(@RequestBody User user) {
        userService.updateAvatar(user);
        return ResultBody.success();
    }

    @PostMapping
    public ResultBody save(@RequestBody User user) {
        userService.saveUser(user);
        return ResultBody.success();
    }

    // 删除接口
    @PostMapping("/{id}")
    public ResultBody updateDeleted(@PathVariable("id") Integer id) {
        userService.updateDeleted(id);
        return ResultBody.success();
    }

    // 分页查询接口
    @GetMapping("/page")
    public ResultBody findPage(UserPage userPage) {
        PageRequest pageable = PageRequest.of(userPage.getPage() - 1, userPage.getSize());
        Page<User> data = userService.findByConditionsWithPagination(
            pageable, userPage.getUsername(), userPage.getEmail(), userPage.getAddress());
        return ResultBody.success(data);
    }

    // 导出接口 Excel poi工具
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询数据
        List<User> list = userService.findAll();
        // 在内存操作，写出浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 自定义标题别名
        writer.setOnlyAlias(true);
        writer.addHeaderAlias("username", "username");
        writer.addHeaderAlias("password", "password");
        writer.addHeaderAlias("nickname", "nickname");
        writer.addHeaderAlias("email", "email");
        writer.addHeaderAlias("phone", "phone");
        writer.addHeaderAlias("address", "address");
        writer.addHeaderAlias("createTime", "createTime");
        writer.addHeaderAlias("avatarUrl", "avatarUrl");

        // 一次性写出list内对象到excel, 使用默认样式，强制输出标题
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("User-Information", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);
        outputStream.close();
        writer.close();
    }

    /**
     * excel 导入
     *
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public ResultBody imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list = reader.readAll(User.class);
        List<User> res = userService.saveBatch(list);
        return ResultBody.success();
    }

}
