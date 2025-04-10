package com.example.newsfeed.boards.controller;

import static com.example.newsfeed.common.util.SessionUtil.getUserId;

import com.example.newsfeed.boards.dto.CommentRequestDto;
import com.example.newsfeed.boards.dto.CommentResponseDto;
import com.example.newsfeed.boards.repository.BoardRepository;
import com.example.newsfeed.boards.service.CommentService;
import com.example.newsfeed.common.response.ApiResponse;
import com.example.newsfeed.users.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boards/{boardId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    /**
     * 댓글 작성 요청 컨트롤러
     *
     * @param boardId     게시글 식별자
     * @param requestDto  댓글 요청 정보가 담긴 {@link CommentRequestDto} 객체
     * @param userRequest 로그인 세션 정보가 담긴 {@link HttpServletRequest} 객체
     * @return 댓글 정보가 담긴 {@link CommentResponseDto} 객체
     */
    @PostMapping
    public ApiResponse<CommentResponseDto> save(
        @PathVariable Long boardId,
        @RequestBody CommentRequestDto requestDto,
        HttpServletRequest userRequest
    ) {
        return ApiResponse.created(
            commentService.save(getUserId(userRequest), boardId, requestDto));
    }

    /**
     * 댓글 단건 조회 요청 컨트롤러
     *
     * @param boardId   게시글 식별자
     * @param commentId 댓글 식별자
     * @return 조회된 댓글 정보가 담겨있는 {@link CommentResponseDto} 객체
     */
    @GetMapping("/{commentId}")
    public ApiResponse<CommentResponseDto> findOne(
        @PathVariable Long boardId,
        @PathVariable Long commentId
    ) {
        return ApiResponse.ok(commentService.findOne(boardId, commentId));
    }

    /**
     * 댓글 수정 요청 컨트롤러
     *
     * @param commentId   댓글 식별자
     * @param requestDto  댓글 요청 정보가 담긴 {@link CommentRequestDto} 객체
     * @param userRequest 로그인 세션 정보가 담긴 {@link HttpServletRequest} 객체
     * @return 댓글 정보가 담긴 {@link CommentRequestDto} 객체
     */
    @PatchMapping("/{commentId}")
    public ApiResponse<CommentResponseDto> update(
        @PathVariable Long commentId,
        @RequestBody CommentRequestDto requestDto,
        HttpServletRequest userRequest
    ) {
        return ApiResponse.ok(commentService.update(commentId, getUserId(userRequest), requestDto));
    }

    /**
     * 댓글 삭제 요청 컨트롤러
     *
     * @param commentId   댓글 식별자
     * @param userRequest 로그인 세션 정보가 담긴 {@link HttpServletRequest} 객체
     * @return 메세지 응답 , 성공 - 200, 실패(다른 사용자 삭제 시도) - 400, 실패(댓글 식별자 없음) - 404
     */
    @DeleteMapping("/{commentId}")
    public ApiResponse<String> delete(
        @PathVariable Long commentId,
        HttpServletRequest userRequest
    ) {
        commentService.delete(commentId, getUserId(userRequest));
        return ApiResponse.ok();
    }
}
