package com.example.identityservice.service;

import com.example.identityservice.dto.request.FormLogin;
import com.example.identityservice.dto.request.FormRegister;
import com.example.identityservice.dto.request.IntrospectRequest;

public interface AuthService {
    Boolean register(FormRegister formRegister);
    String login(FormLogin formLogin);
    boolean introspect(IntrospectRequest jwt);
}
