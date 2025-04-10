package com.example.newsfeed.likes.service;


import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.boards.entity.Comment;
import com.example.newsfeed.boards.repository.BoardRepository;
import com.example.newsfeed.boards.repository.CommentRepository;
import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.likes.dto.LikeSaveRequestDto;
import com.example.newsfeed.likes.dto.LikeSaveResponseDto;
import com.example.newsfeed.likes.entity.Like;
import com.example.newsfeed.likes.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;

    /**
     * 좋아요 저장 요청 서비스
     *
     * @param requestDto 좋아요 요청 정보가 담긴 {@link LikeSaveRequestDto} 객체
     * @return 좋아요 응답 정보가 담긴 {@link LikeSaveResponseDto} 객체
     */
    @Override
    @Transactional
    public LikeSaveResponseDto save(LikeSaveRequestDto requestDto) {

        Board board = boardRepository.findByIdOrElseThrow(requestDto.getBoardId());

        // 자기 게시판에 좋아요 금지
        if (board.getUser().getId() == requestDto.getUserId()) {
            throw new BusinessException(ExceptionCode.BOARD_SELFLIKE_BLOCK);
        }

        Comment comment =
            requestDto.getCommentId() == null || requestDto.getCommentId().equals(0L)// 0L이면 게시물 좋아요
                ? null
                : commentRepository.findByIdOrElseThrow(requestDto.getCommentId()); // 댓글 좋아요

        if (comment != null) {
            // 자기 댓글에 좋아요 금지
            if (comment.getUser().getId() == requestDto.getUserId()) {
                throw new BusinessException(ExceptionCode.COMMENT_SELFLIKE_BLOCK);
            }
        }

        Like like = new Like(requestDto.getUserId(), board, comment);

        return LikeSaveResponseDto.toDto(likeRepository.save(like));

    }


}
