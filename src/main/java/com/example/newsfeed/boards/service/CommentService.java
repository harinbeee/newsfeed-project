package com.example.newsfeed.boards.service;

import com.example.newsfeed.boards.dto.CommentRequestDto;
import com.example.newsfeed.boards.dto.CommentResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {


    /**
     * 댓글 생성 요청 서비스
     *
     * @param userId     유저 식별자
     * @param boardId    게시글 식별자
     * @param requestDto 댓글 내용이 담긴 {@link CommentRequestDto} 객체
     * @return 댓글 정보가 담긴 {@link CommentResponseDto} 객체
     */
    CommentResponseDto save(Long userId, Long boardId, CommentRequestDto requestDto);

    /**
     * 댓글 단건 조회 요청 서비스
     *
     * @param boardId   게시글 식별자
     * @param commentId 댓글 식별자
     * @return 댓글 정보가 담긴 {@link CommentResponseDto} 객체
     */
    CommentResponseDto findOne(Long boardId, Long commentId);


    /**
     * 댓글 수정 요청 서비스
     *
     * @param commentId  댓글 식별자
     * @param userId     유저 식별자
     * @param requestDto 댓글 내용이 담긴 {@link CommentRequestDto} 객체
     * @return 댓글 정보가 담긴 {@link CommentResponseDto} 객체
     */
    CommentResponseDto update(Long commentId, Long userId, CommentRequestDto requestDto);

    /**
     * 댓글 삭제 요청 서비스
     *
     * @param commentId 댓글 식별자
     * @param userId    유저 식별자
     */
    void delete(Long commentId, Long userId);

}
