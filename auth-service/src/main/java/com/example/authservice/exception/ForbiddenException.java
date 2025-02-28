package com.example.authservice.exception;

import com.example.authservice.dto.ErrorCode;
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


}
