package com.example.ordersservice.repository;

import com.example.ordersservice.entity.OrdersProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersProductRepository extends JpaRepository<OrdersProduct,Long> {
}
