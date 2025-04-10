package com.example.newsfeed.friends.service;

import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.friends.dto.FriendFindResponseDto;
import com.example.newsfeed.friends.dto.FriendSaveRequestDto;
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
     * 팔로우 저장 요청 서비스
     *
     * @param requestDto 팔로우 요청을 받는 유저 정보가 담긴 {@link FriendSaveRequestDto} 객체
     * @param fromUserId 팔로우 요청을 하는 유저 식별자
     * @return 팔로우 정보가 담긴 {@link FriendSaveResponseDto} 객체
     */
    @Override
    public FriendSaveResponseDto save(FriendSaveRequestDto requestDto, Long fromUserId) {

        //db에 이미 같은 형식의 데이터가 있는지 체크
        if (friendRepository.findByToUserIdAndFromUserId(requestDto.getToUserId(), fromUserId)
            .isPresent()) {
            throw new BusinessException(ExceptionCode.DB_DATA_CONFLICT);
        }

        if (fromUserId.equals(requestDto.getToUserId())) { // 같은 유저를 팔로우 하는지 체크
            throw new BusinessException(ExceptionCode.NOT_VALID_ERROR);
        }

        User toUser = userRepository.findByIdElseThrow(requestDto.getToUserId());
        User fromUser = userRepository.findByIdElseThrow(fromUserId);

        Friend friend = new Friend(toUser, fromUser);

        return FriendSaveResponseDto.toDto(friendRepository.save(friend));

    }

    /**
     * 나를 팔로우하는 유저 조회 서비스
     *
     * @param loginUser 로그인한 유저 식별자
     * @param toUserId  나를 팔로우하는 유저 식별자
     * @return 나를 팔로우하는 유저 정보가 담긴 {@link FriendFindResponseDto} 객체 리스트
     */
    @Override
    public List<FriendFindResponseDto> findByToUserId(Long loginUser, Long toUserId) {

        friendRepository.findByIdElseThrow(toUserId);

        // 로그인한 유저와 path 로 받은 id 값 비교
        if (!loginUser.equals(toUserId)) {
            throw new BusinessException(ExceptionCode.USER_ACCESS_DENIED);
        }

        List<Friend> friendList = friendRepository.findByToUserId(toUserId);
        return friendList.stream()
            .map(FriendFindResponseDto::toDto)
            .toList();

    }

    /**
     * 내가 팔로우하는 유저 조회 서비스
     *
     * @param loginUser  로그인한 유저 식별자
     * @param fromUserId 내가 팔로우하는 유저 식별자
     * @return 내가 팔로우하는 유저 정보가 담긴 {@link FriendFindResponseDto} 객체 리스트
     */
    @Override
    public List<FriendFindResponseDto> findByFromUserId(Long loginUser, Long fromUserId) {

        friendRepository.findByIdElseThrow(fromUserId);

        // 로그인한 유저와 path 로 받은 id 값 비교
        if (!loginUser.equals(fromUserId)) {
            throw new BusinessException(ExceptionCode.USER_ACCESS_DENIED);
        }

        List<Friend> friendList = friendRepository.findByFromUserId(fromUserId);
        return friendList.stream()
            .map(FriendFindResponseDto::toDto)
            .toList();

    }

    /**
     * 팔로우 취소 요청 서비스
     *
     * @param toUserId   팔로우 취소 요청을 받는 유저 식별자
     * @param fromUserId 팔로우 취소 요청을 하는 유저 식별자
     */
    @Override
    public void delete(Long toUserId, Long fromUserId) {

        Friend friend = friendRepository.findByTwoIdOrElseThrow(toUserId, fromUserId);

        friendRepository.delete(friend);

    }

}
