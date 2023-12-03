package com.ensak.connect.config.api;


import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiError {
    private HttpStatus status;
    private Boolean success;
    private String message;

    public ApiError(){
    this.success = false;
    }

    public ApiError(HttpStatus status){
        this();
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable ex){
        this(status);
        this.message = ex.getMessage();
    }
}
