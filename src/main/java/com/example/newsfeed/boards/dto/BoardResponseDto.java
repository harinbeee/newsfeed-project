package com.example.newsfeed.boards.dto;

import lombok.Getter;

@Getter
public class BoardResponseDto {

    private final Long id;

    private final String username;

    private final String title;

    private final String contents;

    public BoardResponseDto(Long id, String username, String title, String contents) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.contents = contents;
    }
}
