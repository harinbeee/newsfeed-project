package com.example.newsfeed.friends.repository;

import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.friends.entity.FriendRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    Optional<FriendRequest> findByToUserIdAndFromUserId(Long toUserId, Long fromUserId);

    default FriendRequest findByTwoIdOrElseThrow(Long toUserId, Long fromUserId) {
        return findByToUserIdAndFromUserId(toUserId, fromUserId).orElseThrow(
            () -> new BusinessException(ExceptionCode.USER_NOT_FOUND));
    }

    Optional<List<FriendRequest>> findByToUserId(Long toUserId); // 로그인 한 유저 아이디 기준 조회

    Optional<List<FriendRequest>> findByFromUserId(Long fromUserId); // 로그인 한 유저 아이디 기준 조회

    @Modifying
    @Query("DELETE FROM FriendRequest f WHERE f.fromUser.id = :userId or f.toUser.id = :userId")
    void deleteFriendByUserId(
        @Param("userId") Long userId);


}
