package com.example.identityservice.service;

import com.example.identityservice.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserResponse> getAll(Pageable pageable , String search, Integer status);

//    Response<?> editUser(UserRequest request);
//    Response<?> changePassword(FormChangePassword formChangePassword);
}
