package com.example.newsfeed.common.handler;

import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ErrorResponse;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.common.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String VALIDATION_ERROR_DELIMITER = ", ";

    @ExceptionHandler(value = {ResponseStatusException.class,
        ConstraintViolationException.class,
        MethodArgumentNotValidException.class})
    public ApiResponse<ErrorResponse> responseStatusExceptionHandler(
        Exception ex
    ) {
        log.error("Catch Exception : {}", ex.getMessage());
        return ApiResponse.fail(new BusinessException(ExceptionCode.NOT_VALID_ERROR));
    }

    @ExceptionHandler(BusinessException.class)
    public ApiResponse<ErrorResponse> handleBusinessException(BusinessException ex) {
        log.error("Catch Business Exception : {}", ex.getMessage());
        return ApiResponse.fail(ex);
    }

//    private ApiResponse<ErrorResponse> createErrorResponse(ExceptionCode code, String reason) {
//        ErrorResponse response = ErrorResponse.of(code);
//        return new ApiResponse<>();
//    }

    private String createValidationErrorMessage(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
            .map(error -> String.format("%s:%s", error.getField(), error.getDefaultMessage()))
            .collect(Collectors.joining(VALIDATION_ERROR_DELIMITER));
    }

}
