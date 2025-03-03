package com.example.identityservice.service.impl;

import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.repository.RoleRepository;
import com.example.identityservice.repository.UserRepository;
import com.example.identityservice.security.JwtTokenProvider;
import com.example.identityservice.service.UserService;
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
