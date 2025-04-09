package com.example.newsfeed.boards.service;

import com.example.newsfeed.boards.dto.BoardPageResponseDto;
import com.example.newsfeed.boards.dto.BoardResponseDto;
import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.boards.repository.BoardRepository;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    /**
     * @param
     * @param title
     * @param contents
     * @return
     */
    @Override
    public BoardResponseDto save(String nickname, String title, String contents) {

        User findUser = userRepository.findByNicknameElseThrow(nickname);

        Board board = new Board(title, contents);
        board.setUser(findUser);

        Board savedboard = boardRepository.save(board);
        return new BoardResponseDto(savedboard.getId(), board.getTitle(), board.getContents(),
            findUser.getNickname());
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

        return new BoardResponseDto(boardId, findBoard.getUser().getNickname(),
            findBoard.getTitle(), findBoard.getContents());
    }

    /**
     * @param boardId
     * @param name
     * @param title
     * @param contents
     * @return
     */
    @Override
    public BoardResponseDto update(Long boardId, String name, String title, String contents) {

        Board findboard = boardRepository.findByIdOrElseThrow(boardId);

        // 작성자 = 로그인유저인지 검증
        if (findboard.getUser().getNickname().equals(name)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        // 기존 내용 저장하기,,,,
        String updateTitle = (title != null) ? title : findboard.getTitle();
        String updateContents = (contents != null) ? contents : findboard.getContents();

        findboard.update(updateTitle, updateContents);
        return new BoardResponseDto(boardId, findboard.getUser().getNickname(),
            findboard.getTitle(),
            findboard.getContents());
    }

    /**
     * @param name
     * @param boardId
     */
    @Override
    public void delete(String name, Long boardId) {

        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);

        // 작성자 = 로그인유저인지 검증
        if (findBoard.getUser().getNickname().equals(name)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        boardRepository.delete(findBoard);
    }


}
