package com.example.identityservice.controller;

import com.example.identityservice.dto.ApiResponse;
import com.example.identityservice.dto.request.CustomerRequest;
import com.example.identityservice.dto.request.OrdersRequest;
import com.example.identityservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/getAll")
    public ApiResponse<?> getAll(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(required = false) String search,
                                 @RequestParam(required = false) Integer status) {
        return ApiResponse.SUCCESS(customerService.getAll(PageRequest.of(page - 1, size), search, status));
    }
    @GetMapping("/findById")
    public ApiResponse<?> findById(@RequestParam Long id) {
        return ApiResponse.SUCCESS(customerService.findById(id));
    }


    @PostMapping("/addOrUpdate")
    public ApiResponse<?> addOrUpdate(@RequestBody CustomerRequest request) {
        return ApiResponse.SUCCESS(customerService.addOrUpdate(request));
    }

    @PostMapping("/createOrUpdatePoint")
    public ApiResponse<?> createOrUpdatePoint(@RequestBody OrdersRequest request) {
        return ApiResponse.SUCCESS(customerService.createOrUpdatePoint(request));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<?> delete(@RequestParam Long id) {
        return ApiResponse.SUCCESS(customerService.delete(id));
    }
}
