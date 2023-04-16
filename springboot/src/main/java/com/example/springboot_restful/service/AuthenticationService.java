package com.example.springboot_restful.service;

import com.example.springboot_restful.dto.LoginRequest;
import com.example.springboot_restful.dto.LoginResponse;
import com.example.springboot_restful.dto.RegisterRequest;
import com.example.springboot_restful.dto.RegisterResponse;
import com.example.springboot_restful.entity.User;

public interface AuthenticationService {

    void register(RegisterRequest request);

    LoginResponse authenticate(LoginRequest request);

    void saveUserToken(User user, String jwtToken);

    void revokeAllUserTokens(User user);
}
