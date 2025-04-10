package com.example.newsfeed.boards.service;

import com.example.newsfeed.boards.dto.BoardPageResponseDto;
import com.example.newsfeed.boards.dto.BoardRequestDto;
import com.example.newsfeed.boards.dto.BoardResponseDto;
import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.boards.repository.BoardRepository;
import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    /**
     * 게시글 생성 요청 서비스
     *
     * @param userId     게시글을 작성하는 유저 식별자
     * @param requestDto 게시글 작성 요청 내용이 담겨있는 {@link BoardRequestDto} 객체
     * @return 작성된 게시글 정보가 담겨있는 {@link BoardResponseDto} 객체
     */
    @Override
    public BoardResponseDto save(Long userId, BoardRequestDto requestDto) {

        User findUser = userRepository.findByIdElseThrow(userId);

        Board board = new Board(requestDto);
        board.setUser(findUser);

        Board savedboard = boardRepository.save(board);

        return BoardResponseDto.toDto(savedboard);

    }

    /**
     * 게시글 전체 조회 요청 서비스
     *
     * @param page          현재 페이지
     * @param size          페이지당 게시글 개수
     * @param isFriendBoard 친구의 게시글 여부
     * @param userId        친구 식별자
     * @return 조회된 게시글 정보가 담겨있는 {@link Page} 객체
     */
    @Transactional(readOnly = true)
    @Override
    public Page<BoardPageResponseDto> findAll(int page, int size, boolean isFriendBoard,
        Long userId) {

        // page 값 조절
        int adjustedPage = (page > 0) ? page - 1 : 0;

        PageRequest pageable = PageRequest.of(
            adjustedPage, size,
            Sort.by("updatedAt").descending()
        );

        // isFriendBoard true 일 때 친구의 게시글이 우선순위
        if (isFriendBoard) {
            return boardRepository.findAllByFriendPriority(pageable, userId);
        }
        // false 일 때 업데이트 날짜로 정렬
        Page<Board> boardPage = boardRepository.findAll(pageable);

        return boardPage.map(BoardPageResponseDto::new);

    }

    /**
     * 게시글 단건 조회 요청 서비스
     *
     * @param boardId 게시글 식별자
     * @return 조회된 게시글 정보가 담겨있는 {@link BoardResponseDto} 객체
     */
    @Override
    public BoardResponseDto findOne(Long boardId) {
        return BoardResponseDto.toDto(boardRepository.findByIdOrElseThrow(boardId));
    }

    /**
     * 게시글 수정 요청 서비스
     *
     * @param boardId    게시글 식별자
     * @param userId     유저 식별자
     * @param requestDto 게시글 수정 요청 정보가 담겨있는 {@link BoardRequestDto} 객체
     * @return 수정된 게시글 정보가 담겨있는 {@link BoardResponseDto} 객체
     */
    @Transactional
    @Override
    public BoardResponseDto update(Long boardId, Long userId, BoardRequestDto requestDto) {

        // 게시글 찾기
        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);

        // 작성자 = 로그인 유저인지 검증
        if (!findBoard.getUser().getId().equals(userId)) {
            throw new BusinessException(ExceptionCode.BOARD_UPDATE_FORBIDDEN);
        }

        // 기존 내용 저장
        String updateTitle =
            (requestDto.getTitle() != null) ? requestDto.getTitle() : findBoard.getTitle();
        String updateContents =
            (requestDto.getContents() != null) ? requestDto.getContents() : findBoard.getContents();

        findBoard.update(updateTitle, updateContents);

        Board updatedBoard = boardRepository.findByIdOrElseThrow(boardId); // 업데이트 내용 저장

        return BoardResponseDto.toDto(updatedBoard);

    }

    /**
     * 게시글 삭제 요청 서비스
     *
     * @param userId  유저 식별자
     * @param boardId 게시글 식별자
     */
    @Transactional
    @Override
    public void delete(Long userId, Long boardId) {

        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);

        // 작성자 = 로그인 유저인지 검증
        if (!findBoard.getUser().getId().equals(userId)) {
            throw new BusinessException(ExceptionCode.BOARD_DELETE_FORBIDDEN);
        }

        boardRepository.delete(findBoard);

    }

}
