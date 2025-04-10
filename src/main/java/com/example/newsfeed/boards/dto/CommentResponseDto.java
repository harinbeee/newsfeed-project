package com.example.newsfeed.boards.dto;

import com.example.newsfeed.boards.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentResponseDto {

    private final Long commentId;
    private final Long boardId;
    private final String nickname;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(
            comment.getCommentId(),
            comment.getBoard().getBoardId(),
            comment.getUser().getNickname(),
            comment.getContents(),
            comment.getCreatedAt(),
            comment.getUpdatedAt()
        );
    }

}
