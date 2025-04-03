package com.example.ordersservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Voucher implements Serializable {

    private Long id;

    private String name;

    private String code;

    private Long value;

    private Long quantity;

    private Integer status;



}
