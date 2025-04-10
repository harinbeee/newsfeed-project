package com.example.newsfeed.friends.dto;

import com.example.newsfeed.friends.entity.FriendRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FriendAcceptResponseDto {

    private final Long id;
    private final Long fromUserId;
    private final Long toUserId;

    public static FriendAcceptResponseDto toDto(
        FriendRequest friendRequest) { // FriendRequest > FriendAcceptResponseDto 변환
        return new FriendAcceptResponseDto(
            friendRequest.getId(),
            friendRequest.getFromUser().getId(),
            friendRequest.getToUser().getId()
        );
    }
}
