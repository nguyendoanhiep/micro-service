package com.example.authservice.service;

import com.example.authservice.dto.request.FormLogin;
import com.example.authservice.dto.request.FormRegister;
import com.example.authservice.dto.request.IntrospectRequest;

public interface AuthService {
    Boolean register(FormRegister formRegister);
    String login(FormLogin formLogin);

    boolean introspect(IntrospectRequest jwt);
}
