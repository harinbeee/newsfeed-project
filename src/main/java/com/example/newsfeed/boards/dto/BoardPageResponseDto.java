package com.example.newsfeed.boards.dto;

import com.example.newsfeed.boards.entity.Board;
import com.fasterxml.jackson.annotation.JsonFormat;

public class BoardPageResponseDto {

    private Long userId;

    private String username;

    private String nickname;

    private String title;

    private String contents;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updatedAt;

    public BoardPageResponseDto(Board board) {
        this.userId = board.getUser().getId();
        this.username = board.getUser().getUsername();
        this.nickname = board.getUser().getNickname();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }

    public BoardPageResponseDto(Long userId, String username, String nickname, String title,
        String contents,
        String createdAt, String updatedAt) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
