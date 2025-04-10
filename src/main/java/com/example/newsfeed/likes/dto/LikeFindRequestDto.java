package com.example.newsfeed.likes.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class LikeFindRequestDto {

    @Min(0)
    private Long boardId;

    @Min(0)
    private Long commentId;

}
