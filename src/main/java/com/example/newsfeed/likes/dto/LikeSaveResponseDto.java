package com.example.newsfeed.likes.dto;

import com.example.newsfeed.likes.entity.Like;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikeSaveResponseDto {

    private final Long userId;
    private final Long boardId;

    public static LikeSaveResponseDto toDto(Like like) {
        return new LikeSaveResponseDto(like.getUserId(), like.getBoard().getBoardId());
    }

}
