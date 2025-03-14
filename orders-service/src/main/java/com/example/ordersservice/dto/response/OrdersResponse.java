package com.example.ordersservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class OrdersResponse {
    private Long id;
    private String customerName;
    private String code;
    private String numberPhone;
    private String voucherName;
    private Long originalTotalValue;
    private Long discountAmount;
    private Long totalValue;
    private Integer status;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createDate;

    public OrdersResponse(Long id , String customerName, String code, String numberPhone, String voucherName, Long originalTotalValue, Long discountAmount, Long totalValue, Integer status, Date createDate) {
        this.id = id;
        this.customerName = customerName;
        this.code = code;
        this.numberPhone = numberPhone;
        this.voucherName = voucherName;
        this.originalTotalValue = originalTotalValue;
        this.discountAmount = discountAmount;
        this.totalValue = totalValue;
        this.status = status;
        this.createDate = createDate;
    }
}
