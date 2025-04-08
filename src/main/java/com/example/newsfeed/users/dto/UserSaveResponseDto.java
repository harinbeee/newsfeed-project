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

<<<<<<< HEAD
  private String name;
=======
    private String username;
>>>>>>> 2d97b16dacc3024bace2a0714f79fac446a7c7ad

  private String nickname;

  private String phone;

  private String profilePicture;

  private String description;

  private boolean isDeleted;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public static UserSaveResponseDto toDto(User user) { // user > UserSaveResponseDto 변환

<<<<<<< HEAD
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
=======
        return new UserSaveResponseDto(
            user.getId(),
            user.getEmail(),
            user.getPassword(),
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
>>>>>>> 2d97b16dacc3024bace2a0714f79fac446a7c7ad
}
