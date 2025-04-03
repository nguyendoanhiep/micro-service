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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer implements Serializable {

    private Long id;

    private String name;

    private String numberPhone;

    private String email;

    private String gender;

    private String address;

    private Integer status;

    private String urlImage;

    private Long loyaltyPoints;

}
