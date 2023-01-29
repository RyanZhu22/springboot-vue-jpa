package com.example.springboot_restful.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springboot_restful.entity.User;
import com.example.springboot_restful.mapper.UserMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

@Component
public class TokenUtils {

    private static UserMapper staticUserMapper;

    @Resource
    private UserMapper userMapper;

    @PostConstruct
    public void setUserService() {
        staticUserMapper = userMapper;
    }


    /**
     * 生成Token
     * @return
     */
    public static String genToken(String userId, String sign) {
        return JWT.create().withAudience(userId)  // save user id into token as Payload
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // token expires after 2h
                .sign(Algorithm.HMAC256(sign)); // use password as the key for token
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return user对象
     */
    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserMapper.findByUserId(Integer.valueOf(userId));
            }
        }  catch (Exception e) {
            return null;
        }
        return null;
    }
}
