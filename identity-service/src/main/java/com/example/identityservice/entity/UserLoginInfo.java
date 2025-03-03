package com.example.identityservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginInfo {
    private Long id;
    private String username;
    private String jwtToken;
    private Set<Role> roles;
    private Set<Resource> resources;
}
