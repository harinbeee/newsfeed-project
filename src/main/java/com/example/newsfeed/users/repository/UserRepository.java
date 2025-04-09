package com.example.newsfeed.users.repository;

import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.users.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    default User findByIdElseThrow(Long id) {
        return findById(id).orElseThrow(
            () -> new BusinessException(ExceptionCode.USER_NOT_FOUND));
    }

    Optional<User> findByEmail(String email); //회원가입 시 중복인지 체크

    default User findByEmailElseThrow(String email) {
        return findByEmail(email).orElseThrow(
            () -> new BusinessException(ExceptionCode.EMAIL_NOT_FOUND));
    }

    Optional<User> findByUsername(String username);

    default User findByUsernameElseThrow(String username) {
        return findByUsername(username).orElseThrow(
            () -> new BusinessException(ExceptionCode.USER_NOT_FOUND));
    }
}
