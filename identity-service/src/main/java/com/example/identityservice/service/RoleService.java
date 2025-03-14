package com.example.identityservice.service;

import com.example.identityservice.dto.request.BindingRequest;
import com.example.identityservice.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    Page<Role> getAll(Pageable pageable,String name , Integer status);

    Role addOrUpdate(Role role);

    Boolean delete(Long id);
    Boolean binding(BindingRequest request);
}
