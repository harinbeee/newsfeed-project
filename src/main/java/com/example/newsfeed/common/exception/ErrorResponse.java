package com.example.newsfeed.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
public class ErrorResponse {

    private HttpStatus httpStatus;
    private Integer code;
    private String message;

    public ErrorResponse(ExceptionCode exceptionCode) {
        this.message = exceptionCode.getMessage();
        this.httpStatus = exceptionCode.getHttpStatus();
        this.code = exceptionCode.getCode();
    }

    public static ErrorResponse of(ExceptionCode code) {
        return new ErrorResponse(code);
    }

}