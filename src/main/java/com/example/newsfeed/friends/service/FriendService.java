package com.example.newsfeed.friends.service;

import com.example.newsfeed.friends.dto.FriendFindResponseDto;
import com.example.newsfeed.friends.dto.FriendSaveRequestDto;
import com.example.newsfeed.friends.dto.FriendSaveResponseDto;

public interface FriendService {

    FriendSaveResponseDto save(FriendSaveRequestDto requestDto);

    void delete(Long toUserId, Long fromUserId);

    FriendFindResponseDto findByToUserId(Long toUserId);

    FriendFindResponseDto findByFromUserId(Long fromUserId);
}
