package com.example.newsfeed.boards.service;

import com.example.newsfeed.boards.dto.CommentRequestDto;
import com.example.newsfeed.boards.dto.CommentResponseDto;
import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.boards.entity.Comment;
import com.example.newsfeed.boards.repository.BoardRepository;
import com.example.newsfeed.boards.repository.CommentRepository;
import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    /**
     * 댓글 생성 요청 서비스
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

        return CommentResponseDto.toDto(commentRepository.save(comment), 0L);

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
        if (!findboard.getBoardId().equals(findComment.getBoard().getBoardId())) {
            throw new BusinessException(ExceptionCode.NOT_MATCH_BOARD);
        }
        Long likeCount = commentRepository.countCommentLikes(commentId);

        return CommentResponseDto.toDto(findComment, likeCount);

    }

    /**
     * 댓글 수정 요청 서비스
     *
     * @param commentId  댓글 식별자
     * @param userId     유저 식별자
     * @param requestDto 댓글 내용이 담긴 {@link CommentRequestDto} 객체
     * @return 댓글 정보가 담긴 {@link CommentResponseDto} 객체
     */
    @Transactional
    @Override
    public CommentResponseDto update(Long commentId, Long userId, CommentRequestDto requestDto) {

        // 현재 로그인한 유저찾기
        User loginUser = userRepository.findByIdElseThrow(userId);
        // 수정할 댓글
        Comment updatedComment = commentRepository.findByIdOrElseThrow(commentId);
        Long likeCount = commentRepository.countCommentLikes(commentId);

        // 수정 실패 : 댓글 작성자 = 현재 로그인 유저가 아닐때
        if (!loginUser.getId().equals(updatedComment.getUser().getId())) {
            throw new BusinessException(ExceptionCode.BOARD_UPDATE_FORBIDDEN);
        }

        updatedComment.setContents(requestDto.getContents());

        return CommentResponseDto.toDto(updatedComment, likeCount);
    }

    /**
     * 댓글 삭제 요청 서비스
     *
     * @param commentId 댓글 식별자
     * @param userId    유저 식별자
     */
    @Override
    public void delete(Long commentId, Long userId) {

        // 현재 로그인한 유저찾기
        User loginUser = userRepository.findByIdElseThrow(userId);
        // 삭제할 댓글
        Comment deletedComment = commentRepository.findByIdOrElseThrow(commentId);

        // 삭제 실패 : 댓글 작성자 = 현재 로그인 유저가 아닐때
        if (!loginUser.getId().equals(deletedComment.getUser().getId())) {
            throw new BusinessException(ExceptionCode.BOARD_UPDATE_FORBIDDEN);
        }

        commentRepository.delete(deletedComment);
    }
}
