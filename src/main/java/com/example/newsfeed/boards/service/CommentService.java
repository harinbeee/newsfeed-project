package com.example.newsfeed.boards.service;

import com.example.newsfeed.boards.dto.CommentResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {

    CommentResponseDto save(Long userId, Long boardId, String contents);

    CommentResponseDto findOne(Long boardId, Long commentId);

}
