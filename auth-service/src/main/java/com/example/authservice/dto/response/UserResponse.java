package com.example.authservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class UserResponse {
    private Long id;

    private String username;

    private String numberPhone;

    private Integer status;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date modifiedDate;

    public UserResponse(Long id, String username, String numberPhone, Integer status, Date createDate, Date modifiedDate) {
        this.id = id;
        this.username = username;
        this.numberPhone = numberPhone;
        this.status = status;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }

}
