package com.example.identityservice.service;

import com.example.identityservice.dto.request.FormChangePassword;
import com.example.identityservice.dto.request.UserRequest;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserResponse> getAll(Pageable pageable , String search, Integer status);

    Boolean addOrUpdate(UserRequest userRequest);

    Boolean delete(Long id);
    Boolean changePassword(FormChangePassword formChangePassword);
}
