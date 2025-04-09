package com.example.newsfeed.friends.service;

import com.example.newsfeed.friends.dto.FriendFindResponseDto;
import com.example.newsfeed.friends.dto.FriendSaveResponseDto;
import java.util.List;

public interface FriendService {

    FriendSaveResponseDto save(Long toUserId, Long fromUserId);

    void delete(Long toUserId, Long fromUserId);

    List<FriendFindResponseDto> findByToUserId(Long toUserId);

    List<FriendFindResponseDto> findByFromUserId(Long fromUserId);

}
