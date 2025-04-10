package com.example.newsfeed.friends.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FriendAcceptRequestDto {

    private final Long toUserId; // 팔로잉 된 사람
    private final Long fromUserId; // 팔로우 누룬 사람
}
