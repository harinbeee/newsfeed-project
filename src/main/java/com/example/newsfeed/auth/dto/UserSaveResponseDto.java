package com.example.newsfeed.auth.dto;

import com.example.newsfeed.users.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSaveResponseDto {

    private final Long id;
    private final String email;
    private final String username;
    private final String nickname;
    private final String phone;
    private final String profilePicture;
    private final String description;
    private final boolean isDeleted;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static UserSaveResponseDto toDto(User user) { // user > UserSaveResponseDto 변환
        return new UserSaveResponseDto(
            user.getId(),
            user.getEmail(),
            user.getUsername(),
            user.getNickname(),
            user.getPhone(),
            user.getProfilePicture(),
            user.getDescription(),
            user.isDeleted(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }

}
