package com.example.newsfeed.common.handler;

import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.common.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 입력 값 검증 예외 핸들러
    @ExceptionHandler(value = {
        ResponseStatusException.class,
        ConstraintViolationException.class,
        MethodArgumentNotValidException.class
    })
    public ApiResponse<?> handleValidationException(Exception ex) {
        log.error("Catch Validation Exception : {}", ex.getMessage());
        return ApiResponse.fail(new BusinessException(ExceptionCode.NOT_VALID_ERROR));
    }

    // 커스텀 예외 핸들러
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<?> handleBusinessException(BusinessException ex) {
        log.error("Catch Business Exception : {}", ex.getMessage());
        return ApiResponse.fail(ex);
    }

    // 예상치 못한 예외 핸들러
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleGeneralException(Exception e) {
        log.error("Catch General Exception : {}", e.getMessage());
        return ApiResponse.fail(new BusinessException(ExceptionCode.INTERNAL_SERVER_ERROR));
    }

}
