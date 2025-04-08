package com.example.newsfeed.boards.repository;


import com.example.newsfeed.boards.dto.BoardPageResponseDto;
import com.example.newsfeed.boards.entity.Board;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface BoardRepository extends JpaRepository<Board, Long> {


    Optional<Board> findById(Long boardId);

    default Board findByIdOrElseThrow(Long boardId) {
        return findById(boardId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Does not exist id +" + boardId));
    }

    @Query("""
                SELECT new com.example.newsfeed.boards.dto.BoardPageResponseDto(u.id, u.username, b.title, b.contents, b.createdAt, b.updatedAt)
                FROM Board b LEFT JOIN b.user u
        
        
        
        
        """)
    Page<BoardPageResponseDto> findAllByFriendPriority(Pageable pageable);
}
