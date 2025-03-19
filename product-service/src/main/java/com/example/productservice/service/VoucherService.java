package com.example.productservice.service;

import com.example.productservice.dto.request.AssignCusRequest;
import com.example.productservice.dto.request.VoucherRequest;
import com.example.productservice.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VoucherService {
    Page<Voucher> getAll(Pageable pageable, String search , Integer status);
    List<Long> getCustomerIdsById(Long voucherId);
    Boolean save(VoucherRequest request);
    Boolean AssignCus(AssignCusRequest assignCusRequest);
    Boolean delete(Long id);

    List<Voucher> getByCustomerId(Long customerId);
    Voucher findById(Long id);
    Long useVoucher(Long id);
}
