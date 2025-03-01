package com.example.authservice.exception;

import com.example.authservice.dto.ErrorCode;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException{

    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(String message) {
        super(message);
    }
}
