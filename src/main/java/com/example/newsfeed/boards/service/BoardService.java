package com.example.newsfeed.boards.service;

import com.example.newsfeed.boards.dto.BoardPageResponseDto;
import com.example.newsfeed.boards.dto.BoardRequestDto;
import com.example.newsfeed.boards.dto.BoardResponseDto;
import com.example.newsfeed.common.util.SortType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface BoardService {

    /**
     * 게시글 생성 요청 서비스
     *
     * @param userId     게시글을 작성하는 유저 식별자
     * @param requestDto 게시글 작성 요청 내용이 담겨있는 {@link BoardRequestDto} 객체
     * @return 작성된 게시글 정보가 담겨있는 {@link BoardResponseDto} 객체
     */
    BoardResponseDto save(Long userId, BoardRequestDto requestDto);

    /**
     * 게시글 전체 조회 요청 서비스
     *
     * @param page   현재 페이지
     * @param size   페이지당 게시글 개수
     * @param sort   FRIEND = 친구 우선순위, LIKES = 좋아요 우선순위, RECENT = 수정일 기준 정렬
     * @param userId 친구 식별자
     * @return 조회된 게시글 정보가 담겨있는 {@link Page} 객체
     */
    Page<BoardPageResponseDto> findAll(int page, int size, SortType sort, Long userId);

    /**
     * 게시글 단건 조회 요청 서비스
     *
     * @param boardId 게시글 식별자
     * @return 조회된 게시글 정보가 담겨있는 {@link BoardResponseDto} 객체
     */
    BoardResponseDto findOne(Long boardId);

    /**
     * 게시글 수정 요청 서비스
     *
     * @param boardId    게시글 식별자
     * @param userId     유저 식별자
     * @param requestDto 게시글 수정 요청 정보가 담겨있는 {@link BoardRequestDto} 객체
     * @return 수정된 게시글 정보가 담겨있는 {@link BoardResponseDto} 객체
     */
    BoardResponseDto update(Long boardId, Long userId, BoardRequestDto requestDto);

    /**
     * 게시글 삭제 요청 서비스
     *
     * @param userId  유저 식별자
     * @param boardId 게시글 식별자
     */
    void delete(Long userId, Long boardId);

}
