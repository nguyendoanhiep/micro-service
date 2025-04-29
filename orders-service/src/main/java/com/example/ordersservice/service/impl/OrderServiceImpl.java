package com.example.ordersservice.service.impl;


import com.example.ordersservice.dto.ApiResponse;
import com.example.ordersservice.dto.request.OrdersRequest;
import com.example.ordersservice.dto.response.Customer;
import com.example.ordersservice.dto.response.OrdersResponse;
import com.example.ordersservice.dto.response.Voucher;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;


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
    public Page<OrdersResponse> getAll(Pageable pageable, String search, Integer status, Date fromDate, Date toDate) {
        return orderRepository.getAll(pageable, search, status ,fromDate , toDate).map(order -> {
            Customer customer = new Customer();
            if (order.getCustomerId() != null) {
                ApiResponse<Customer> response = identityClient.findById(order.getCustomerId());
                if(response.getCode() == 200) {
                    customer = response.getData();
                }
            }
            Voucher voucher = new Voucher();
            if (order.getVoucherId() != null) {
                ApiResponse<Voucher> response = voucherClient.findById(order.getVoucherId());
                if(response.getCode() == 200) {
                    voucher = response.getData();
                }
            }
            return new OrdersResponse(
                    order.getId(),
                    order.getCustomerId() == null ? "" : customer.getName(),
                    order.getCode(),
                    order.getCustomerId() == null ? "" : customer.getNumberPhone(),
                    order.getVoucherId() == null ? "" : voucher.getName(),
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
            ApiResponse<Long> apiResponse = identityClient.createOrUpdatePoint(request);
            if(apiResponse.getCode() == 200){
                customerId = apiResponse.getData();
            }else {
                return false;
            }
        }
        if (request.getVoucherId() != null) {
            ApiResponse<Long> apiResponse = voucherClient.useVoucher(request.getVoucherId());
            if(apiResponse.getCode() != 200){
                // Kafka send message rollback step 1
                return false;
            }
        }
        try{
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
        }catch (Exception e){
            log.info(e.getMessage());
            // Kafka send message rollback step 1 and step 2
        }
        return true;

    }

    @Override
    public Boolean autoGenOrders(Long totalRecord, Date createDate) {
        List<Orders> orders = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createDate);
        for (int i = 0; i < totalRecord; i++) {
            Date newDate = calendar.getTime();
            try{
                Long value = getRandomValue();
                orders.add(
                        Orders.builder()
                                .code(generateRandomCode())
                                .customerId(getRandomNumber())
                                .originalTotalValue(value)
                                .discountAmount(0L)
                                .totalValue(value)
                                .status(1)
                                .createDate(newDate)
                                .modifiedDate(new Date())
                                .build());
                calendar.add(Calendar.MINUTE, 1);
            }catch (Exception e){
                log.info(e.getMessage());
                // Kafka send message rollback step 1 and step 2
            }
        }
        orderRepository.saveAll(orders);
        return true;
    }
    Long getRandomNumber() {
        Long[] numbers = {1L, 3L, 4L, 5L, 6L , null , null};
        Random random = new Random();
        int randomIndex = random.nextInt(numbers.length);
        return numbers[randomIndex];
    }
    public long getRandomValue() {
        long[] values = {100000, 20000, 40000, 45000, 52000, 83200, 63000, 270000, 140000, 190000};
        Random random = new Random();
        int randomIndex = random.nextInt(values.length);
        return values[randomIndex];
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

        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(candidateChars.length());
            sb.append(candidateChars.charAt(index));
        }
        return sb.append(timestamp).toString();
    }
}
