package com.example.ordersservice.repository;


import com.example.ordersservice.dto.response.OrdersResponse;
import com.example.ordersservice.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;


public interface OrderRepository extends JpaRepository<Orders,Long> {
    @Query("select o from Orders o " +
            "where (:search is null or o.code like %:search%) " +
            "and (:status is null or o.status = :status)" +
            "and (cast(:fromDate as date ) is null or o.createDate >= :fromDate) " +
            "and (cast(:toDate as date ) is null or o.createDate <= :toDate) " +
            "order by o.createDate desc")
    Page<Orders> getAll(Pageable pageable,
                        @Param("search") String search,
                        @Param("status") Integer status,
                        @Param("fromDate") Date fromDate,
                        @Param("toDate") Date toDate);
}
