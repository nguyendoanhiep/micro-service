package com.example.productservice.service;


import com.example.productservice.dto.request.ProductRequest;
import com.example.productservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<Product> getAll(Pageable pageable , String name, Integer status);
    Product addOrUpdate(ProductRequest request);
    Boolean delete(Long id);

}
