package com.example.productservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class VoucherRequest {
    private Long id;
    private String name;
    private String code;
    private Long value;
    private Long quantity;
    private Integer status;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date startDate;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date expirationDate;
}
