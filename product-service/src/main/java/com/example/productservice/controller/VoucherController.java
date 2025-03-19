package com.example.productservice.controller;


import com.example.productservice.dto.ApiResponse;
import com.example.productservice.dto.request.AssignCusRequest;
import com.example.productservice.dto.request.VoucherRequest;
import com.example.productservice.entity.Voucher;
import com.example.productservice.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/voucher")
public class VoucherController {
    @Autowired
    VoucherService voucherService;

    @GetMapping("/getAll")
    public ApiResponse<?> getAll(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(required = false) String search,
                                 @RequestParam(required = false) Integer status
    ) {
        return ApiResponse.SUCCESS(voucherService.getAll(PageRequest.of(page - 1, size), search, status ));
    }

    @PostMapping("/addOrUpdate")
    public ApiResponse<?> addOrUpdate(@RequestBody VoucherRequest request) {
        return ApiResponse.SUCCESS(voucherService.save(request));
    }

    @PostMapping("/AssignCus")
    public ApiResponse<?> AssignCus(@RequestBody AssignCusRequest assignCusRequest) {
        return ApiResponse.SUCCESS(voucherService.AssignCus( assignCusRequest));
    }

    @GetMapping("/getCustomerIdsById")
    public ApiResponse<?> getCustomerIdsById(@RequestParam Long voucherId) {
        return ApiResponse.SUCCESS(voucherService.getCustomerIdsById(voucherId));
    }

    @GetMapping("/findById")
    public Voucher findById(@RequestParam Long id) {
        return voucherService.findById(id);
    }

    @PostMapping("/useVoucher")
    public Long useVoucher(@RequestParam Long id) {
        return voucherService.useVoucher(id);
    }

    @PostMapping("/delete")
    public ApiResponse<?> delete(@RequestParam Long id) {
        return ApiResponse.SUCCESS(voucherService.delete(id));
    }

    @GetMapping("/getByCustomerId")
    public ApiResponse<?> getByCustomerId(@RequestParam Long customerId) {
        return ApiResponse.SUCCESS(voucherService.getByCustomerId(customerId));
    }
}
