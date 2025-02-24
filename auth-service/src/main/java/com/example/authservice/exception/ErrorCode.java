package com.example.authservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    SUCCESS(200, "SUCCESS"),
    FAIL(400 , "FAIL"),
    INVALID_PASSWORD(411 , "INVALID PASSWORD"),
    DATA_NOT_FOUND(410 , "DATA NOT FOUND"),
    DATA_ALREADY_EXISTS(409 , "DATA ALREADY EXISTS");

    private final int code;
    private final String message;
}
