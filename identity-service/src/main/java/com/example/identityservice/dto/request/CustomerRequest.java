package com.example.identityservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CustomerRequest {

    private Long id;

    private String name;

    private String numberPhone;

    private String email;

    private String gender;

    private String address;

    private Integer status;

//    private String urlImage;

    private Long loyaltyPoints;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

}
