package com.example.authservice.controller;

import com.example.authservice.dto.ApiResponse;
import com.example.authservice.dto.request.FormLogin;
import com.example.authservice.dto.request.FormRegister;
import com.example.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody FormRegister formRegister) {
        return ApiResponse.builder()
                .code(200)
                .message("ok")
                .result(userService.register(formRegister))
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody FormLogin formLogin) {
        return ApiResponse.builder()
                .code(200)
                .message("ok")
                .result(userService.login(formLogin))
                .build();
    }
}
