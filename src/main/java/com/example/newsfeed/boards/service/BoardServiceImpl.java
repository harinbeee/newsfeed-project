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
    public BoardResponseDto save(Long id, String title, String contents) {

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
    public BoardResponseDto update(String title, String contents) {

        Board board = boardRepository.findById(id);
        User findUser = board.getUser();

        String updateTitle = (title != null) ? title : board.getTitle();
        String updateContents = (contents != null) ? contents : board.getContents();

        board.update(updateTitle, updateContents);
        return new BoardResponseDto(board.getTitle(), board.getContent());
    }

    @Override
    public void delete(Long boardId) {

        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);

        boardRepository.delete(findBoard);
    }


}
