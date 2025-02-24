package com.example.authservice.exception;

import lombok.Data;

@Data
public class DataAlreadyExistsException extends RuntimeException{
    public DataAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    private ErrorCode errorCode;

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
