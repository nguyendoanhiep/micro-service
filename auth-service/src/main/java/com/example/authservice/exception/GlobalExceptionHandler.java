package com.example.authservice.exception;

import com.example.authservice.dto.ApiResponse;
import com.example.authservice.dto.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<?> handlingException(Exception exception) {
        log.error("Exception: ", exception);
        return ResponseEntity.ok(ApiResponse.FAIL(ErrorCode.FAIL));
    }
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<?> handlingRuntimeException(RuntimeException ex) {
        log.error("RuntimeException: ", ex);
        if(ex instanceof DataNotFoundException){
            return ResponseEntity.ok(ApiResponse.FAIL(((DataNotFoundException) ex).getErrorCode()));
        }
        if(ex instanceof DataAlreadyExistsException){
            return ResponseEntity.ok(ApiResponse.FAIL(((DataAlreadyExistsException) ex).getErrorCode()));
        }
        if(ex instanceof ForbiddenException){
            return ResponseEntity.ok(ApiResponse.FAIL(((ForbiddenException) ex).getErrorCode()));
        }
        if(ex instanceof InvalidBearerTokenException){
            return ResponseEntity.ok(ApiResponse.FAIL(ErrorCode.INVALID_TOKEN));
        }
        return ResponseEntity.ok(ApiResponse.FAIL(500, ex.getMessage()));
    }
    @ExceptionHandler(value = BusinessException.class)
    ResponseEntity<?> BusinessException(BusinessException ex) {
        log.error("RuntimeException: ", ex);
        return ResponseEntity.ok(ApiResponse.FAIL(ex.getErrorCode().getCode(), ex.getMessage()));
    }

}
