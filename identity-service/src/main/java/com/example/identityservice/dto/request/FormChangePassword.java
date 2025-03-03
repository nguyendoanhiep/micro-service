package com.example.identityservice.dto.request;

import lombok.Data;

@Data
public class FormChangePassword {
    Long id;
    String currentPassword;
    String newPassword;
}
