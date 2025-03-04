package com.example.productservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> implements Serializable {
    @Builder.Default
    private int code = 1000;
    private String message;
    private T data;


    public static <T> ApiResponse<?> SUCCESS(T data) {
        return ApiResponse.builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }
    public static ApiResponse<?> FAIL(int code , String message) {
        return ApiResponse.builder()
                .code(code)
                .message(message)
                .data(false)
                .build();
    }
    public static ApiResponse<?> FAIL(ErrorCode errorCode) {
        return ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .data(false)
                .build();
    }
}