package com.example.identityservice.service.impl;

import com.example.identityservice.dto.request.BindingRequest;
import com.example.identityservice.entity.Resource;
import com.example.identityservice.entity.Role;
import com.example.identityservice.repository.ResourceRepository;
import com.example.identityservice.repository.RoleRepository;
import com.example.identityservice.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ResourceRepository resourceRepository;

    @Override
    public Page<Role> getAll(Pageable pageable, String name, Integer status) {
        return roleRepository.getAll(pageable, name, status);
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

    @Override
    public Boolean binding(BindingRequest request) {
        Role role = roleRepository.findById(request.getRoleId()).get();
        Set<Resource> resources = resourceRepository.getResourcesByIds(
                request.getResourceIds()
                        .stream()
                        .map(Long::valueOf)
                        .collect(Collectors.toSet()));
        role.setResources(resources);
        roleRepository.save(role);
        return true;
    }
}
