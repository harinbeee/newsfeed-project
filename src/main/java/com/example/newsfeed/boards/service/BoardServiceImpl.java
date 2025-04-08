package com.example.newsfeed.boards.service;

import com.example.newsfeed.boards.dto.BoardResponseDto;
import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.boards.repository.BoardRepository;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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
    public BoardResponseDto save(String name, String title, String contents) {

        User findUser = userRepository.findByIdElseThrow(name);

        Board board = new Board(title, contents);
        board.setUser(findUser);

        Board savedboard = boardRepository.save(board);
        return new BoardResponseDto(savedboard.getId(), board.getTitle(), board.getContents(),
            findUser.getNickname());
    }


    @Override
    public List<BoardResponseDto> findAll() {
        List<Board> boards = boardRepository.findAll();

        return boards.stream().map(BoardResponseDto::toDto).toList();
    }


    @Override
    public BoardResponseDto update(String name, Long boardId, String title, String contents) {

        Board findboard = boardRepository.findByIdOrElseThrow(boardId);

        // 작성자 = 로그인유저인지 검증
        if (findboard.getUser().getId().equals(name)) {
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
