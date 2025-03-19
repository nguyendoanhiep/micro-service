package com.example.ordersservice.service;

import com.example.ordersservice.dto.request.OrdersRequest;

import com.example.ordersservice.dto.response.OrdersResponse;
import com.example.ordersservice.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrdersResponse> getAll(Pageable pageable, String search , Integer status);
    Boolean save(OrdersRequest request);
    Boolean changeStatus(Long id);
}
