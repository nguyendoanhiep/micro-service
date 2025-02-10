package com.example.authservice.service;

import com.example.authservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> getAll(Pageable pageable , String search, Integer status);
}
