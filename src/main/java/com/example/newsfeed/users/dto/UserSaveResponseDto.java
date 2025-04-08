package com.example.newsfeed.users.dto;

import com.example.newsfeed.users.entity.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSaveResponseDto {

    private Long id;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private String phone;

    private String profilePicture;

    private String description;

    private boolean isDeleted;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static UserSaveResponseDto toDto(User user) { // user > UserSaveResponseDto 변환

        return new UserSaveResponseDto(
            user.getId(),
            user.getEmail(),
            user.getPassword(),
            user.getName(),
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
