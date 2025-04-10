package com.example.newsfeed.friends.dto;

import com.example.newsfeed.friends.entity.FriendRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FriendRequestFindResponseDto {

    private final Long id;
    private final Long fromUserId;
    private final Long toUserId;

    public static FriendRequestFindResponseDto toDto(
        FriendRequest friendRequest) { // Friend > FriendRequestFindResponseDto 변환
        return new FriendRequestFindResponseDto(
            friendRequest.getId(),
            friendRequest.getFromUser().getId(),
            friendRequest.getToUser().getId()
        );
    }

}
