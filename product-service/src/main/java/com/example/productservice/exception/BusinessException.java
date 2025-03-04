package com.example.productservice.exception;

import com.example.productservice.dto.ErrorCode;
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
