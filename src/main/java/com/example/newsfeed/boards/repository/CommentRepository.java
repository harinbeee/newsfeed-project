package com.example.newsfeed.boards.repository;

import com.example.newsfeed.boards.entity.Comment;
import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment findByIdOrElseThrow(Long commentId) {
        return findById(commentId).orElseThrow(
            () -> new BusinessException(ExceptionCode.COMMENT_NOT_FOUND));
    }

    @Query("""
        SELECT count(l) FROM Like l WHERE l.comment.commentId =:commentId
        """)
    Long countCommentLikes(@Param("commentId") Long commentId);

    Optional<List<Comment>> findByUserId(Long userId);


    @Query("SELECT c FROM Comment c JOIN FETCH c.board WHERE c.user.id = :userId")
    Optional<List<Comment>> findByUserIdAndBoard(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.user.id = :userId")
    void deleteCommentByUserId(
        @Param("userId") Long userId);

}
