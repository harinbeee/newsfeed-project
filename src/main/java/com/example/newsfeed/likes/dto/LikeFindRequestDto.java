package com.example.newsfeed.likes.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class LikeFindRequestDto {

    @Min(1)
    private Long boardId;

    @Min(1)
    private Long commentId;
    
}
