package com.example.newsfeed.likes.service;

import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.boards.entity.Comment;
import com.example.newsfeed.boards.repository.BoardRepository;
import com.example.newsfeed.boards.repository.CommentRepository;
import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.likes.dto.LikeFindRequestDto;
import com.example.newsfeed.likes.dto.LikeFindResponseDto;
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

    /**
     * 게시글 ID로 조회 or 댓글 ID로 조회
     * <p>
     * boardId,commentId로,boardId+commentId로 로 조회 가능 넘겨주는 값에 따라 사용에 따라 수정 부탁합니다
     *
     * @param requestDto 게시글 ID,  댓글 ID
     * @return 게시글 좋아요 개수 or댓글 좋아요 개수
     */
    @Override
    public LikeFindResponseDto findLikeCntByBoardIdOrCommentId(LikeFindRequestDto requestDto) {

        boardRepository.findByIdOrElseThrow(requestDto.getBoardId()); // 있는 게시글 인지 체크

        Long likeCnt = likeRepository.findByBoardIdORCommentId(
                requestDto.getBoardId(),
                requestDto.getCommentId()
            )
            .orElseThrow(() -> new BusinessException(ExceptionCode.Like_NOT_FOUND));

        return new LikeFindResponseDto(likeCnt);
    }

}
