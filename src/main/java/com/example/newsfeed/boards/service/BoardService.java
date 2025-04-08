package com.example.newsfeed.boards.service;

import com.example.newsfeed.boards.dto.BoardResponseDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface BoardService {

    BoardResponseDto save(String title, String contents);

    List<BoardResponseDto> findAll();

    void delete(Long boardId);

}
