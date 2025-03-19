package com.example.ordersservice.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "product-service", url = "${app.services.product}")
public interface VoucherClient {
    @PostMapping(value = "/voucher/useVoucher", produces = MediaType.APPLICATION_JSON_VALUE)
    Long useVoucher(@RequestParam Long id);

    @GetMapping(value = "/voucher/findById", produces = MediaType.APPLICATION_JSON_VALUE)
    Map<?,String> findById(@RequestParam Long id);
}
