package com.example.authservice.exception;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    private ErrorCode errorCode;

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
