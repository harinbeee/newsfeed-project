package com.example.newsfeed.boards.service;

import com.example.newsfeed.boards.dto.BoardPageResponseDto;
import com.example.newsfeed.boards.dto.BoardResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface BoardService {

    BoardResponseDto save(String title, String contents);

    Page<BoardPageResponseDto> findAll(int page, int size, boolean isFriendBoard);

    void delete(Long boardId);

}
