package com.example.ordersservice.repository;


import com.example.ordersservice.dto.response.OrdersResponse;
import com.example.ordersservice.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface OrderRepository extends JpaRepository<Orders,Long> {
    @Query("select o from Orders o where (:search is null or o.code like %:search%) and (:status = 0 or o.status = :status) order by o.createDate desc")
    Page<Orders> getAll(Pageable pageable, String search , Integer status);

}
