package com.example.newsfeed.friends.dto;

import com.example.newsfeed.friends.entity.Friend;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FriendFindResponseDto {

    private final Long id;
    private final Long fromUserId;
    private final Long toUserId;

    public static FriendFindResponseDto toDto(Friend friend) { // Friend > FriendFindResponseDto 변환
        return new FriendFindResponseDto(
            friend.getId(),
            friend.getFromUser().getId(),
            friend.getToUser().getId()
        );
    }

}
