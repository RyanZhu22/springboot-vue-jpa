package com.example.springboot_restful.service.impl;

import com.example.springboot_restful.dto.LoginRequest;
import com.example.springboot_restful.dto.LoginResponse;
import com.example.springboot_restful.dto.RegisterResponse;
import com.example.springboot_restful.dto.RegisterRequest;
import com.example.springboot_restful.entity.Token;
import com.example.springboot_restful.entity.User;
import com.example.springboot_restful.enums.Role;
import com.example.springboot_restful.enums.TokenType;
import com.example.springboot_restful.exception.ServiceException;
import com.example.springboot_restful.repository.TokenRepository;
import com.example.springboot_restful.service.AuthenticationService;
import com.example.springboot_restful.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService service;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public void register(RegisterRequest request) {
        if (service.findByUsername(request.getUsername()).isPresent()) {
            throw new ServiceException("500", "The Username already exists");
        }

        if (!request.getPassword().equals(request.getConfirmPwd())) {
            throw new ServiceException("500", "password is not match");
        }
        var user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .email(request.getEmail())
            .name(request.getName())
            .role(Role.USER)
            .deleted(0)
            .build();
        User savedUser = service.save(user);
//        var jwtToken = jwtService.generateToken(user);
//        saveUserToken(savedUser, jwtToken);
//        return RegisterResponse.builder().token(jwtToken).build();
    }

    @Override
    public LoginResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        var user = service.findByUsername(request.getUsername())
            .orElseThrow();
        user.setUpdate_time(LocalDateTime.now()); // set update_time
        service.save(user); // save into db
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken); // token
        LoginResponse response = service.getUserMenusAndAuths(user);
        return LoginResponse.builder()
            .user(response.getUser())
            .token(jwtToken)
            .menus(response.getMenus())
            .auths(response.getAuths())
            .build();
    }

    @Override
    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
        tokenRepository.save(token);
    }

    @Override
    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
