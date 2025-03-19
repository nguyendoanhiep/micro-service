package com.example.ordersservice.http;

import com.example.ordersservice.dto.ApiResponse;
import com.example.ordersservice.dto.request.OrdersRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "identity-service", url = "${app.services.identity}")
public interface IdentityClient {
    @PostMapping(value = "/customer/createOrUpdatePoint", produces = MediaType.APPLICATION_JSON_VALUE)
    Long createOrUpdatePoint(@RequestBody OrdersRequest request);

    @GetMapping(value = "/customer/findById", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> findById(@RequestParam Long id);
}
