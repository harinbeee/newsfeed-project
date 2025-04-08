package com.example.newsfeed.boards.service;

import com.example.newsfeed.boards.dto.BoardResponseDto;
import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.boards.repository.BoardRepository;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public BoardResponseDto save(Long userId, String title, String contents) {

        User findUser = userRepository.findByIdElseThrow(userId);

        Board board = new Board(title, contents);
        board.setUser(findUser);

        Board savedboard = boardRepository.save(board);
        return new BoardResponseDto(savedboard.getId(), board.getTitle(), board.getContents(),
            findUser.getName());
        
    }


    @Override
    public List<BoardResponseDto> findAll() {
        List<Board> boards = boardRepository.findAll();

        return boards.stream().map(BoardResponseDto::toDto).toList();
    }


    @Override
    public BoardResponseDto update(Long boardId, String title, String contents) {

        Optional<Board> board = boardRepository.findById(boardId);

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
