package com.example.newsfeed.boards.repository;

import com.example.newsfeed.boards.entity.Comment;
import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment findByIdOrElseThrow(Long commentId) {
        return findById(commentId).orElseThrow(
            () -> new BusinessException(ExceptionCode.COMMENT_NOT_FOUND));
    }

}
