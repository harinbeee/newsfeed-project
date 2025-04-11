package com.example.newsfeed.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    private Integer code;
    private String message;

    public ErrorResponse(ExceptionCode exceptionCode) {
        this.message = exceptionCode.getMessage();
        this.code = exceptionCode.getCode();
    }

    public static ErrorResponse of(ExceptionCode code) {
        return new ErrorResponse(code);
    }

}