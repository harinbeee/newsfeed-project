package com.example.newsfeed.likes.service;

import com.example.newsfeed.likes.dto.LikeFindRequestDto;
import com.example.newsfeed.likes.dto.LikeFindResponseDto;
import com.example.newsfeed.likes.dto.LikeSaveRequestDto;
import com.example.newsfeed.likes.dto.LikeSaveResponseDto;

public interface LikeService {

    LikeSaveResponseDto save(LikeSaveRequestDto requestDto);

    LikeFindResponseDto findLikeCntByBoardId(LikeFindRequestDto requestDto);
}
