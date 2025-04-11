package com.example.newsfeed.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
    // 400
    NOT_VALID_ERROR(400, HttpStatus.BAD_REQUEST, "Validation Exception 발생"),
    EMAIL_NOT_FOUND(400, HttpStatus.BAD_REQUEST, "이메일이 존재하지 않습니다."),
    EMAIL_ALREADY_USED(400, HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다."),
    FRIEND_ALREADY_USED(400, HttpStatus.BAD_REQUEST, "이미 친구 입니다."),
    FRIEND_NOT_FOUND(400, HttpStatus.BAD_REQUEST, "친구가 없습니다."),
    FRIEND_FROM_REQUEST(400, HttpStatus.BAD_REQUEST, "내가 보낸 친구 요청이 없습니다."),
    FRIEND_TO_REQUEST(400, HttpStatus.BAD_REQUEST, "나에게 온 친구 요청이 없습니다."),
    PASSWORD_INVALID(400, HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    PASSWORD_NOT_CHANGED(400, HttpStatus.BAD_REQUEST, "기존 비밀번호와 새로운 비밀번호가 일치합니다."),
    BOARD_UPDATE_FORBIDDEN(400, HttpStatus.BAD_REQUEST, "작성자 본인만 수정할 수 있습니다."),
    BOARD_DELETE_FORBIDDEN(400, HttpStatus.BAD_REQUEST, "작성자 본인만 삭제할 수 있습니다."),
    LOGIN_FORBIDDEN(400, HttpStatus.BAD_REQUEST, "탈퇴한 회원은 로그인 하실 수 없습니다."),
    SIGNUP_FORBIDDEN(400, HttpStatus.BAD_REQUEST, "탈퇴한 회원은 재가입 할 수 없습니다."),
    ALREADY_LOGIN(400, HttpStatus.BAD_REQUEST, "이미 로그인 되어있습니다."),
    BOARD_SELFLIKE_BLOCK(400, HttpStatus.BAD_REQUEST, "자신이 작성한 게시글에 좋아요를 누를 수 없습니다."),
    COMMENT_SELFLIKE_BLOCK(400, HttpStatus.BAD_REQUEST, "자신이 작성한 댓글에 좋아요를 누를 수 없습니다."),
    DUPLICATED_LIKE_BLOCK(400, HttpStatus.BAD_REQUEST, "좋아요를 중복해서 누를 수 없습니다."),

    // 401
    NOT_LOGIN_ERROR(401, HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),

    // 403
    USER_ACCESS_DENIED(403, HttpStatus.FORBIDDEN, "로그인한 유저의 id와 일치하지 않습니다."),

    // 404
    BOARD_NOT_FOUND(404, HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."),
    COMMENT_NOT_FOUND(404, HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."),
    USER_NOT_FOUND(404, HttpStatus.NOT_FOUND, "유저 정보를 찾을 수 없습니다."),
    LIKE_NOT_FOUND(404, HttpStatus.NOT_FOUND, "좋아요 테이블 정보를 찾을 수 없습니다."),
    SORT_TYPE_NOT_FOUND(404, HttpStatus.NOT_FOUND, "지원하지 않는 정렬 타입 입니다."),

    //409
    DB_DATA_CONFLICT(409, HttpStatus.CONFLICT, "동일한 값이 존재 합니다"),
    FOLLOW_USER_CONFLICT(409, HttpStatus.CONFLICT, "본인에게는 팔로우 할 수 없습니다"),

    // 500
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");


    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

}