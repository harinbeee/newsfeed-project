package com.example.newsfeed.boards.dto;

import com.example.newsfeed.boards.entity.Board;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BoardResponseDto {

    private final Long id;

    private final String nickname;

    private final String title;

    private final String contents;

    private final LocalDateTime createAt;

    private final LocalDateTime updatedAt;

    public BoardResponseDto(Long id, String title, String contents, String nickname,
        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.contents = contents;
        this.createAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // 전체 조회
    public static BoardResponseDto toDto(Board board) {
        return new BoardResponseDto(board.getId(), board.getTitle(),
            board.getContents(), board.getUser().getNickname(), board.getCreatedAt(),
            board.getUpdatedAt());
    }
}
