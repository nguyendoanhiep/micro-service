package com.example.identityservice.service.impl;

import com.example.identityservice.dto.ErrorCode;
import com.example.identityservice.dto.request.FormChangePassword;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.entity.User;
import com.example.identityservice.exception.BusinessException;
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

import java.util.Date;
import java.util.List;

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

    @Override
    public Page<UserResponse> getAll(Pageable pageable, String search, Integer status) {
        Page<User> users = userRepository.getAll(pageable , search , status);
        return users.map(user -> new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getNumberPhone(),
                user.getStatus(),
                user.getCreateDate(),
                user.getModifiedDate(),
                user.getRoles()
        ));
    }

    @Override
    public User save(User user) {
        user.setCreateDate(new Date());
        user.setModifiedDate(new Date());
        return userRepository.save(user);
    }

    @Override
    public User edit(User user) {
        user.setModifiedDate(new Date());
        return userRepository.save(user);
    }

    @Override
    public Boolean delete(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public Boolean changePassword(FormChangePassword formChangePassword) {
        User user = userRepository.findById(formChangePassword.getId()).get();
        boolean isPasswordMatch = passwordEncoder.matches(formChangePassword.getCurrentPassword(), user.getPassword());
        if (!isPasswordMatch) {
            throw new BusinessException(ErrorCode.PASSWORD_NO_MATCH);
        }
        user.setPassword(passwordEncoder.encode(formChangePassword.getNewPassword()));
        userRepository.save(user);
        return true;
    }


}
