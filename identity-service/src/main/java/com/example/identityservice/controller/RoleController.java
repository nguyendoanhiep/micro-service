package com.example.identityservice.controller;

import com.example.identityservice.dto.ApiResponse;
import com.example.identityservice.entity.Role;
import com.example.identityservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;
    @GetMapping("/getAll")
    public ApiResponse<?> getAll(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(required = false) String name,
                                 @RequestParam Integer status
    ) {
        return ApiResponse.SUCCESS(roleService.getAll(PageRequest.of(page - 1, size), name, status));
    }

    @PostMapping("/save")
    public ApiResponse<?> save(@RequestBody Role role) {
        return ApiResponse.SUCCESS(roleService.save(role));
    }

    @PostMapping("/edit")
    public ApiResponse<?> edit(@RequestBody Role role) {
        return ApiResponse.SUCCESS(roleService.edit(role));
    }

    @PostMapping("/delete")
    public ApiResponse<?> delete(@RequestParam Long id) {
        return ApiResponse.SUCCESS(roleService.delete(id));
    }

}
