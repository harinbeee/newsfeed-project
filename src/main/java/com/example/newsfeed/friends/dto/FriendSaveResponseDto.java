package com.example.newsfeed.friends.dto;

import com.example.newsfeed.friends.entity.Friend;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FriendSaveResponseDto {

    private final Long id;
    private final Long fromUserId;
    private final Long toUserId;

    public static FriendSaveResponseDto toDto(Friend friend) { // Friend > FriendSaveResponseDto 변환
        return new FriendSaveResponseDto(
            friend.getId(),
            friend.getFromUser().getId(),
            friend.getToUser().getId()
        );
    }

}
