package com.example.springboot_restful.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.entity.User;
import com.example.springboot_restful.service.UserService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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


    @GetMapping("/id")
    public ResultBody findOne(@RequestParam("id") Integer id) {
        Optional<User> user = userService.findById(id);
        return ResultBody.success(user);
    }

    @PostMapping("/update")
    public ResultBody update(User user) {
        User updateUser = userService.update(user);
        if (updateUser == null) {
            return ResultBody.error("500", "Internet Error");
        }

        return ResultBody.success();
    }

    @GetMapping
    public List<User> findAll() {
        List<User> all = userService.findAll();
        return all;
    }

    // RequestBody 将前端JSON数据转化成Java对象
    @PostMapping
    public ResultBody save(@RequestBody User user) {
        userService.saveUser(user);
        return ResultBody.success();
    }

    /**
     * 注册接口
     * @param user
     * @return
     */
    @PostMapping("/register")
    public ResultBody register(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        // hutool校验username是否未空
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return ResultBody.error("500", "username or password is null");
        }

        if (userService.findByUsername(user.getUsername()) == null) {
            userService.saveUser(user);
            return ResultBody.success();
        } else {
            return ResultBody.error("500", "Username already exists");
        }
    }

    // 真删除接口
    @DeleteMapping(value = "/{id}")
    public ResultBody deleteByIdT(@PathVariable("id") Integer id) {
        userService.deleteById(id);
        return ResultBody.success();
    }

    // 假删除接口
    @PostMapping(value = "/{id}")
    public ResultBody deleteByIdF(@PathVariable("id") Integer id) {
        userService.updateDeleted(id);
        return ResultBody.success();
    }

    // 分页查询接口
    // 接口路径： /user/page?pageNum=1&pageSize=10
    // LIMIT (pageNum - 1) * pageSize
    @GetMapping("/page")
    public Map<String, Object> findPage(@RequestParam(value = "username", required = false) String username,
                                        @RequestParam(value = "email", required = false) String email,
                                        @RequestParam(value = "address", required = false) String address,
                                        @RequestParam(value = "deleted", required = false) Integer deleted,
                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size,
                                        @RequestParam(value = "sort", defaultValue = "id") String sort,
                                        @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        Page<User> data = userService.selectPage(username, email, address, deleted, pageable);
        Long total = userService.selectTotal(username, email, address, deleted);
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        log.info(String.valueOf(pageable), data, total);
        return res;
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
