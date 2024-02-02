package com.leo.safetytalk.controller;

import com.leo.safetytalk.entity.User;
import com.leo.safetytalk.service.UserService;
import com.leo.safetytalk.util.ApiResponse;
import com.leo.safetytalk.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ApiResponse login(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 返回适当的响应
            return ApiResponse.ok().message("login success");
        } catch (BadCredentialsException e) {
            // 处理认证失败
            return ApiResponse.error().message("login failed");
        }
    }

    @PostMapping("/register")
    public ApiResponse register(@RequestBody User user) {

        if (!isUserInformationValid(user)) {
            return ApiResponse.error().message("username or password invalid");
        }

        if (isUsernameExist(user.getUsername())) {
            return ApiResponse.error().message("username already registered");
        }

        // register user
        String password = user.getPassword();
        boolean passwordSecure = ValidationUtil.isPasswordSecure(password);
        if (!passwordSecure) {
            return ApiResponse.error().message("Password does not meet security requirements");
        }
        user.setPassword(passwordEncoder.encode(password));
        userService.save(user);

        return ApiResponse.ok().message("User registered successfully");
    }

    private boolean isUsernameExist(String username) {
        return userService.isUserExistByUsername(username);
    }

    private boolean isUserInformationValid(User user) {
        // Validate username length
        if (!ValidationUtil.isValidUsername(user.getUsername())) {
            return false;
        }

        // Validate password security
        if (!ValidationUtil.isPasswordSecure(user.getPassword())) {
            return false;
        }

        return true;
    }

}