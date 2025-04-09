package com.example.newsfeed.friends.repository;

import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.friends.entity.Friend;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findByToUserIdAndFromUserId(Long toUserId, Long fromUserId);

    List<Friend> findByToUserId(Long toUserId); // 로그인 한 유저 아이디 기준 조회

    List<Friend> findByFromUserId(Long fromUserId); // 로그인 한 유저 아이디 기준 조회

    default Friend findByIdElseThrow(Long id) {
        return findById(id).orElseThrow(
            () -> new BusinessException(ExceptionCode.USER_NOT_FOUND));
    }
}
