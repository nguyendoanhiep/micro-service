package com.example.identityservice.controller;

import com.example.identityservice.dto.ApiResponse;
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
    public ApiResponse<?> getAll(@RequestParam int page,
                                 @RequestParam int size,
                                 @RequestParam(required = false) String search,
                                 @RequestParam Integer status
    ) {
        return ApiResponse.SUCCESS(userService.getAll(PageRequest.of(page - 1, size), search, status));
    }


//    @PostMapping("/edit")
//    public Response<?> edit(@RequestBody UserRequest request) {
//        return userService.editUser(request);
//    }

//    @PostMapping("/changePassword")
//    public Response<?> changePassword(@RequestBody FormChangePassword formChangePassword) {
//        return userService.changePassword(formChangePassword);
//    }
}
