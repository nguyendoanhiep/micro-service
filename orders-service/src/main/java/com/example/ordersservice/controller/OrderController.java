package com.example.ordersservice.controller;

import com.example.ordersservice.dto.ApiResponse;
import com.example.ordersservice.dto.request.OrdersRequest;
import com.example.ordersservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/getAll")
    public ApiResponse<?> getAll(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(required = false) String search,
                                 @RequestParam(required = false) Integer status
    ) {
        return ApiResponse.SUCCESS(orderService.getAll(PageRequest.of(page - 1, size), search, status));
    }

    @PostMapping("/addOrUpdate")
    public ApiResponse<?> save(@RequestBody OrdersRequest request) {
        return ApiResponse.SUCCESS(orderService.save(request));
    }


    @PostMapping("/changeStatus")
    public ApiResponse<?> changeStatus(@RequestParam Long id) {
        return ApiResponse.SUCCESS(orderService.changeStatus(id));
    }
}
