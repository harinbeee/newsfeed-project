package com.example.newsfeed.boards.service;

import com.example.newsfeed.boards.dto.CommentResponseDto;
import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.boards.entity.Comment;
import com.example.newsfeed.boards.repository.BoardRepository;
import com.example.newsfeed.boards.repository.CommentRepository;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import javax.swing.text.AbstractDocument.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public CommentResponseDto save(Long userId, Long boardId, String contents) {

        User findUser = userRepository.findByIdElseThrow(userId);
        Board board = boardRepository.findByIdOrElseThrow(boardId);

        Comment comment = new Comment(contents);
        comment.setBoard(board);
        comment.setUser(findUser);

        Comment savedComment = commentRepository.save(comment);
        return CommentResponseDto.toDto(savedComment);
    }

    @Override
    public CommentResponseDto findOne(Long boardId, Long commentId) {
        Board findboard = boardRepository.findByIdOrElseThrow(boardId); // 게시물 - 댓글 일치하는지 검증?
        Comment findComment = commentRepository.findByIdOrElseThrow(commentId);
        return CommentResponseDto.toDto(findComment);
    }
}
