package com.example.newsfeed.common.handler;

import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ErrorResponse;
import com.example.newsfeed.common.exception.ExceptionCode;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String VALIDATION_ERROR_DELIMITER = ", ";

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> responseStatusExceptionHandler(
        ResponseStatusException ex
    ) {
        log.error("Catch Response Status Exception : {}", ex.getMessage());
        return createErrorResponse(ExceptionCode.NOT_VALID_ERROR, ex.getReason());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> constraintViolationExceptionHandler(
        ConstraintViolationException ex
    ) {
        log.error("Catch Constraint Violation Exception : {}", ex.getMessage());
        return createErrorResponse(ExceptionCode.NOT_VALID_ERROR, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException ex
    ) {
        String errorMessage = createValidationErrorMessage(ex.getBindingResult());
        log.error(errorMessage, ex.getMessage());
        return createErrorResponse(ExceptionCode.NOT_VALID_ERROR, errorMessage);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        log.error("Catch Business Exception : {}", ex.getMessage());
        return createErrorResponse(ex.getExceptionCode(), ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(ExceptionCode code, String reason) {
        ErrorResponse response = ErrorResponse.of(code, reason);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    private String createValidationErrorMessage(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
            .map(error -> String.format("%s:%s", error.getField(), error.getDefaultMessage()))
            .collect(Collectors.joining(VALIDATION_ERROR_DELIMITER));
    }

}
