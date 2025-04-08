package com.example.newsfeed.users.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateUserProfileRequestDto {

    private final String nickname;
    private final String phone;
    private final String profilePicture;
    private final String description;

}
