package com.example.newsfeed.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    @Setter
    private int status;

    @Setter
    private String code;

    @Setter
    private String message;

    private String reason;

    public ErrorResponse(final ExceptionCode code, final String reason) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
        this.reason = reason;
    }

    public static ErrorResponse of(final ExceptionCode code, final String reason) {
        return new ErrorResponse(code, reason);
    }


}