package com.example.identityservice.exception;

import com.example.identityservice.dto.ErrorCode;
import lombok.Data;

@Data
public class DataAlreadyExistsException extends RuntimeException{

    private ErrorCode errorCode = ErrorCode.DATA_ALREADY_EXISTS;

    public DataAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public DataAlreadyExistsException(String message) {
        super(message);
    }
    public DataAlreadyExistsException() {
    }
}
