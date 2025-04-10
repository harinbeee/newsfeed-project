package com.example.newsfeed.boards.controller;

import static com.example.newsfeed.common.util.SessionUtil.getUserId;

import com.example.newsfeed.boards.dto.BoardPageResponseDto;
import com.example.newsfeed.boards.dto.BoardRequestDto;
import com.example.newsfeed.boards.dto.BoardResponseDto;
import com.example.newsfeed.boards.service.BoardService;
import com.example.newsfeed.common.response.ApiResponse;
import com.example.newsfeed.users.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import com.example.newsfeed.common.util.SortType;
import com.example.newsfeed.users.dto.UserFindResponseDto;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final UserRepository userRepository;

    /**
     * 게시글 생성 요청 컨트롤러
     *
     * @param requestDto 게시물 생성 요청 정보가 담긴 {@link BoardRequestDto} 객체
     * @param request    로그인 세션 정보가 딤긴 {@link HttpServletRequest} 객체
     * @return 생성된 게시글 정보가 담긴 {@link BoardResponseDto} 객체
     */
    @PostMapping
    public ApiResponse<BoardResponseDto> save(
        @Valid @RequestBody BoardRequestDto requestDto,
        HttpServletRequest request
    ) {
        return ApiResponse.created(boardService.save(getUserId(request), requestDto));
    }

    /**
     * 게시글 전체 조회 요청 컨트롤러
     *
     * @param page    현재 페이지
     * @param size    페이지당 게시글 개수
     * @param sort    FRIEND = 친구 우선순위, LIKES = 좋아요 우선순위, RECENT = 수정일 기준 정렬
     * @param request 로그인 세션 정보가 담긴 {@link HttpServletRequest} 객체
     * @return 조회된 게시글 정보가 담긴 {@link BoardPageResponseDto} 객체
     */
    @GetMapping
    public ApiResponse<Page<BoardPageResponseDto>> findAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "RECENT") SortType sort,
        HttpServletRequest request
    ) {
        return ApiResponse.ok(boardService.findAll(page, size, isFriendBoard, getUserId(request)));
    }

    /**
     * 게시글 단건 조회 요청 컨트롤러
     *
     * @param boardId 게시물 식별자
     * @return 조회된 게시글 정보가 담긴 {@link BoardResponseDto} 객체
     */
    @GetMapping("/{boardId}")
    public ApiResponse<BoardResponseDto> findOne(
        @PathVariable Long boardId
    ) {
        return ApiResponse.ok(boardService.findOne(boardId));
    }

    /**
     * 게시글 수정 요청 컨트롤러
     *
     * @param boardId    수정할 게시물 식별자 요청
     * @param requestDto 게시물 수정 dto 요청
     * @param request    로그인 유저 정보 요청
     * @return 수정된 게시글 정보가 담긴 {@link BoardResponseDto} 객체
     */
    @PatchMapping("/{boardId}")
    public ApiResponse<BoardResponseDto> update(
        @PathVariable Long boardId,
        @RequestBody BoardRequestDto requestDto,
        HttpServletRequest request
    ) {
        return ApiResponse.ok(boardService.update(boardId, getUserId(request), requestDto));
    }


    /**
     * 게시글 삭제
     *
     * @param boardId 삭제할 게시물 식별자 요청
     * @param request 로그인 유저 정보 요청
     * @return 메세지 응답 , 성공 - 200, 실패(다른 사용자 삭제 시도) - 400, 실패(게시물 식별자 없음) - 404
     */
    @DeleteMapping("/{boardId}")
    public ApiResponse<String> delete(
        @PathVariable Long boardId,
        HttpServletRequest request
    ) {
        boardService.delete(getUserId(request), boardId);
        return ApiResponse.ok();
    }


}
