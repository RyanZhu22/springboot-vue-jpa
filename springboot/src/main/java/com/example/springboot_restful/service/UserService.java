package com.example.springboot_restful.service;

import cn.hutool.core.util.StrUtil;
import com.example.springboot_restful.common.JsonResult;
import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.common.error.CommonEnum;
import com.example.springboot_restful.common.handler.MsgException;
import com.example.springboot_restful.controller.dto.UserDTO;
import com.example.springboot_restful.entity.User;
import com.example.springboot_restful.mapper.UserMapper;
import com.example.springboot_restful.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public UserDTO login(UserDTO userDTO) throws ResultBody {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        User one = userMapper.login(username, password);
        // 查询数据库 验证username and password是否一致
        if (one != null && one.getUsername().equals(username) && userDTO.getPassword().equals(password)) {
            // 设置nickname, avatarUrl, token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setNickname(one.getNickname());
            userDTO.setAvatarUrl(one.getAvatar_url());
            userDTO.setToken(token);
            return userDTO;
        } else {
            throw ResultBody.error("500", "Username or password is incorrect");
        }
    }

    public ResultBody save(User user) throws ResultBody {
        try {
            User one = userMapper.findByUsername(user.getUsername());
            if (one == null) {
                if (user.getId() == null) {
                    userMapper.insert(user);
                } else {
                    userMapper.update(user);
                }
                return ResultBody.success(user);
            } else { // 查找到相同用户名
                //抛出自定义异常
                throw new MsgException("500", "Username already exists");
            }
        } catch (Exception e) {
            throw ResultBody.error(CommonEnum.INTERNAL_SERVER_ERROR);
        }
    }

    // 批量删除
    public int saveBatch(List<User> list) {
        try {
            return userMapper.saveBatch(list);
        } catch (Exception e) {
            return -1;
        }
    }

}