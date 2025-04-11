package com.example.newsfeed.common.response;

import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final HttpStatus httpStatus;
    private final boolean success;
    private final T data;
    private final ErrorResponse errorResponse;

    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(HttpStatus.OK, true, null, null);
    }

    public static <T> ApiResponse<T> ok(final T data) {
        return new ApiResponse<>(HttpStatus.OK, true, data, null);
    }

    public static <T> ApiResponse<T> created(final T data) {
        return new ApiResponse<>(HttpStatus.CREATED, true, data, null);
    }

    public static <T> ApiResponse<T> fail(final BusinessException e) {
        return new ApiResponse<>(e.getExceptionCode().getHttpStatus(), false, null,
            ErrorResponse.of(e.getExceptionCode()));
    }

}
