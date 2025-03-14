package com.example.identityservice.dto.request;

import lombok.Data;

import java.util.Set;

@Data
public class UserRequest {
    private Long id;
    private String username;
    private String password;
    private String numberPhone;
    private Integer status;
    private Set<String> roles;
}
