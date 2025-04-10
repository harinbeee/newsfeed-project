package com.example.newsfeed.friends.service;

import com.example.newsfeed.friends.dto.FriendAcceptRequestDto;
import com.example.newsfeed.friends.dto.FriendAcceptResponseDto;
import com.example.newsfeed.friends.dto.FriendRequestFindResponseDto;
import java.util.List;

public interface FriendRequestService {

    /**
     * 친구 요청 수락 서비스
     *
     * @param requestDto
     * @param fromUserId
     * @return
     */
    FriendAcceptResponseDto save(FriendAcceptRequestDto requestDto, Long fromUserId);

    List<FriendRequestFindResponseDto> findByToUserId(Long toUserId);


    List<FriendRequestFindResponseDto> findByFromUserId(Long fromUserId);


    void reject(Long toUserId, Long fromUserId);


}
