package com.example.identityservice.service;


import com.example.identityservice.dto.request.CustomerRequest;
import com.example.identityservice.dto.request.OrdersRequest;
import com.example.identityservice.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    Page<Customer> getAll(Pageable pageable, String search , Integer status);
    Customer findById(Long id);
//    Response<?> getAllByVoucherId(Pageable pageable, String search , Long voucherId);
    Customer addOrUpdate(CustomerRequest request);
    Long createOrUpdatePoint(OrdersRequest request);
    Boolean delete(Long id);

}
