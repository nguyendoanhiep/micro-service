package com.example.identityservice.service.impl;

import com.example.identityservice.entity.Resource;
import com.example.identityservice.repository.ResourceRepository;
import com.example.identityservice.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    ResourceRepository resourceRepository;

    @Override
    public Page<Resource> getAll(Pageable pageable, String path, Integer status) {
        return resourceRepository.getAll(pageable, path, status);
    }

    @Override
    public Resource save(Resource resource) {
        return resourceRepository.save(resource);
    }

    @Override
    public Resource edit(Resource resource) {
        return resourceRepository.save(resource);
    }

    @Override
    public Boolean delete(Long id) {
        resourceRepository.deleteById(id);
        return true;
    }
}
