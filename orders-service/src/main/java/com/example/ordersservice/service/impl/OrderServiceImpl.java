package com.example.ordersservice.service.impl;


import com.example.ordersservice.dto.request.OrdersRequest;
import com.example.ordersservice.dto.response.OrdersResponse;
import com.example.ordersservice.entity.Orders;
import com.example.ordersservice.repository.OrderRepository;
import com.example.ordersservice.repository.OrdersProductRepository;
import com.example.ordersservice.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;


    @Autowired
    OrdersProductRepository ordersProductRepository;

    @Override
    public Page<Orders> getAll(Pageable pageable, String search, Integer status) {

            return orderRepository.findAll(pageable);

    }

    @Override
    @Transactional
    public Boolean save(OrdersRequest request) {
//            if (request.getNumberPhone() != null) {
//                Customer customer = customerRepository.findByNumberPhone(request.getNumberPhone());
//                if (customer == null) {
//                    customerRepository.save(Customer
//                            .builder()
//                            .id(null)
//                            .numberPhone(request.getNumberPhone())
//                            .status(1)
//                            .loyaltyPoints(0L)
//                            .createDate(new Date())
//                            .modifiedDate(new Date())
//                            .build());
//                } else {
//                    customer.setLoyaltyPoints(customer.getLoyaltyPoints() + (request.getTotalValue()) / 100);
//                    customerRepository.save(customer);
//                }
//            }
//            if (request.getVoucherId() != null) {
//                Optional<Voucher> voucher = voucherRepository.findById(request.getVoucherId());
//                voucher.ifPresent(entity -> {
//                    if (entity.getQuantity() > 0) {
//                        entity.setQuantity(entity.getQuantity() - 1);
//                        if (entity.getQuantity() == 0) {
//                            entity.setStatus(2);
//                        }
//                        voucherRepository.save(entity);
//                    }
//                });
//            }
            Orders orders = orderRepository.save(Orders
                    .builder()
                    .id(request.getId())
                    .code(generateRandomCode())
                    .customerId(null)
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
    public Boolean changeStatus (Long id) {
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
