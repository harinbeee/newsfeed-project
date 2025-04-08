package com.example.newsfeed.boards.service;

import com.example.newsfeed.boards.dto.BoardPageResponseDto;
import com.example.newsfeed.boards.dto.BoardResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface BoardService {

    BoardResponseDto save(Long userId, String title, String contents);

    Page<BoardPageResponseDto> findAll(int page, int size, boolean isFriendBoard);


    BoardResponseDto update(Long userId, Long boardId, String title, String contents);

    void delete(Long userId, Long boardId);

}
