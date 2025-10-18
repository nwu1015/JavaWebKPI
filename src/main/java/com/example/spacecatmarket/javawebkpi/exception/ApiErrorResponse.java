package com.example.spacecatmarket.javawebkpi.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorResponse {
    private int status;
    private String error;
    private String message;
    private String path;
}