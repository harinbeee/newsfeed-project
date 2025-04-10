package com.example.newsfeed.likes.service;

import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.boards.entity.Comment;
import com.example.newsfeed.boards.repository.BoardRepository;
import com.example.newsfeed.boards.repository.CommentRepository;
import com.example.newsfeed.likes.dto.LikeSaveRequestDto;
import com.example.newsfeed.likes.dto.LikeSaveResponseDto;
import com.example.newsfeed.likes.entity.Like;
import com.example.newsfeed.likes.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;

    /**
     * 좋아요 저장 메소드
     *
     * @param requestDto 게시글 ID, 작성한 유저 ID
     * @return 게시글 ID, 작성한 유저 ID , 좋아요 테이블 식별자
     */
    @Override
    public LikeSaveResponseDto save(LikeSaveRequestDto requestDto) {

        Board board = boardRepository.findByIdOrElseThrow(requestDto.getBoardId());

        Comment comment = requestDto.getCommentId().equals(0L) // 0L이면 게시물 좋아요
            ? null
            : commentRepository.findByIdOrElseThrow(requestDto.getCommentId()); // 댓글 좋아요

        Like like = new Like(requestDto.getUserId(), board, comment);

        return LikeSaveResponseDto.toDto(likeRepository.save(like));
    }


}
