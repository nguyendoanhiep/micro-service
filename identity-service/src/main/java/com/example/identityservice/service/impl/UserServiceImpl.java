package com.example.identityservice.service.impl;

import com.example.identityservice.dto.ErrorCode;
import com.example.identityservice.dto.request.FormChangePassword;
import com.example.identityservice.dto.request.UserRequest;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.entity.User;
import com.example.identityservice.exception.BusinessException;
import com.example.identityservice.exception.DataNotFoundException;
import com.example.identityservice.repository.RoleRepository;
import com.example.identityservice.repository.UserRepository;
import com.example.identityservice.security.JwtTokenProvider;
import com.example.identityservice.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Page<UserResponse> getAll(Pageable pageable, String search, Integer status) {
        Page<User> users = userRepository.getAll(pageable, search, status);
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
    @Transactional
    public Boolean addOrUpdate(UserRequest request) {
        User user;
        if (request.getId() == null) {
            user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setCreateDate(new Date());
        } else {
            user = userRepository.findById(request.getId()).orElseThrow(() -> new DataNotFoundException(ErrorCode.DATA_NOT_FOUND));
        }
        user.setNumberPhone(request.getNumberPhone());
        user.setStatus(request.getStatus());
        user.setModifiedDate(new Date());
        user.setRoles(request.getRoles()
                .stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new BusinessException("ROLE NOT FOUND")))
                .collect(Collectors.toSet()));
        userRepository.save(user);
        return true;
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
