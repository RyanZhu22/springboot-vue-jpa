package com.example.springboot_restful.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.example.springboot_restful.common.Constants;
import com.example.springboot_restful.dto.LoginResponse;
import com.example.springboot_restful.dto.UserRequest;
import com.example.springboot_restful.entity.Permission;
import com.example.springboot_restful.entity.Role;
import com.example.springboot_restful.entity.User;
import com.example.springboot_restful.exception.ServiceException;
import com.example.springboot_restful.repository.UserRepository;
import com.example.springboot_restful.service.PermissionService;
import com.example.springboot_restful.service.RolePermissionService;
import com.example.springboot_restful.service.RoleService;
import com.example.springboot_restful.service.UserService;
import com.example.springboot_restful.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RolePermissionService rolePermissionService;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    /**
     * 登录业务逻辑
     *
     * @param user
     * @return
     */
    @Override
    public LoginResponse getUserMenusAndAuths(User user) {
        // 查询用户的菜单树（2层）
        String flag = user.getRole().name();
        List<Permission> all = getPermissions(flag); // 水平
        // 页面菜单权限
        List<Permission> menus = getTreePermissions(all); // 树形
        // 页面按钮权限
        List<Permission> auths = all.stream().filter(permission -> permission.getType() == 3)
            .collect(Collectors.toList());
        // 返回登录数据
        return LoginResponse.builder().user(user).menus(menus).auths(auths).build();
    }


    @Override
    public void logout(String uid) {
        // current session logout
//        StpUtil.logout(uid);
        log.info("User {} exit successful", uid);
    }

    // TODO Why check email in passwordReset and check uid in passwordChange
    // The user doesn't know their current password, so we use email and check email
    @Override
    public String passwordReset(UserRequest user) {
        // validate user through check email
        String email = user.getEmail();
        User dbUser = userRepository.findByEmail(email).orElseThrow();
        // TODO why we should validate email validation code

        // new password
        String newPwd = "123";
        // set new password to dbUser
//        .setPassword(BCrypt.hashpw(newPwd));
        // save into database
        try {
            update(dbUser);
        } catch (Exception e) {
            throw new RuntimeException("Register Failed", e);
        }
        return newPwd;
    }

    // TODO Why check email in passwordReset and check uid in passwordChange
    // The user doesn't know their current password
    @Override
    public void passwordChange(UserRequest user) {
        // validate user through uid
        User dbUser = userRepository.findByUid(user.getUid());
        if (dbUser == null) {
            throw new ServiceException("The user is not found");
        }
//        boolean checkPwd = BCrypt.checkpw(user.getPassword(), dbUser.getPassword());
//        if (!checkPwd) {
//            throw new ServiceException("The old password is incorrect");
//        }
        // get the new password
        String newPwd = user.getNewPassword();
        // encrypted password
//        dbUser.setPassword(BCrypt.hashpw(newPwd));
        update(dbUser); // update database
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User saveUser(User user) {
        // 校验username是否唯一
        Optional<User> dbUser = userRepository.findByUsername(user.getUsername());
        if (dbUser.isPresent()) {
            throw new ServiceException("用户已存在");
        }
        // 校验是否有name
        if (StrUtil.isBlank(user.getName())) {
            // 设置昵称
            user.setName("system_user" + RandomUtil.randomString(6));
        }
        // 校验是否有password
        if (StrUtil.isBlank(user.getPassword())) {
            // 设置默认密码
            user.setPassword(Constants.DEFAULT_PASSWORD);
        }
        // 加密用户密码
//        user.setPassword(BCrypt.hashpw(user.getPassword())); // BCrypt hash加密
        // 设置uid 唯一标识
        user.setUid(IdUtil.fastSimpleUUID());
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Register Failed", e);
        }
        return user;
    }

    /**
     * 校验邮箱
     *
     * @param emailKey
     * @param emailCode
     */
    @Override
    public void validateEmail(String emailKey, String emailCode) {
        Integer code = RedisUtils.getCacheObject(emailKey);
        if (code == null) {
            throw new ServiceException("Verification code is invalid");
        }
        if (!emailCode.equals(code.toString())) {
            throw new ServiceException("验证码错误");
        }
        RedisUtils.deleteObject(emailKey); // 清除缓存
    }

    private List<Permission> getPermissions(String roleFlag) {
        // 通过唯一标识Flag获取role
        Role role = roleService.findByFlag(roleFlag);
        // 通过roleId获取对应的permissions
        List<Integer> rolePermissions = rolePermissionService.findPermissionIdsByRoleId(role.getId());
        List<Permission> permissionList = permissionService.findAll();
        List<Permission> all = new ArrayList<>(); // 水平的菜单树
        for (Integer permissionId : rolePermissions) {
            permissionList.stream().filter(permission -> permission.getId().equals(permissionId))
                .findFirst().ifPresent(all::add);
        }
        return all;
    }

    // 获取角色对应的菜单树
    private List<Permission> getTreePermissions(List<Permission> all) {
        // 菜单树 1级 -> 2级
        List<Permission> parentList = all.stream().filter(permission -> permission.getType() == 1)
            .collect(Collectors.toList());// type == 1是目录
        for (Permission permission : parentList) {
            Integer pid = permission.getId();
            List<Permission> level2List = all.stream().filter(permission1 -> pid.equals(permission1.getPid()))
                .collect(Collectors.toList());// 2级菜单
            permission.setChildren(level2List);
        }
        return parentList;
    }

    public List<User> saveBatch(List<User> users) {
        return userRepository.saveAll(users);
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Long count() {
        return userRepository.count();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }


    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateDeleted(Integer id) {
        userRepository.updateDeleted(id);
    }


    @Override
    public User update(User user) {
        User existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.updateFields(user);
        return userRepository.save(existingUser);
    }

    @Override
    public User partialUpdate(User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User existingUser = optionalUser.get();
        existingUser.updateFields(user);
        return userRepository.save(existingUser);
    }

    @Override
    public void updateAvatar(User user) {
        Optional<User> opt = userRepository.findById(user.getId());
        if (opt.isEmpty()) {
            throw new ServiceException("The user is not found");
        }
        opt.get().setAvatar(user.getAvatar());
        userRepository.save(opt.get());
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public List<User> findByConditions(String username, String email, String address) {
        return userRepository.findByConditions(username, email, address);
    }

    @Override
    public Page<User> findByConditionsWithPagination(Pageable pageable, String username, String email, String address) {
        return userRepository.findByConditionsWithPagination(pageable, username, email, address);
    }


    @Override
    public Long selectTotal(String username, String email, String address, Integer deleted) {
        return userRepository.countByConditions(username, email, address, deleted);
    }
}