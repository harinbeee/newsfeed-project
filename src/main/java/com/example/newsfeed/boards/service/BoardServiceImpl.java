package com.example.newsfeed.boards.service;

import com.example.newsfeed.boards.dto.BoardResponseDto;
import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.boards.repository.BoardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public BoardResponseDto save(String title, String contents) {

        User findUser = userRepository.findUserByUsername(username);

        Board board = new Board(title, contents);
        board.setUser(findUser);

        Board board = boardRepository.save(board);
        return new BoardResponseDto(board.getId(), board.getTitle(), board.getContents(),
            findUser.getUsername());
    }

    @Override
    public List<BoardResponseDto> findAll() {
        List<Board> boards = boardRepository.findAll();

        return boards.stream().map(BoardResponseDto::toDto).toList();
    }

    @Override
    public void delete(Long boardId) {

        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);

        boardRepository.delete(findBoard);
    }


}
