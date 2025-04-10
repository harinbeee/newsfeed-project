package com.example.newsfeed.likes.service;

import com.example.newsfeed.likes.dto.LikeFindRequestDto;
import com.example.newsfeed.likes.dto.LikeFindResponseDto;
import com.example.newsfeed.likes.dto.LikeSaveRequestDto;
import com.example.newsfeed.likes.dto.LikeSaveResponseDto;

public interface LikeService {

    /**
     * 좋아요 저장 요청 서비스
     *
     * @param requestDto 좋아요 요청 정보가 담긴 {@link LikeSaveRequestDto} 객체
     * @return 좋아요 응답 정보가 담긴 {@link LikeSaveResponseDto} 객체
     */
    LikeSaveResponseDto save(LikeSaveRequestDto requestDto);

    /**
     * 게시글 또는 댓글의 좋아요 개수 조회 요청 서비스
     *
     * @param requestDto 요청 정보가 담겨있는 {@link LikeFindRequestDto} 객체
     * @return 좋아요 개수 정보를 담고있는 {@link LikeFindResponseDto} 객체
     */
    LikeFindResponseDto findLikeCntByBoardIdOrCommentId(LikeFindRequestDto requestDto);

}
