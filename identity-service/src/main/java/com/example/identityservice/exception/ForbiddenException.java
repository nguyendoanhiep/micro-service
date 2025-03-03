package com.example.identityservice.exception;

import com.example.identityservice.dto.ErrorCode;
import lombok.Data;

@Data
public class ForbiddenException extends RuntimeException{

    private ErrorCode errorCode = ErrorCode.UNAUTHORIZATION;

    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException() {
    }
}
