package com.example.authservice.service.impl;

import com.example.authservice.dto.response.UserResponse;
import com.example.authservice.entity.User;
import com.example.authservice.repository.RoleRepository;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.security.JwtTokenProvider;
import com.example.authservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Override
    public Page<UserResponse> getAll(Pageable pageable, String search, Integer status) {
        return userRepository.getAll(pageable , search , status);
    }


}
