package com.example.newsfeed.boards.service;

import com.example.newsfeed.boards.dto.BoardResponseDto;

public interface BoardService {

    BoardResponseDto save(String title, String contents);

}
