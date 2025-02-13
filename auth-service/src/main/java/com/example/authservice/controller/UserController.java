package com.example.authservice.controller;

import com.example.authservice.dto.ApiResponse;
import com.example.authservice.dto.request.FormLogin;
import com.example.authservice.dto.request.FormRegister;
import com.example.authservice.service.UserService;
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
                                 @RequestParam String search,
                                 @RequestParam Integer status
    ) {
        return ApiResponse.builder()
                .code(200)
                .message("ok")
                .result(userService.getAll(PageRequest.of(page - 1, size), search, status))
                .build();
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
