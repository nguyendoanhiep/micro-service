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
    public ApiResponse<?> getAll() {
        return ApiResponse.SUCCESS(resourceService.getAll());
    }

    @PostMapping("/addOrUpdate")
    public ApiResponse<?> addOrUpdate(@RequestBody Resource resource) {
        return ApiResponse.SUCCESS(resourceService.addOrUpdate(resource));
    }

    @PostMapping("/delete")
    public ApiResponse<?> delete(@RequestParam Long id) {
        return ApiResponse.SUCCESS(resourceService.delete(id));
    }
}
