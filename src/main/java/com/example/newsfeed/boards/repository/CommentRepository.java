package com.example.newsfeed.boards.repository;

import com.example.newsfeed.boards.entity.Comment;
import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findById(Long commentId);

    default Comment findByIdOrElseThrow(Long commentId) {
        return findById(commentId).orElseThrow(
            () -> new BusinessException(ExceptionCode.BOARD_NOT_FOUND));
        //---> 익셉션 추가 필요
    }
}
