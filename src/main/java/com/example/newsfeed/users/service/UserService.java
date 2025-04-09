package com.example.newsfeed.users.service;

import com.example.newsfeed.users.dto.UpdatePasswordRequestDto;
import com.example.newsfeed.users.dto.UpdateUserProfileRequestDto;
import com.example.newsfeed.users.dto.UpdateUserProfileResponseDto;
import com.example.newsfeed.users.dto.UserFindResponseDto;
import com.example.newsfeed.users.dto.UserSaveRequestDto;
import com.example.newsfeed.users.dto.UserSaveResponseDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<UserFindResponseDto> find(Long userId);

    ResponseEntity<UserSaveResponseDto> save(UserSaveRequestDto requestDto);

    void findByEmail(@Email @NotBlank String email);

    /**
     * 유저 프로필 수정 메소드
     *
     * @param userId 유저 식별자 ID
     * @return 수정된 유저 정보가 담겨있는 응답 DTO 객체
     */
    UpdateUserProfileResponseDto update(Long userId, UpdateUserProfileRequestDto requestDto);

    /**
     * 비밀번호 수정 메소드
     *
     * @param userId     유저 식별자 ID
     * @param requestDto 클라이언트 요청 정보가 담겨있는 요청 DTO 객체
     */
    void updatePassword(Long userId, UpdatePasswordRequestDto requestDto);

}
