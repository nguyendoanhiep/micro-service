package com.example.identityservice.service;

import com.example.identityservice.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    Page<Role> getAll(Pageable pageable,String name , Integer status);

    Role save(Role role);

    Role edit(Role role);

    Boolean delete(Long id);
}
