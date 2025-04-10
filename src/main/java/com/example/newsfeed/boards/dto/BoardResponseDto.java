package com.example.newsfeed.boards.dto;

import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.boards.entity.Comment;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardResponseDto {

    private final Long boardId;
    private final String nickname;
    private final String title;
    private final String contents;
    private final String boardImage;
    private final LocalDateTime cratedAt;
    private final LocalDateTime updatedAt;
    private final List<CommentResponseDto> comments;

    // 전체 조회
    public static BoardResponseDto toDto(Board board) {
        return new BoardResponseDto(
            board.getBoardId(),
            board.getUser().getNickname(),
            board.getTitle(),
            board.getContents(),
            board.getBoardImage(),
            board.getCreatedAt(),
            board.getUpdatedAt(),
            board.getComments().stream().map(CommentResponseDto::toDto).toList()
        );
    }

}
