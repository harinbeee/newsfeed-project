package com.example.newsfeed.friends.dto;

import com.example.newsfeed.friends.entity.Friend;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FriendSaveResponseDto {

    private Long id;

    private Long fromUserId;

    private Long toUserId;

    public static FriendSaveResponseDto toDto(Friend friend) { // Friend > FriendSaveResponseDto 변환
        return new FriendSaveResponseDto(
            friend.getId(),
            friend.getFromUserId(),
            friend.getToUser().getId()
        );
    }
}
