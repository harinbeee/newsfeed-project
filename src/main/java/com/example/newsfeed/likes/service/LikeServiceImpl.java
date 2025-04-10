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
     * 좋아요 저장 메소드
     *
     * @param requestDto 게시글 ID, 작성한 유저 ID
     * @return 게시글 ID, 작성한 유저 ID , 좋아요 테이블 식별자
     */
    @Override
    public LikeSaveResponseDto save(LikeSaveRequestDto requestDto) {

        Board board = boardRepository.findByIdOrElseThrow(requestDto.getBoardId());

        Like like = new Like(requestDto.getUserId(), board);

        return LikeSaveResponseDto.toDto(likeRepository.save(like));
    }

    @Override
    public LikeFindResponseDto findLikeCntByBoardId(LikeFindRequestDto requestDto) {

        boardRepository.findByIdOrElseThrow(requestDto.getBoardId()); // 있는 게시글 인지 체크

        Like like = likeRepository.findByBoardIdOrCommnetId(
                requestDto.getBoardId(),
                requestDto.getCommentId()
            )
            .orElseThrow(() -> new BusinessException(ExceptionCode.Like_NOT_FOUND));

        return LikeFindResponseDto.toDto(like);
    }

}
