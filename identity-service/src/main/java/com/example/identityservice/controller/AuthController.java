package com.example.identityservice.controller;

import com.example.identityservice.dto.request.IntrospectRequest;
import com.example.identityservice.dto.ApiResponse;
import com.example.identityservice.dto.request.FormLogin;
import com.example.identityservice.dto.request.FormRegister;
import com.example.identityservice.service.AuthService;
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
    @PostMapping("/introspect")
    public ApiResponse<?> introspect(@RequestBody IntrospectRequest request) {
        return ApiResponse.SUCCESS(authService.introspect(request));
    }
}
