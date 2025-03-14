package com.example.productservice.repository;

import com.example.productservice.entity.VoucherCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoucherCustomerRepository extends JpaRepository<VoucherCustomer,Long> {

    void deleteAllByVoucherId(Long voucherId);

    @Query("select vc.customerId from VoucherCustomer vc where vc.voucherId = :voucherId")
    List<Long> getAllCustomerIdsById (Long voucherId);
}
