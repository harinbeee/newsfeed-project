package com.example.newsfeed.boards.service;

import com.example.newsfeed.boards.dto.BoardResponseDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface BoardService {

    BoardResponseDto save(Long userId, String title, String contents);

    List<BoardResponseDto> findAll();


    BoardResponseDto update(Long userId, Long boardId, String title, String contents);

    void delete(Long userId, Long boardId);

}
