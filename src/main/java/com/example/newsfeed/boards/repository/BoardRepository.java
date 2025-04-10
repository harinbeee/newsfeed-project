package com.example.newsfeed.boards.repository;


import com.example.newsfeed.boards.dto.BoardPageResponseDto;
import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    default Board findByIdOrElseThrow(Long boardId) {
        return findById(boardId).orElseThrow(
            () -> new BusinessException(ExceptionCode.BOARD_NOT_FOUND));
    }

    @Query("""
        SELECT new com.example.newsfeed.boards.dto.BoardPageResponseDto(u.id, u.username, u.nickname, b.title, b.contents, b.boardImage, COUNT(l.userId), COUNT(c), b.createdAt, b.updatedAt)
        FROM Board b LEFT JOIN b.user u LEFT JOIN Friend f ON f.fromUser.id = :userId AND f.toUser = u LEFT JOIN Comment c ON c.board = b LEFT JOIN Like l ON l.board = b
        GROUP BY b
        ORDER BY CASE WHEN f.fromUser.id = :userId THEN 0 ELSE 1 END, COUNT(l.userId) DESC, b.updatedAt DESC
        """)
    Page<BoardPageResponseDto> findAllByFriendPriority(Pageable pageable, Long userId);

    @Query("""
        SELECT new com.example.newsfeed.boards.dto.BoardPageResponseDto(u.id, u.username, u.nickname, b.title, b.contents, b.boardImage, COUNT(l.userId), COUNT(c), b.createdAt, b.updatedAt)
        FROM Board b LEFT JOIN b.user u LEFT JOIN Friend f ON f.fromUser.id = :userId AND f.toUser = u LEFT JOIN Comment c ON c.board = b LEFT JOIN Like l ON l.board = b
        GROUP BY b
        ORDER BY COUNT(l.userId) DESC, b.updatedAt DESC
        """)
    Page<BoardPageResponseDto> findAllByLikePriority(Pageable pageable, Long userId);

    @Query("""
        SELECT new com.example.newsfeed.boards.dto.BoardPageResponseDto(u.id, u.username, u.nickname, b.title, b.contents, b.boardImage, COUNT(l.userId), COUNT(c),b.createdAt, b.updatedAt)
        FROM Board b LEFT JOIN b.user u LEFT JOIN Friend f ON f.fromUser.id = :userId AND f.toUser = u LEFT JOIN Comment c ON c.board = b LEFT JOIN Like l ON l.board = b
        GROUP BY b
        ORDER BY b.updatedAt DESC
        """)
    Page<BoardPageResponseDto> findAllByUpdatedAtPriority(Pageable pageable, Long userId);

    @Query("""
        SELECT count(l) FROM Like l WHERE l.board.boardId =:boardId AND l.comment IS NULL
        """)
    Long countBoardLikes(@Param("boardId") Long boardId);
}
