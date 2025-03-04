package com.example.productservice.exception;


import com.example.productservice.dto.ApiResponse;
import com.example.productservice.dto.ErrorCode;
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
        log.error("RuntimeException: ", ex);
        if(ex instanceof DataNotFoundException){
            return ResponseEntity.ok(ApiResponse.FAIL(((DataNotFoundException) ex).getErrorCode()));
        }
        if(ex instanceof DataAlreadyExistsException){
            return ResponseEntity.ok(ApiResponse.FAIL(((DataAlreadyExistsException) ex).getErrorCode()));
        }

        return ResponseEntity.ok(ApiResponse.FAIL(500, ex.getMessage()));
    }
    @ExceptionHandler(value = BusinessException.class)
    ResponseEntity<?> BusinessException(BusinessException ex) {
        log.error("RuntimeException: ", ex);
        return ResponseEntity.ok(ApiResponse.FAIL(ex.getErrorCode().getCode(), ex.getMessage()));
    }

}
