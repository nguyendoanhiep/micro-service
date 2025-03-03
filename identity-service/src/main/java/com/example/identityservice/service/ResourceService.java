package com.example.identityservice.service;

import com.example.identityservice.entity.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResourceService {
    Page<Resource> getAll(Pageable pageable, String path , Integer status);

    Resource save(Resource resource);

    Resource edit(Resource resource);

    Boolean delete(Long id);
}
