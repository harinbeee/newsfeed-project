package com.example.newsfeed.users.dto;

import com.example.newsfeed.users.entity.User;
import java.time.LocalDateTime;
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
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static UpdateUserProfileResponseDto toDto(User user) {
        return new UpdateUserProfileResponseDto(
            user.getId(),
            user.getUsername(),
            user.getNickname(),
            user.getPhone(),
            user.getProfilePicture(),
            user.getDescription(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }

}
