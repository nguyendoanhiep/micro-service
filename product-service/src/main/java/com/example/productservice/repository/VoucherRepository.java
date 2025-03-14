package com.example.productservice.repository;

import com.example.productservice.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    @Query(value = "select v from Voucher v where " +
            "(:search is null or v.name like :search% or v.code like :search% ) and " +
            "(:status is null or v.status =:status)")
    Page<Voucher> getAll(Pageable pageable, String search , Integer status);

    @Query("select v from Voucher v join VoucherCustomer vc on v.id = vc.voucherId where vc.customerId = :customerId ")
    List<Voucher> getByCustomerId(Long customerId);

}
