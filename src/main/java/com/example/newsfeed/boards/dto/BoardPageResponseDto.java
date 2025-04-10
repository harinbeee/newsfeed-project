package com.example.newsfeed.boards.dto;

import com.example.newsfeed.boards.entity.Board;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardPageResponseDto {

    private final Long userId;
    private final String username;
    private final String nickname;
    private final String title;
    private final String contents;
    private final Long commentCount;
    private final Long likeCount;
    private final String boardImage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedAt;

    public BoardPageResponseDto(Board board) {
        this.userId = board.getUser().getId();
        this.username = board.getUser().getUsername();
        this.nickname = board.getUser().getNickname();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }

}
