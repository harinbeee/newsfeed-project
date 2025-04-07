package com.example.newsfeed.boards.controller;

import com.example.newsfeed.boards.dto.BoardRequestDto;
import com.example.newsfeed.boards.dto.BoardResponseDto;
import com.example.newsfeed.boards.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardResponseDto> save(
        @RequestBody BoardRequestDto requestDto,
        HttpServletRequest request
    ) {

        //로그인 정보 가져오기
//        HttpSession session = request.getSession(false);
//        UserResponseDto loginUser = session.getAttribute("loginUser");

        BoardResponseDto boardResponseDto =
            boardService.save(requestDto.getTitle(), requestDto.getContents());

        return new ResponseEntity<>(boardResponseDto, HttpStatus.CREATED);

    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> delete(@PathVariable Long boardId) {

        boardService.delete(boardId);

        return new ResponseEntity<>("게시물 삭제 성공!", HttpStatus.OK);
    }


}
