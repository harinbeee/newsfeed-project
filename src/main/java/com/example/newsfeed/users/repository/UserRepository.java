package com.example.newsfeed.users.repository;

import com.example.newsfeed.users.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface UserRepository extends JpaRepository<User, Long> {

    default User findByIdElseThrow(Long id) {
        return findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유저 식별자가 존재하지 않음"));
    }

    Optional<User> findByEmail(String email);
}
