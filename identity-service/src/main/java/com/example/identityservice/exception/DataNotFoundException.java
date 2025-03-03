package com.example.identityservice.exception;

import com.example.identityservice.dto.ErrorCode;
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
