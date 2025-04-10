package com.example.newsfeed.likes.service;

import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.boards.repository.BoardRepository;
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


    /**
     * 좋아요 저장 요청 서비스
     *
     * @param requestDto 좋아요 요청 정보가 담긴 {@link LikeSaveRequestDto} 객체
     * @return 좋아요 응답 정보가 담긴 {@link LikeSaveResponseDto} 객체
     */
    @Override
    public LikeSaveResponseDto save(LikeSaveRequestDto requestDto) {

        Board board = boardRepository.findByIdOrElseThrow(requestDto.getBoardId());

        Like like = new Like(requestDto.getUserId(), board);

        return LikeSaveResponseDto.toDto(likeRepository.save(like));

    }

    /**
     * 게시글 또는 댓글의 좋아요 개수 조회 요청 서비스
     * <p>
     * boardId,commentId로,boardId+commentId로 로 조회 가능 넘겨주는 값에 따라 사용에 따라 수정 부탁합니다
     * </p>
     *
     * @param requestDto 게시글 또는 댓글의 좋아요 요청 정보가 담겨있는 {@link LikeFindRequestDto} 객체
     * @return 좋아요 개수 정보를 담고있는 {@link LikeFindResponseDto} 객체
     */
    @Override
    public LikeFindResponseDto findLikeCntByBoardIdOrCommentId(LikeFindRequestDto requestDto) {

        boardRepository.findByIdOrElseThrow(requestDto.getBoardId()); // 있는 게시글 인지 체크

        Long likeCnt = likeRepository.findByBoardIdOrCommentId(
                requestDto.getBoardId(),
                requestDto.getCommentId()
            )
            .orElseThrow(() -> new BusinessException(ExceptionCode.Like_NOT_FOUND));

        return new LikeFindResponseDto(likeCnt);

    }

}
