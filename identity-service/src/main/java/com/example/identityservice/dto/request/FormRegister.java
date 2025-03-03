package com.example.identityservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormRegister {
    private String username;
    private String password;
    private String numberPhone;
}
