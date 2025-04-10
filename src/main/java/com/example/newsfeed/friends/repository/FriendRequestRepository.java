package com.example.newsfeed.friends.repository;

import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.friends.entity.FriendRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    Optional<FriendRequest> findByToUserIdAndFromUserId(Long toUserId, Long fromUserId);

    List<FriendRequest> findByToUserId(Long toUserId); // 로그인 한 유저 아이디 기준 조회

    List<FriendRequest> findByFromUserId(Long fromUserId); // 로그인 한 유저 아이디 기준 조회


    default FriendRequest findByIdElseThrow(Long id) {
        return findById(id).orElseThrow(
            () -> new BusinessException(ExceptionCode.USER_NOT_FOUND));
    }

    default FriendRequest findByTwoIdOrElseThrow(Long toUserId, Long fromUserId) {
        return findByToUserIdAndFromUserId(toUserId, fromUserId).orElseThrow(
            () -> new BusinessException(ExceptionCode.USER_NOT_FOUND));
    }

}
