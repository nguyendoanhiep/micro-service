package com.example.productservice.service.impl;

import com.example.productservice.dto.request.ProductRequest;
import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;


    @Override
    public Page<Product> getAll(Pageable pageable, String name, Integer status) {
        return productRepository.getAll(pageable, name, status);
    }

    @Override
    public Product addOrUpdate(ProductRequest request) {
        Product product = request.getId() == null ? new Product() : productRepository.findById(request.getId()).get();
        product.setId(request.getId());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStatus(request.getStatus());
        product.setCreateDate(request.getId() == null ? new Date() : product.getCreateDate());
        product.setModifiedDate(new Date());
        return productRepository.save(product);
    }

    @Override
    public Boolean delete(Long id) {
        productRepository.deleteById(id);
        return true;
    }
}
