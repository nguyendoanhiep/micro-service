package com.example.authservice.controller;

import com.example.authservice.exception.ApiResponse;
import com.example.authservice.dto.request.FormLogin;
import com.example.authservice.dto.request.FormRegister;
import com.example.authservice.service.AuthService;
import com.example.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody FormRegister formRegister) {
        return ApiResponse.SUCCESS(authService.register(formRegister));
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody FormLogin formLogin) {
        return ApiResponse.SUCCESS(authService.login(formLogin));

    }
}
