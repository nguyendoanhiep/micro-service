package com.example.productservice.controller;

import com.example.productservice.dto.ApiResponse;
import com.example.productservice.dto.request.ProductRequest;
import com.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/getAll")
    public ApiResponse<?> getAll(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) Integer status
    ) {
        return ApiResponse.SUCCESS(productService.getAll(PageRequest.of(page - 1, size), name, status));
    }

    @PostMapping("/addOrUpdate")
    public ApiResponse<?> addOrUpdate(@RequestBody ProductRequest request) {
        return ApiResponse.SUCCESS(productService.addOrUpdate(request));
    }

    @PostMapping("/delete")
    public ApiResponse<?> delete(@RequestParam Long id) {
        return ApiResponse.SUCCESS(productService.delete(id));
    }

}
