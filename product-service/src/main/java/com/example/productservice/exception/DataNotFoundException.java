package com.example.productservice.exception;

import com.example.productservice.dto.ErrorCode;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class DataNotFoundException extends RuntimeException{

    private ErrorCode errorCode;

    public DataNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public DataNotFoundException(String message) {
        super(message);
    }
}
