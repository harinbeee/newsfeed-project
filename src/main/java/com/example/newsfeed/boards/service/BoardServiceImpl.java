package com.example.newsfeed.boards.service;

import com.example.newsfeed.boards.dto.BoardPageResponseDto;
import com.example.newsfeed.boards.dto.BoardResponseDto;
import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.boards.repository.BoardRepository;
import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import jakarta.persistence.EntityManager;
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
    private final EntityManager em;

    /**
     * @param
     * @param title
     * @param contents
     * @return
     */
    @Override
    public BoardResponseDto save(Long userId, String title, String contents) {
        // username으로 유저 찾기
        User findUser = userRepository.findByIdElseThrow(userId);

        Board board = new Board(title, contents);
        board.setUser(findUser);

        Board savedboard = boardRepository.save(board);
        return BoardResponseDto.toDto(savedboard);
    }

    /**
     * @param page
     * @param size
     * @param isFriendBoard
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Page<BoardPageResponseDto> findAll(int page, int size, boolean isFriendBoard,
        Long userId) {
        // page 값 조절
        int adjustedPage = (page > 0) ? page - 1 : 0;

        PageRequest pageable = PageRequest.of(adjustedPage, size,
            Sort.by("updatedAt").descending());

        // isFriendBoard true 일 때 친구의 게시글이 우선순위
        if (isFriendBoard) {
            return boardRepository.findAllByFriendPriority(pageable, userId);
        }
        // false 일 때 업데이트 날짜로 정렬
        Page<Board> boardPage = boardRepository.findAll(pageable);

        return boardPage.map(BoardPageResponseDto::new);
    }

    /**
     * @param boardId
     * @return
     */
    @Override
    public BoardResponseDto findOne(Long boardId) {

        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);

        return BoardResponseDto.toDto(findBoard);
    }


    @Transactional
    @Override
    public BoardResponseDto update(Long boardId, Long userId, String title, String contents) {

        // 게시글 찾기
        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);
        if (findBoard == null) {
            throw new BusinessException(ExceptionCode.BOARD_NOT_FOUND);
        }

        // 작성자 = 로그인 유저인지 검증
        if (!findBoard.getUser().getId().equals(userId)) {
            throw new BusinessException(ExceptionCode.BOARD_UPDATE_FORBIDDEN);
        }

        // 기존 내용 저장
        String updateTitle = (title != null) ? title : findBoard.getTitle();
        String updateContents = (contents != null) ? contents : findBoard.getContents();

        findBoard.update(updateTitle, updateContents);
        em.flush();
        Board updatedBoard = boardRepository.findByIdOrElseThrow(boardId); // 업데이트 내용 저장

        return BoardResponseDto.toDto(updatedBoard);
    }

    /**
     * @param name
     * @param boardId
     */
    @Override
    public void delete(String name, Long boardId) {

        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);

        if (findBoard == null) {
            throw new BusinessException(ExceptionCode.BOARD_NOT_FOUND);
        }

        // 작성자 = 로그인 유저인지 검증
        if (!findBoard.getUser().getNickname().equals(name)) {
            throw new BusinessException(ExceptionCode.BOARD_DELETE_FORBIDDEN);
        }

        boardRepository.delete(findBoard);
    }


}
