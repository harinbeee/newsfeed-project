package com.example.newsfeed.friends.service;

import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.friends.dto.FriendFindResponseDto;
import com.example.newsfeed.friends.dto.FriendSaveResponseDto;
import com.example.newsfeed.friends.entity.Friend;
import com.example.newsfeed.friends.repository.FriendRepository;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;

    private final UserRepository userRepository;

    /**
     * 팔로우 저장 메소드 팔로우한 유저 아이디와 팔로잉한 유저 아이디 가져와서 저장
     *
     * @param toUserId   팔로우 유저 ID 와 팔로잉 유저 ID
     * @param fromUserId
     * @return 팔로우 유저 ID, 와 팔로잉 유저 ID, 식별자 ID
     */
    @Override
    public FriendSaveResponseDto save(Long toUserId, Long fromUserId) {

        //db에 이미 같은 형식의 데이터가 있는지 체크
        if (friendRepository.findByToUserIdAndFromUserId(toUserId, fromUserId).isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "중복된 데이터가 존재함"
            );
        }

        User toUser = userRepository.findByIdElseThrow(toUserId);
        User fromUser = userRepository.findByIdElseThrow(fromUserId);

        Friend friend = new Friend(
            toUser,
            fromUser
        );

        return FriendSaveResponseDto.toDto(friendRepository.save(friend));

    }


    /**
     * Friend 테이블에서 id 값으로 조회
     *
     * @param toUserId toUserId 받음
     * @return 조회결과
     */
    @Override
    public List<FriendFindResponseDto> findByToUserId(Long toUserId) {

        friendRepository.findByIdElseThrow(toUserId);

        List<Friend> friendList = friendRepository.findByToUserId(toUserId);
        return friendList.stream()
            .map(FriendFindResponseDto::toDto).toList();
    }

    /**
     * Friend 테이블에서 id 값으로 조회
     *
     * @param fromUserId toUserId 받음
     * @return 조회결과
     */
    @Override
    public List<FriendFindResponseDto> findByFromUserId(Long fromUserId) {

        friendRepository.findByIdElseThrow(fromUserId);

        List<Friend> friendList = friendRepository.findByFromUserId(fromUserId);
        return friendList.stream()
            .map(FriendFindResponseDto::toDto).toList();
    }

    /**
     * 팔로우 취소
     *
     * @param toUserId   팔로우 취소 된 사람 아이디
     * @param fromUserId 팔로우 취소 누른 사람 아이디
     */
    @Override
    public void delete(Long toUserId, Long fromUserId) {
        Friend friend = friendRepository
            .findByToUserIdAndFromUserId(toUserId, fromUserId)
            .orElseThrow(() -> new BusinessException(ExceptionCode.USER_NOT_FOUND));
        friendRepository.delete(friend);
    }


}
