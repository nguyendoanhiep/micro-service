package com.example.ordersservice.dto.request;

import com.example.ordersservice.entity.OrdersProduct;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrdersRequest {
    private Long id;
    private String code;
    private String numberPhone;
    private Long voucherId;
    private Long originalTotalValue;
    private Long discountAmount;
    private Long totalValue;
    private Integer status;
    private List<OrdersProduct> ordersProducts;
}
