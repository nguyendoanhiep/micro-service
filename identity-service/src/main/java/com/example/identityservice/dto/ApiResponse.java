package com.example.identityservice.dto;

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


    public static <T> ApiResponse<?> SUCCESS( T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        response.setData(data);
        return response;
    }
    public static <T> ApiResponse<?> FAIL(int code , String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }
    public static <T> ApiResponse<?> FAIL(ErrorCode errorCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return response;
    }
}