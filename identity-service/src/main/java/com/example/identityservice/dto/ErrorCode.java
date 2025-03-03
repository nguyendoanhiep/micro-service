package com.example.identityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    SUCCESS(200, "SUCCESS"),
    FAIL(400 , "UNKNOWN ERROR"),

    PASSWORD_NO_MATCH(413 , "Current Password Is Not Correct"),
    INVALID_TOKEN(412,"INVALID TOKEN"),
    UNAUTHENTICATED(401,"UNAUTHENTICATED"),
    UNAUTHORIZATION(403,"UNAUTHORIZATION"),
    INVALID_PASSWORD(411 , "INVALID PASSWORD"),
    DATA_NOT_FOUND(410 , "DATA NOT FOUND"),
    DATA_ALREADY_EXISTS(409 , "DATA ALREADY EXISTS");

    private final int code;
    private final String message;
}
