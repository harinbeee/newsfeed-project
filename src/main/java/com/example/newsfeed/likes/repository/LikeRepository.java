package com.example.newsfeed.likes.repository;

import com.example.newsfeed.likes.entity.Like;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {

    /**
     * 중복체크 하는 쿼리 commentID 가 null 이면 마지막 and 조건이 true가 되어 동작함 Null 이 이닌 경우 > 마지막 and 조건의 앞 부분이
     * false 이므로 무시되고 or 뒤의 비교부분만 실행해서 비교
     *
     * @param userId
     * @param boardId
     * @param commentId
     * @return
     */
    @Query(""
        + "SELECT l "
        + "FROM Like l "
        + "WHERE l.board.boardId = :boardId "
        + " AND l.userId = :userId "
        + " AND (:commentId "
        + " IS Null and l.comment.commentId is null) or l.comment.commentId = :commentId "
    )
    Optional<Like> findByUserIdAndBoardIdAndCommentId(
        @Param("userId") Long userId,
        @Param("boardId") Long boardId,
        @Param("commentId") Long commentId
    );

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Like l WHERE l.board.boardId = :boardId")
    void deleteLikeByBoardBoardId(@Param("boardId") Long boardId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Like l WHERE l.board.boardId = :boardId and l.comment.commentId = :commentId")
    void deleteLikeByBoardBoardIdAndCommentCommentId(
        @Param("boardId") Long boardId,
        @Param("commentId") Long commentId);
}
