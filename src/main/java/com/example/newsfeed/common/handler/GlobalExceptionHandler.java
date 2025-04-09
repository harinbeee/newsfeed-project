package com.example.newsfeed.common.handler;

import com.example.newsfeed.common.exception.ErrorResponse;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.common.exception.UserAccessDeniedException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> responseStatusExceptionHandler(
        ResponseStatusException re) {

        final ErrorResponse response = ErrorResponse.of(
            ExceptionCode.NOT_VALID_ERROR, re.getReason());

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    /**
     * > @Validated 검증에 걸렸을 때 오류 클라이언트로 보내주는 메소드
     *
     * @param ce exception 에러정보 가지고 있음
     * @return 에러메시지 status, code, message, reason
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> constraintViolationExceptionHandler(
        ConstraintViolationException ce
    ) {

        final ErrorResponse response = ErrorResponse.of(
            ExceptionCode.NOT_VALID_ERROR, ce.getMessage());

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));

    }

    /**
     * > @valid 유효성 검증에 실패했을 경우 발생하는 예외 처리
     *
     * @param ex exception 에러정보 가지고 있음
     * @return 에러메시지 status, code, message, reason
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException ex) {

        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();

        // reason 상세 오류메시지 세팅
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            stringBuilder.append(fieldError.getField()).append(":"); // 필드 아이디
            stringBuilder.append(fieldError.getDefaultMessage()); // 오류 메시지
            stringBuilder.append(", ");
        }

        final ErrorResponse response = ErrorResponse.of(
            ExceptionCode.NOT_VALID_ERROR, String.valueOf(stringBuilder));

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));

    }

    @ExceptionHandler(UserAccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleUserAccessDeniedException(
        UserAccessDeniedException ex) {

        final ErrorResponse response = ErrorResponse.of(
            ExceptionCode.USER_ACCESS_DENIED, ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }


}
