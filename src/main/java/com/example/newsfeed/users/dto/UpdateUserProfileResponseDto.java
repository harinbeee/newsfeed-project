package com.example.newsfeed.users.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateUserProfileResponseDto {

    private final Long userId;
    private final String username;
    private final String nickname;
    private final String phone;
    private final String profile_picture;
    private final String description;
    private final String createdAt;
    private final String updatedAt;

}
