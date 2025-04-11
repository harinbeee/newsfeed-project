package com.example.newsfeed.friends.service;

import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.friends.dto.FriendAcceptRequestDto;
import com.example.newsfeed.friends.dto.FriendAcceptResponseDto;
import com.example.newsfeed.friends.dto.FriendFindResponseDto;
import com.example.newsfeed.friends.dto.FriendRequestFindResponseDto;
import com.example.newsfeed.friends.entity.FriendRequest;
import com.example.newsfeed.friends.repository.FriendRepository;
import com.example.newsfeed.friends.repository.FriendRequestRepository;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {

    private final FriendRepository friendRepository;
    private final FriendRequestRepository requestRepository;
    private final UserRepository userRepository;

    @Override
    public FriendAcceptResponseDto save(FriendAcceptRequestDto requestDto, Long fromUserId) {

        //db에 이미 같은 형식의 데이터가 있는지 체크
        isUsed(requestRepository.findByToUserIdAndFromUserId(requestDto.getToUserId(), fromUserId)
            .isPresent(), ExceptionCode.DB_DATA_CONFLICT);
        //이미 친구 인지 체크
        isUsed(friendRepository.findByToUserIdAndFromUserId(requestDto.getToUserId(), fromUserId)
            .isPresent(), ExceptionCode.FRIEND_ALREADY_USED);

        if (fromUserId.equals(requestDto.getToUserId())) { // 같은 유저를 팔로우 하는지 체크
            throw new BusinessException(ExceptionCode.FOLLOW_USER_CONFLICT);
        }

        User toUser = userRepository.findByIdElseThrow(requestDto.getToUserId());
        User fromUser = userRepository.findByIdElseThrow(fromUserId);

        FriendRequest friendRequest = new FriendRequest(toUser, fromUser);

        return FriendAcceptResponseDto.toDto(requestRepository.save(friendRequest));
    }

    /**
     * 나에게 친구 요청 보낸 유저 조회 서비스
     *
     * @param toUserId 나를 팔로우하는 유저 식별자
     * @return 나를 팔로우하는 유저 정보가 담긴 {@link FriendFindResponseDto} 객체 리스트
     */
    @Override
    public List<FriendRequestFindResponseDto> findByToUserId(Long toUserId) {

        List<FriendRequest> friendList = requestRepository.findByToUserId(toUserId);
        return friendList.stream()
            .map(FriendRequestFindResponseDto::toDto)
            .toList();

    }

    /**
     * 내가 친구 요청 보낸 유저 조회 서비스
     *
     * @param fromUserId 내가 팔로우하는 유저 식별자
     * @return 내가 팔로우하는 유저 정보가 담긴 {@link FriendFindResponseDto} 객체 리스트
     */
    @Override
    public List<FriendRequestFindResponseDto> findByFromUserId(Long fromUserId) {

        List<FriendRequest> friendList = requestRepository.findByFromUserId(fromUserId);
        return friendList.stream()
            .map(FriendRequestFindResponseDto::toDto)
            .toList();

    }

    /**
     * 친구 요청 거절 서비스
     *
     * @param toUserId   친구 요청 거절 하는유저 식별자
     * @param fromUserId 친구 요청을 한 유저 식별자
     */
    @Override
    public void reject(Long fromUserId, Long toUserId) {

        requestRepository.delete(requestRepository.findByTwoIdOrElseThrow(toUserId, fromUserId));

    }

    @Override
    public void isUsed(boolean isUsed, ExceptionCode exceptionCode) {
        //db에 이미 같은 형식의 데이터가 있는지 체크
        if (isUsed) {
            throw new BusinessException(exceptionCode);
        }
    }
}
