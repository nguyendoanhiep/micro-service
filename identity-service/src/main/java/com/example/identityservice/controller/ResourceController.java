package com.example.identityservice.controller;

import com.example.identityservice.dto.ApiResponse;
import com.example.identityservice.entity.Resource;
import com.example.identityservice.entity.Role;
import com.example.identityservice.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    ResourceService resourceService;

    @GetMapping("/getAll")
    public ApiResponse<?> getAll(@RequestParam int page,
                                 @RequestParam int size,
                                 @RequestParam(required = false) String path,
                                 @RequestParam Integer status
    ) {
        return ApiResponse.SUCCESS(resourceService.getAll(PageRequest.of(page - 1, size), path, status));
    }

    @PostMapping("/save")
    public ApiResponse<?> save(@RequestBody Resource resource) {
        return ApiResponse.SUCCESS(resourceService.save(resource));
    }

    @PostMapping("/edit")
    public ApiResponse<?> edit(@RequestBody Resource resource) {
        return ApiResponse.SUCCESS(resourceService.edit(resource));
    }

    @PostMapping("/delete")
    public ApiResponse<?> delete(@RequestParam Long id) {
        return ApiResponse.SUCCESS(resourceService.delete(id));
    }
}
