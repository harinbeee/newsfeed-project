package com.example.newsfeed.likes.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikeFindRequestDto {

    @Min(0)
    private final Long boardId;

    @Min(0)
    private final Long commentId;

}
