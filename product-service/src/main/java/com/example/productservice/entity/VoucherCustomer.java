package com.example.productservice.entity;

import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
@Table(name = "voucher_customer")
public class VoucherCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "voucher_id", nullable = false)
    private Long voucherId;
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
}
