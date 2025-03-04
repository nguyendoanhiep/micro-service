package com.example.identityservice.service.impl;

import com.example.identityservice.dto.request.FormLogin;
import com.example.identityservice.dto.request.FormRegister;
import com.example.identityservice.dto.request.IntrospectRequest;
import com.example.identityservice.entity.Role;
import com.example.identityservice.entity.User;
import com.example.identityservice.dto.UserLoginInfo;
import com.example.identityservice.exception.BusinessException;
import com.example.identityservice.exception.DataAlreadyExistsException;
import com.example.identityservice.dto.ErrorCode;
import com.example.identityservice.exception.ForbiddenException;
import com.example.identityservice.repository.ResourceRepository;
import com.example.identityservice.repository.RoleRepository;
import com.example.identityservice.repository.UserRepository;
import com.example.identityservice.security.JwtTokenProvider;
import com.example.identityservice.service.AuthService;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final String[] PUBLIC_ENDPOINTS = {
            "/identity/auth/register",
            "/identity/auth/login",
            "/identity/auth/introspect",
            "/product/getAll",
    };
    private final PathMatcher pathMatcher = new AntPathMatcher();
    @Override
    @Transactional
    public Boolean register(FormRegister formRegister) {
        try {
            Optional<User> checkUserExists = userRepository.findByUsername(formRegister.getUsername());
            if (checkUserExists.isPresent()) {
                throw new DataAlreadyExistsException();
            }
            Set<Role> roles = new HashSet<>();
            Optional<Role> role = roleRepository.findByName("ROLE_USER");
            role.ifPresent(roles::add);
            userRepository.save(User.builder()
                    .id(null)
                    .username(formRegister.getUsername())
                    .password(passwordEncoder.encode(formRegister.getPassword()))
                    .numberPhone(formRegister.getNumberPhone())
                    .roles(roles)
                    .status(1)
                    .createDate(new Date())
                    .modifiedDate(new Date())
                    .build());
            return true;
        } catch (Exception e) {
            log.info(e.getMessage());
            throw e;
        }
    }

    @Override
    public String login(FormLogin formLogin) {
        try {
            User user = userRepository.getUserByUsername(formLogin.getUsername());
            if(!passwordEncoder.matches(formLogin.getPassword(),user.getPassword())) throw new BusinessException(ErrorCode.INVALID_PASSWORD);
            String jwt = jwtTokenProvider.generateToken(user);
            UserLoginInfo userLoginInfo = UserLoginInfo
                    .builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .roles(user.getRoles())
                    .resources(resourceRepository.getResourcesByRoleIds(user
                            .getRoles()
                            .stream()
                            .map(Role::getId)
                            .collect(Collectors.toSet())))
                    .jwtToken(jwt)
                    .build();
            saveUserSession(userLoginInfo);
            return jwt;
        }catch (Exception e) {
            log.info(e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean introspect(IntrospectRequest introspectRequest) {
        if(Arrays.stream(PUBLIC_ENDPOINTS).anyMatch(
                publicEndpoint -> pathMatcher.match(publicEndpoint,introspectRequest.getPath()))){
            return true;
        }
        if (!jwtTokenProvider.validateToken(introspectRequest.getToken())) {
            throw new InvalidBearerTokenException("Invalid Token");
        }
        UserLoginInfo userLoginInfo = getUserSession(jwtTokenProvider.getIdFromJWT(introspectRequest.getToken()));
        if(userLoginInfo.getRoles().stream().map(Role::getName).anyMatch("ROLE_ADMIN"::equals)){
            return true;
        }
        if(userLoginInfo.getResources().stream().noneMatch(
                item -> item.getPath().contains(introspectRequest.getPath()))){
            throw new ForbiddenException();
        }
        return true;
    }

    public void saveUserSession(UserLoginInfo userLoginInfo){
        try {
            stringRedisTemplate.opsForValue().set("USER_SESSION_" + userLoginInfo.getId(),new Gson().toJson(userLoginInfo));
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }
    public UserLoginInfo getUserSession(Long id) {
        return new Gson().fromJson(stringRedisTemplate.opsForValue().get("USER_SESSION_" + id),UserLoginInfo.class);
    }
}
