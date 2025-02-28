package com.example.authservice.exception;

import com.example.authservice.dto.ErrorCode;
import lombok.Data;

@Data
public class DataAlreadyExistsException extends RuntimeException{

    private ErrorCode errorCode;

    public DataAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public DataAlreadyExistsException(String message) {
        super(message);
    }
}
