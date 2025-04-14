package com.example.newsfeed.users.service;

import com.example.newsfeed.users.dto.UpdatePasswordRequestDto;
import com.example.newsfeed.users.dto.UpdateUserProfileRequestDto;
import com.example.newsfeed.users.dto.UpdateUserProfileResponseDto;
import com.example.newsfeed.users.dto.UserDeleteRequsetDto;
import com.example.newsfeed.users.dto.UserFindResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    UserFindResponseDto find(Long userId);

    /**
     * 유저 프로필 수정 메소드
     *
     * @param userId 유저 식별자 ID
     * @return 수정된 유저 정보가 담겨있는 응답 DTO 객체
     */
    UpdateUserProfileResponseDto update(Long userId,
        UpdateUserProfileRequestDto requestDto);

    /**
     * 회원 탈퇴 메소드
     *
     * @param requestDto 탈퇴 요청 {@link UserDeleteRequsetDto} 객체
     * @param userId     유저 식별자
     * @param request    세션 정보
     * @param response   세션 응답
     */
    void withdraw(UserDeleteRequsetDto requestDto, Long userId, HttpServletRequest request,
        HttpServletResponse response);

    /**
     * 비밀번호 수정 메소드
     *
     * @param userId     유저 식별자 ID
     * @param requestDto 클라이언트 요청 정보가 담겨있는 요청 DTO 객체
     */
    void updatePassword(Long userId, UpdatePasswordRequestDto requestDto);

}
