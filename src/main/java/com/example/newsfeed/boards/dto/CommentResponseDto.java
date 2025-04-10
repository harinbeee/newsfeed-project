package com.example.newsfeed.boards.dto;

import com.example.newsfeed.boards.entity.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

    private Long commentId;

    private Long boardId;

    private String nickname;

    private String contents;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

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
