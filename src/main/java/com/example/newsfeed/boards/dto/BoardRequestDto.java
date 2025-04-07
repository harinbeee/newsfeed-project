package com.example.newsfeed.boards.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {

    private final String title;

    private final String contents;

    public BoardRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
