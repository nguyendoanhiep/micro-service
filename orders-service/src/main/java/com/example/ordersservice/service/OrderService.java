package com.example.ordersservice.service;

import com.example.ordersservice.dto.request.OrdersRequest;

import com.example.ordersservice.dto.response.OrdersResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface OrderService {
    Page<OrdersResponse> getAll(Pageable pageable, String search , Integer status , Date fromDate , Date toDate);
    Boolean save(OrdersRequest request);
    Boolean autoGenOrders(Long totalRecord , Date createDate);
    Boolean changeStatus(Long id);
}
