package com.example.newsfeed.friends.dto;

import com.example.newsfeed.friends.entity.Friend;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendFindResponseDto {

    private Long id;

    private Long fromUserId;

    private Long toUserId;

    public static FriendFindResponseDto toDto(Friend friend) { // Friend > FriendFindResponseDto 변환
        return new FriendFindResponseDto(
            friend.getId(),
            friend.getFromUser().getId(),
            friend.getToUser().getId()
        );
    }
}
