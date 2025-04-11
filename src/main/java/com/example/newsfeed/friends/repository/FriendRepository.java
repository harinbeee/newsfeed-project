package com.example.newsfeed.friends.repository;

import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.friends.entity.Friend;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findByToUserIdAndFromUserId(Long toUserId, Long fromUserId);


    default Friend findByIdElseThrow(Long id) {
        return findById(id).orElseThrow(
            () -> new BusinessException(ExceptionCode.USER_NOT_FOUND));
    }

    default Friend findByTwoIdOrElseThrow(Long toUserId, Long fromUserId) {
        return findByToUserIdAndFromUserId(toUserId, fromUserId).orElseThrow(
            () -> new BusinessException(ExceptionCode.USER_NOT_FOUND));
    }

    Optional<List<Friend>> findByFromUserId(Long userId); // 로그인 한 유저 아이디 기준 조회

    @Modifying
    @Query("DELETE FROM Friend f WHERE f.fromUser.id = :userId or f.toUser.id = :userId")
    void deleteFriendByUserId(
        @Param("userId") Long userId);

}
