package com.example.identityservice.controller;

import com.example.identityservice.dto.ApiResponse;
import com.example.identityservice.entity.User;
import com.example.identityservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/getAll")
    public ApiResponse<?> getAll(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(required = false) String search,
                                 @RequestParam(required = false) Integer status
    ) {
        return ApiResponse.SUCCESS(userService.getAll(PageRequest.of(page - 1, size), search, status));
    }
    @PostMapping("/save")
    public ApiResponse<?> save(@RequestBody User user) {
        return ApiResponse.SUCCESS(userService.save(user));
    }

    @PostMapping("/edit")
    public ApiResponse<?> edit(@RequestBody User user) {
        return ApiResponse.SUCCESS(userService.edit(user));
    }

    @PostMapping("/delete")
    public ApiResponse<?> delete(@RequestParam Long id) {
        return ApiResponse.SUCCESS(userService.delete(id));
    }
}
