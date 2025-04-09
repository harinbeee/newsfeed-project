package com.example.newsfeed.boards.dto;

import com.example.newsfeed.boards.entity.Board;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardResponseDto {

    private Long id;

    private String nickname;

    private String title;

    private String contents;

    private LocalDateTime cratedAt;

    private LocalDateTime updatedAt;


    // 전체 조회
    public static BoardResponseDto toDto(Board board) {
        return new BoardResponseDto(
            board.getId(),
            board.getUser().getNickname(),
            board.getTitle(),
            board.getContents(),
            board.getCreatedAt(),
            board.getUpdatedAt()
        );
    }
}
