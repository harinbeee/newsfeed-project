package com.example.newsfeed.boards.service;

import com.example.newsfeed.boards.dto.CommentRequestDto;
import com.example.newsfeed.boards.dto.CommentResponseDto;
import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.boards.entity.Comment;
import com.example.newsfeed.boards.repository.BoardRepository;
import com.example.newsfeed.boards.repository.CommentRepository;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    /**
     * 댓글 저장 요청 서비스
     *
     * @param userId     유저 식별자
     * @param boardId    게시글 식별자
     * @param requestDto 댓글 내용이 담긴 {@link CommentRequestDto} 객체
     * @return 댓글 정보가 담긴 {@link CommentResponseDto} 객체
     */
    @Override
    public CommentResponseDto save(Long userId, Long boardId, CommentRequestDto requestDto) {

        User findUser = userRepository.findByIdElseThrow(userId);
        Board board = boardRepository.findByIdOrElseThrow(boardId);

        Comment comment = new Comment(requestDto.getContents());
        comment.setBoard(board);
        comment.setUser(findUser);

        return CommentResponseDto.toDto(commentRepository.save(comment));

    }

    /**
     * 댓글 단건 조회 요청 서비스
     *
     * @param boardId   게시글 식별자
     * @param commentId 댓글 식별자
     * @return 댓글 정보가 담긴 {@link CommentResponseDto} 객체
     */
    @Override
    public CommentResponseDto findOne(Long boardId, Long commentId) {

        Board findboard = boardRepository.findByIdOrElseThrow(boardId); // 게시물 - 댓글 일치하는지 검증?
        Comment findComment = commentRepository.findByIdOrElseThrow(commentId);

        return CommentResponseDto.toDto(findComment);

    }
}
