package com.example.newsfeed.boards.controller;

import com.example.newsfeed.boards.dto.BoardPageResponseDto;
import com.example.newsfeed.boards.dto.BoardRequestDto;
import com.example.newsfeed.boards.dto.BoardResponseDto;
import com.example.newsfeed.boards.service.BoardService;
import com.example.newsfeed.users.dto.UserFindResponseDto;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * 게시글 생성
     *
     * @param requestDto 게시물 생성 dto 요청
     * @param request    로그인 유저 정보
     * @return 게시글 정보가 담긴 응답 dto , 성공 - 201, 실패 - 400
     */
    @PostMapping
    public ResponseEntity<BoardResponseDto> save(
        @RequestBody BoardRequestDto requestDto,
        HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("user");
        User user = userRepository.findByIdElseThrow(userId);

        UserFindResponseDto loginUser = UserFindResponseDto.toDto(user);

        BoardResponseDto boardResponseDto =
            boardService.save(loginUser.getNickname(), requestDto.getTitle(),
                requestDto.getContents());

        return new ResponseEntity<>(boardResponseDto, HttpStatus.CREATED);

    }

    /**
     * 게시글 전체조회
     *
     * @param page
     * @param size
     * @param isFriendBoard
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<BoardPageResponseDto>> findAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "false") boolean isFriendBoard,
        HttpServletRequest request
    ) {

        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("user");

        Page<BoardPageResponseDto> boardPageResponseDto = boardService.findAll(page, size,
            isFriendBoard, userId);

        return new ResponseEntity<>(boardPageResponseDto, HttpStatus.OK);
    }

    /**
     * 게시글 단건 조회
     *
     * @param boardId 게시물 조회시 요청 식별자
     * @return 게시물 정보가 담긴 응답 Dto, 성공시 200
     */
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> findOne(
        @PathVariable Long boardId
    ) {
        BoardResponseDto boardResponseDto =
            boardService.findOne(boardId);
        return new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
    }

    /**
     * 게시글 수정
     *
     * @param boardId    수정할 게시물 식별자 요청
     * @param requestDto 게시물 수정 dto 요청
     * @param request    로그인 유저 정보 요청
     * @return 수정된 게시물 dto 응답 , 성공 - 200, 실패(다른 사용자 수정 시도)-400, 실패(게시물 식별자 없음)-404
     */
    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> update(
        @PathVariable Long boardId,
        @RequestBody BoardRequestDto requestDto,
        HttpServletRequest request
    ) {

        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("user");
        User user = userRepository.findByIdElseThrow(userId);

        UserFindResponseDto loginUser = UserFindResponseDto.toDto(user);

        BoardResponseDto boardResponseDto =
            boardService.update(boardId, loginUser.getNickname(), requestDto.getTitle(),
                requestDto.getContents());

        return new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
    }


    /**
     * 게시글 삭제
     *
     * @param boardId 삭제할 게시물 식별자 요청
     * @param request 로그인 유저 정보 요청
     * @return 메세지 응답 , 성공 - 200, 실패(다른 사용자 삭제 시도) - 400, 실패(게시물 식별자 없음) - 404
     */
    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> delete(
        @PathVariable Long boardId,
        HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("user");
        User user = userRepository.findByIdElseThrow(userId);

        UserFindResponseDto loginUser = UserFindResponseDto.toDto(user);

        boardService.delete(loginUser.getNickname(), boardId);

        return new ResponseEntity<>("게시물 삭제 성공!", HttpStatus.OK);
    }


}
