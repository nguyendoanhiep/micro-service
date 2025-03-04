package com.example.productservice.dto.request;

import lombok.Data;


@Data
public class ProductRequest {
    private Long id;
    private String name;
    private Long price;
    private Integer status;
}
