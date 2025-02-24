package com.example.authservice.service;

import com.example.authservice.dto.request.FormLogin;
import com.example.authservice.dto.request.FormRegister;
import com.example.authservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> getAll(Pageable pageable , String search, Integer status);

//    Response<?> editUser(UserRequest request);
//    Response<?> changePassword(FormChangePassword formChangePassword);
}
