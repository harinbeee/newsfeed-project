package com.example.newsfeed.likes.dto;

import com.example.newsfeed.likes.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeFindResponseDto {

    private Long userId;

    private Long boardId;

    public static LikeFindResponseDto toDto(Like like) {
        return new LikeFindResponseDto(like.getUserId(), like.getBoard().getBoardId());
    }
}
