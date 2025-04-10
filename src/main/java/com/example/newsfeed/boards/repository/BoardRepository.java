package com.example.newsfeed.boards.repository;


import com.example.newsfeed.boards.dto.BoardPageResponseDto;
import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {


    Optional<Board> findById(Long boardId);

    default Board findByIdOrElseThrow(Long boardId) {
        return findById(boardId).orElseThrow(
            () -> new BusinessException(ExceptionCode.BOARD_NOT_FOUND));
    }

    @Query("""
        SELECT new com.example.newsfeed.boards.dto.BoardPageResponseDto(u.id, u.username, u.nickname, b.title, b.contents, b.createdAt, b.updatedAt)
        FROM Board b LEFT JOIN b.user u LEFT JOIN Friend f ON f.fromUser.id = :userId AND f.toUser = u
        ORDER BY CASE WHEN f.fromUser.id = :userId THEN 0 ELSE 1 END, b.updatedAt DESC
        """)
    Page<BoardPageResponseDto> findAllByFriendPriority(Pageable pageable, Long userId);

    Long boardId(Long boardId);
}
