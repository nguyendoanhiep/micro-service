package com.example.identityservice.service.impl;

import com.example.identityservice.dto.response.ResourceResponse;
import com.example.identityservice.entity.Resource;
import com.example.identityservice.repository.ResourceRepository;
import com.example.identityservice.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    ResourceRepository resourceRepository;

    @Override
    public Set<ResourceResponse> getAll() {
        List<Resource> resourcesParent = resourceRepository.getResourceParent();
        return resourcesParent.stream().map(resource -> {
            ResourceResponse resourceResponse = new ResourceResponse();
            resourceResponse.setId(resource.getId());
            resourceResponse.setName(resource.getName());
            resourceResponse.setPath(resource.getPath());
            resourceResponse.setMethod(resource.getMethod());
            resourceResponse.setResourceChildren(resourceRepository.getResourcesByParentId(resource.getId()));
           return resourceResponse;
        }).collect(Collectors.toSet());
    }

    @Override
    public Resource addOrUpdate(Resource resource) {
        return resourceRepository.save(resource);
    }


    @Override
    public Boolean delete(Long id) {
        resourceRepository.deleteById(id);
        return true;
    }
}
