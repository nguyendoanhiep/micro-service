package com.example.authservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
        log.error("Exception: ", ex);
        if(ex instanceof DataNotFoundException){
            return ResponseEntity.ok(ApiResponse.FAIL(((DataNotFoundException) ex).getErrorCode()));
        }
        if(ex instanceof DataAlreadyExistsException){
            return ResponseEntity.ok(ApiResponse.FAIL(((DataAlreadyExistsException) ex).getErrorCode()));
        }
        if(ex instanceof BusinessException){
            return ResponseEntity.ok(ApiResponse.FAIL(((BusinessException) ex).getErrorCode()));
        }
        return ResponseEntity.ok(ApiResponse.FAIL(500, ex.getMessage()));
    }

}
