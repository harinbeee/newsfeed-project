package com.example.newsfeed.boards.controller;

import com.example.newsfeed.boards.dto.CommentRequestDto;
import com.example.newsfeed.boards.dto.CommentResponseDto;
import com.example.newsfeed.boards.repository.BoardRepository;
import com.example.newsfeed.boards.service.CommentService;
import com.example.newsfeed.users.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
     * @param boardId    게시글 식별자
     * @param requestDto 댓글 요청 정보가 담긴 {@link CommentRequestDto} 객체
     * @param request    로그인 세션 정보가 담긴 {@link HttpServletRequest} 객체
     * @return 댓글 정보가 담긴 {@link CommentResponseDto} 객체
     */
    @PostMapping
    public ResponseEntity<CommentResponseDto> save(
        @PathVariable Long boardId,
        @RequestBody CommentRequestDto requestDto,
        HttpServletRequest request
    ) {

        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("user");

        CommentResponseDto commentResponseDto = commentService.save(userId, boardId, requestDto);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);

    }

    /**
     * 댓글 단건 조회 요청 컨트롤러
     *
     * @param boardId   게시글 식별자
     * @param commentId 댓글 식별자
     * @return 조회된 댓글 정보가 담겨있는 {@link CommentResponseDto} 객체
     */
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> findOne(
        @PathVariable Long boardId,
        @PathVariable Long commentId
    ) {

        CommentResponseDto commentResponseDto = commentService.findOne(boardId, commentId);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);

    }

}
