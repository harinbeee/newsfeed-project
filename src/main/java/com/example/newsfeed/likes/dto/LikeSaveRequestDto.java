package com.example.newsfeed.likes.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikeSaveRequestDto {

    @Min(1)
    private final Long userId;

    @Min(1)
    private final Long boardId;

    @Min(0)
    private final Long commentId;

}
