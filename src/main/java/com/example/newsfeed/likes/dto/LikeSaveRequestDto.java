package com.example.newsfeed.likes.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class LikeSaveRequestDto {

    @Min(1)
    private Long userId;

    @Min(1)
    private Long boardId;

    @Min(1)
    private Long commentId;
    
}
