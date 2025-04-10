package com.example.newsfeed.friends.service;

import com.example.newsfeed.friends.dto.FriendFindResponseDto;
import com.example.newsfeed.friends.dto.FriendSaveRequestDto;
import com.example.newsfeed.friends.dto.FriendSaveResponseDto;
import java.util.List;

public interface FriendService {

    /**
     * 팔로우 저장 요청 서비스
     *
     * @param requestDto 팔로우 요청을 받는 유저 정보가 담긴 {@link FriendSaveRequestDto} 객체
     * @param toUserId   팔로우 요청을 하는 유저 식별자
     * @return 팔로우 정보가 담긴 {@link FriendSaveResponseDto} 객체
     */
    FriendSaveResponseDto accept(FriendSaveRequestDto requestDto, Long toUserId);


    /**
     * 내가 팔로우하는 유저 조회 서비스
     *
     * @param fromUserId 내가 팔로우하는 유저 식별자
     * @return 내가 팔로우하는 유저 정보가 담긴 {@link FriendFindResponseDto} 객체 리스트
     */
    List<FriendFindResponseDto> findFriendListByFromUserId(Long fromUserId);

    /**
     * 팔로우 취소 요청 서비스
     *
     * @param toUserId   팔로우 취소 요청을 받는 유저 식별자
     * @param fromUserId 팔로우 취소 요청을 하는 유저 식별자
     */
    void delete(Long toUserId, Long fromUserId);

}
