package com.example.newsfeed.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserAccessDeniedException extends RuntimeException {

    private final ExceptionCode exceptionCode;

}
