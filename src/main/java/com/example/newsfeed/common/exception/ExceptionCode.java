package com.example.newsfeed.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
    // 400
    NOT_VALID_ERROR(400, "", "Validation Exception 발생"),
    EMAIL_NOT_FOUND(400, "", "이메일이 존재하지 않습니다."),
    EMAIL_ALREADY_USED(400, "", "이미 사용중인 이메일입니다."),
    PASSWORD_INVALID(400, "", "비밀번호가 일치하지 않습니다."),
    PASSWORD_NOT_CHANGED(400, "", "기존 비밀번호와 새로운 비밀번호가 일치합니다."),
    BOARD_UPDATE_FORBIDDEN(400, "", "작성자 본인만 수정할 수 있습니다."),
    BOARD_DELETE_FORBIDDEN(400, "", "작성자 본인만 삭제할 수 있습니다."),
    LOGIN_FORBIDDEN(400, "", "탈퇴한 회원은 로그인 하실 수 없습니다."),
    SIGNUP_FORBIDDEN(400, "", "탈퇴한 회원은 재가입 할 수 없습니다."),
    ALREADY_LOGIN(400, "", "이미 로그인 되어있습니다."),

    // 401
    NOT_LOGIN_ERROR(401, "", "로그인이 필요합니다."),

    // 403
    USER_ACCESS_DENIED(403, "", "로그인한 유저의 id와 일치하지 않습니다."),

    // 404
    BOARD_NOT_FOUND(404, "", "게시글이 존재하지 않습니다."),
    USER_NOT_FOUND(404, "", "유저 정보를 찾을 수 없습니다."),
    Like_NOT_FOUND(404, "", "좋아요 테이블 정보를 찾을 수 없습니다."),

    //409
    DB_DATA_CONFLICT(409, "", "동일한 값이 존재 합니다");

    private final int status;
    private final String code;
    private final String message;

}