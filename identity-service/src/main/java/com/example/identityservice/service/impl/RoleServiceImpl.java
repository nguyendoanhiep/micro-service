package com.example.identityservice.service.impl;

import com.example.identityservice.entity.Role;
import com.example.identityservice.repository.RoleRepository;
import com.example.identityservice.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;
    @Override
    public Page<Role> getAll(Pageable pageable, String name, Integer status) {
        return roleRepository.getAll(pageable,name,status);
    }

    @Override
    public Role addOrUpdate(Role role) {
        return roleRepository.save(role);
    }


    @Override
    public Boolean delete(Long id) {
        roleRepository.deleteById(id);
        return true;
    }
}
