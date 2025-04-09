package com.example.newsfeed.friends.repository;

import com.example.newsfeed.friends.entity.Friend;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findByToUserIdAndFromUserId(Long toUserId, Long fromUserId);

    default Friend findByIdElseThrow(Long id) {
        return findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "아이디가 존재하지 않습니다."));
    }
}
