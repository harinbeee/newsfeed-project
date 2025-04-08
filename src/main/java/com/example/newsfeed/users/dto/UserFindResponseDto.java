package com.example.newsfeed.users.dto;

import com.example.newsfeed.users.entity.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserFindResponseDto {

    private String nickname;

    private String profilePicture;

    private String description;

    private LocalDateTime updatedAt;

    public static UserFindResponseDto toDto(User user) { // user > UserFindResponseDto 변환

        return new UserFindResponseDto(
            user.getName(),
            user.getProfilePicture(),
            user.getNickname(),
            user.getUpdatedAt()
        );
    }

}
