package com.example.newsfeed.friends.dto;

import lombok.Getter;

@Getter
public class FriendSaveRequestDto {

    private Long toUserId; // 팔로잉 된 사람

    private Long fromUserId; // 팔로우 누룬 사람

}
