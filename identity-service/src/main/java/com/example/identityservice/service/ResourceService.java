package com.example.identityservice.service;

import com.example.identityservice.dto.response.ResourceResponse;
import com.example.identityservice.entity.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ResourceService {
    Set<ResourceResponse> getAll();

    Resource addOrUpdate(Resource resource);

    Boolean delete(Long id);
}
