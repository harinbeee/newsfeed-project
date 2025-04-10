package com.example.newsfeed.boards.dto;

import com.example.newsfeed.boards.entity.Board;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardResponseDto {

    private final Long boardId;
    private final String nickname;
    private final String title;
    private final String contents;
    private final LocalDateTime cratedAt;
    private final LocalDateTime updatedAt;

    // 전체 조회
    public static BoardResponseDto toDto(Board board) {
        return new BoardResponseDto(
            board.getBoardId(),
            board.getUser().getNickname(),
            board.getTitle(),
            board.getContents(),
            board.getCreatedAt(),
            board.getUpdatedAt()
        );
    }

}
