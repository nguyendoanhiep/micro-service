package com.example.identityservice.dto.request;

import lombok.Data;

import java.util.Set;

@Data
public class BindingRequest {
    Long roleId;
    Set<String> resourceIds;
}
