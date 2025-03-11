package com.example.identityservice.dto.response;

import com.example.identityservice.entity.Role;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Data
public class UserResponse {
    private Long id;

    private String username;

    private String numberPhone;

    private Integer status;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date modifiedDate;

    private Set<Role>roles;

    public UserResponse(Long id, String username, String numberPhone, Integer status, Date createDate, Date modifiedDate, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.numberPhone = numberPhone;
        this.status = status;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.roles = roles;
    }
}
