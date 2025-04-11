package com.example.newsfeed.friends.service;

import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.friends.dto.FriendFindResponseDto;
import com.example.newsfeed.friends.dto.FriendSaveRequestDto;
import com.example.newsfeed.friends.dto.FriendSaveResponseDto;
import com.example.newsfeed.friends.entity.Friend;
import com.example.newsfeed.friends.entity.FriendRequest;
import com.example.newsfeed.friends.repository.FriendRepository;
import com.example.newsfeed.friends.repository.FriendRequestRepository;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final FriendRequestRepository requestRepository;
    private final UserRepository userRepository;

    /**
     * 친구 수락 후 친구 테이블에 저장 서비스
     *
     * @param requestDto 친구테이블에 저징 할 유저 정보가 담긴 {@link FriendSaveRequestDto} 객체
     * @param toUserId   친구
     * @return 팔로우 정보가 담긴 {@link FriendSaveResponseDto} 객체
     */
    @Transactional
    @Override
    public FriendSaveResponseDto accept(FriendSaveRequestDto requestDto, Long toUserId) {

        FriendRequest friendRequest = requestRepository.findByToUserIdAndFromUserId(
                // 친구 요청이 db에 있는지 체크
                toUserId, requestDto.getFromUserId())
            .orElseThrow(() -> new BusinessException(ExceptionCode.USER_NOT_FOUND));

        //db에 이미 같은 형식의 데이터가 있는지 체크
        if (friendRepository.findByToUserIdAndFromUserId(requestDto.getFromUserId(), toUserId)
            .isPresent()) {
            throw new BusinessException(ExceptionCode.DB_DATA_CONFLICT);
        }

        if (toUserId.equals(requestDto.getFromUserId())) { // 본인에게 친구 요청 한지 체크
            throw new BusinessException(ExceptionCode.FOLLOW_USER_CONFLICT);
        }

        requestRepository.delete(friendRequest);// 수락했으니 요청 삭제

        User fromUser = userRepository.findByIdElseThrow(requestDto.getFromUserId());
        User toUser = userRepository.findByIdElseThrow(toUserId);

        friendRepository.save(new Friend(fromUser, toUser));// 양방향 저장
        return FriendSaveResponseDto.toDto(friendRepository.save(new Friend(toUser, fromUser)));

    }

    /**
     * 내 친구 목록 반환
     *
     * @param fromUserId 친구 목록 조회 식별자
     * @return 내 친구 목록 담긴 {@link FriendFindResponseDto} 객체 리스트
     */
    @Override
    public List<FriendFindResponseDto> findFriendListByFromUserId(Long fromUserId) {

        Optional<List<Friend>> friendList = friendRepository.findByFromUserId(fromUserId);
        return
            friendList.orElseThrow(() -> new BusinessException(ExceptionCode.FRIEND_NOT_FOUND))
                .stream()
                .map(FriendFindResponseDto::toDto).toList();
    }

    /**
     * 친구 삭제 서비스
     *
     * @param toUserId   친구 유저 식별자
     * @param fromUserId 친구 유저 식별자
     */
    @Override
    public void delete(Long toUserId, Long fromUserId) {

        friendRepository.delete(friendRepository.findByTwoIdOrElseThrow(toUserId, fromUserId));
        friendRepository.delete(friendRepository.findByTwoIdOrElseThrow(fromUserId, toUserId));

    }

}
