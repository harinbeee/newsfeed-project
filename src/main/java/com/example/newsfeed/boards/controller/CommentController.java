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

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> findOne(
        @PathVariable Long boardId,
        @PathVariable Long commentId
    ) {
        CommentResponseDto commentResponseDto =
            commentService.findOne(boardId, commentId);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }

}
