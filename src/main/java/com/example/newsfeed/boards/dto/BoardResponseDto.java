package com.example.newsfeed.boards.dto;

import com.example.newsfeed.boards.entity.Board;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardResponseDto {

    private Long boardId;

    private String nickname;

    private String title;

    private String contents;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<CommentResponseDto> comments;


    // 전체 조회
    public static BoardResponseDto toDto(Board board) {
        return new BoardResponseDto(
            board.getBoardId(),
            board.getUser().getNickname(),
            board.getTitle(),
            board.getContents(),
            board.getCreatedAt(),
            board.getUpdatedAt(),
            board.getComments().stream().map(CommentResponseDto::toDto).toList()
        );
    }
}
