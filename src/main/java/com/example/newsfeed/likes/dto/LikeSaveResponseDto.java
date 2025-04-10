package com.example.newsfeed.likes.dto;

import com.example.newsfeed.likes.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeSaveResponseDto {

    private Long userId;

    private Long boardId;

    public static LikeSaveResponseDto toDto(Like like) {
        return new LikeSaveResponseDto(like.getUserId(), like.getBoard().getBoardId());
    }
}
