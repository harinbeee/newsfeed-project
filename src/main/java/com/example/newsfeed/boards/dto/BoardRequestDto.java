package com.example.newsfeed.boards.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardRequestDto {

    @NotBlank(message = "게시글 제목을 입력해주세요.")
    private final String title;
    @NotBlank(message = "게시글 내용을 입력해주세요.")
    private final String contents;
    private final String boardImage;

}
