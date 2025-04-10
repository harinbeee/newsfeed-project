package com.example.newsfeed.boards.dto;

import com.example.newsfeed.boards.entity.Board;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardFindResponseDto {

    private final Long boardId;
    private final String nickname;
    private final String title;
    private final String contents;
    private final String boardImage;
    private final Long likeCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime cratedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedAt;

    private final List<CommentResponseDto> comments;

    // 전체 조회
    public static BoardFindResponseDto toDto(Board board, Long likeCount,
        List<CommentResponseDto> comments) {
        return new BoardFindResponseDto(
            board.getBoardId(),
            board.getUser().getNickname(),
            board.getTitle(),
            board.getContents(),
            board.getBoardImage(),
            likeCount,
            board.getCreatedAt(),
            board.getUpdatedAt(),
            comments
        );
    }

}
