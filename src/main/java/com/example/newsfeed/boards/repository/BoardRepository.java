package com.example.newsfeed.boards.repository;


import com.example.newsfeed.boards.entity.Board;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface BoardRepository extends JpaRepository<Board, Long> {


    Optional<Board> findById(Long boardId);

    default Board findByIdOrElseThrow(Long boardId) {
        return findById(boardId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Does not exist id +" + boardId));
    }

}
