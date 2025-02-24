package com.example.authservice.service;

import com.example.authservice.dto.request.FormLogin;
import com.example.authservice.dto.request.FormRegister;

public interface AuthService {
    Boolean register(FormRegister formRegister);
    String login(FormLogin formLogin);

    void authenticator(String jwt);
}
