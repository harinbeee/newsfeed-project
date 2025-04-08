package com.example.newsfeed.friends.service;

import com.example.newsfeed.friends.dto.FriendFindResponseDto;
import com.example.newsfeed.friends.dto.FriendSaveRequestDto;
import com.example.newsfeed.friends.dto.FriendSaveResponseDto;
import com.example.newsfeed.friends.entity.Friend;
import com.example.newsfeed.friends.repository.FriendRepository;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;

    private final UserRepository userRepository;


    /**
     * 팔로우 저장 메소드 팔로우한 유저 아이디와 팔로잉한 유저 아이디 가져와서 저장
     *
     * @param requestDto 팔로우 유저 ID 와 팔로잉 유저 ID
     * @return 팔로우 유저 ID, 와 팔로잉 유저 ID, 식별자 ID
     */
    @Override
    public FriendSaveResponseDto save(FriendSaveRequestDto requestDto) {

        User toUser = userRepository.findByIdElseThrow(requestDto.getToUserId());
        User fromUser = userRepository.findByIdElseThrow(requestDto.getFromUserId());

        Friend friend = new Friend(
            toUser,
            fromUser
        );

        return FriendSaveResponseDto.toDto(friendRepository.save(friend));

    }

    @Override
    public void delete(Long toUserId, Long fromUserId) {
        Friend friend = friendRepository
            .findByToUserIdAndFromUserId(toUserId, fromUserId)
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "유저 식별자가 존재하지 않음"
                )
            );
        friendRepository.delete(friend);
    }

    @Override
    public FriendFindResponseDto findByToUserId(Long toUserId) {
        return null;
    }

    @Override
    public FriendFindResponseDto findByIdFromUserId(Long fromUserId) {
        return null;
    }

}
