package com.example.newsfeed.users.service;

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
}
