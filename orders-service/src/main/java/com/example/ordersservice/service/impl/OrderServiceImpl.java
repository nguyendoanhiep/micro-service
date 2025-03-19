package com.example.ordersservice.service.impl;


import com.example.ordersservice.dto.request.OrdersRequest;
import com.example.ordersservice.dto.response.OrdersResponse;
import com.example.ordersservice.entity.Orders;
import com.example.ordersservice.http.IdentityClient;
import com.example.ordersservice.http.VoucherClient;
import com.example.ordersservice.repository.OrderRepository;
import com.example.ordersservice.repository.OrdersProductRepository;
import com.example.ordersservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;

    OrdersProductRepository ordersProductRepository;

//    KafkaTemplate<String, String> kafkaTemplate;

    IdentityClient identityClient;

    VoucherClient voucherClient;

    ObjectMapper objectMapper;


    @Override
    public Page<OrdersResponse> getAll(Pageable pageable, String search, Integer status) {
        return orderRepository.getAll(pageable, search, status).map(order -> {
            Map<?,?> customer = null;
            if(order.getCustomerId() != null){
                customer = (Map<?,?>) identityClient.findById(order.getCustomerId()).getData();
            }
            Map<?,String> voucher = null;
            if(order.getVoucherId()!= null){
                 voucher = voucherClient.findById(order.getVoucherId());
            }
            return new OrdersResponse(
                    order.getId(),
                    order.getCustomerId() == null ? "" : customer.get("name").toString(),
                    order.getCode(),
                    order.getCustomerId() == null ? "" : customer.get("numberPhone").toString(),
                    order.getVoucherId()== null ? "" : voucher.get("name"),
                    order.getOriginalTotalValue(),
                    order.getDiscountAmount(),
                    order.getTotalValue(),
                    order.getStatus(),
                    order.getCreateDate()
            );
        });

    }

    @Override
    @Transactional
    public Boolean save(OrdersRequest request) {
        Long customerId = null;
        if (request.getNumberPhone() != null) {
            customerId = identityClient.createOrUpdatePoint(request);
        }
        if (request.getVoucherId() != null) {
            voucherClient.useVoucher(request.getVoucherId());
        }
        Orders orders = orderRepository.save(Orders
                .builder()
                .id(request.getId())
                .code(generateRandomCode())
                .customerId(customerId)
                .voucherId(request.getVoucherId())
                .originalTotalValue(request.getOriginalTotalValue())
                .discountAmount(request.getDiscountAmount() == null ? 0 : request.getDiscountAmount())
                .totalValue(request.getTotalValue())
                .status(1)
                .createDate(new Date())
                .modifiedDate(new Date())
                .build());
        request.getOrdersProducts().forEach(product -> product.setOrdersId(orders.getId()));
        ordersProductRepository.saveAll(request.getOrdersProducts());
        return true;

    }

    @Override
    public Boolean changeStatus(Long id) {
        Orders orders = orderRepository.findById(id).get();
        if (orders.getStatus() == 1) {
            orders.setStatus(2);
            orderRepository.save(orders);
            return true;
        }
        if (orders.getStatus() == 2) {
            orders.setStatus(1);
            orderRepository.save(orders);
            return true;
        }
        return false;
    }

    private String generateRandomCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(candidateChars.length());
            sb.append(candidateChars.charAt(index));
        }
        return sb.append(timestamp).toString();
    }
}
