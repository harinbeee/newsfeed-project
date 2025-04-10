package com.example.newsfeed.likes.repository;

import com.example.newsfeed.likes.entity.Like;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT count(l) FROM Like l WHERE l.board.boardId = :boardId or l.comment.commentId = :commentId")
    Optional<Long> findByBoardIdOrCommentId(
        @Param("boardId") Long boardId,
        @Param("commentId") Long commentId
    );
}
