package com.example.newsfeed.likes.repository;

import com.example.newsfeed.likes.entity.Like;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT count(l) FROM Like l WHERE l.board.boardId = :boardId group by l.board.boardId")
    Optional<Long> findBoardLikeCntByBoardId(
        @Param("boardId") Long boardId
    );

    @Query(""
        + "SELECT count(l) "
        + "FROM Like l "
        + "WHERE l.comment.commentId = :commentId "
        + "and l.board.boardId = :boardId "
        + "group by l.comment.commentId")
    Optional<Long> findCommentLikeCntByCommentIdAndBoardId(
        @Param("commentId") Long commentId,
        @Param("boardId") Long boardId
    );
}
