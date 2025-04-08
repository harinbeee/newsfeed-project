package com.example.newsfeed.boards.service;

import com.example.newsfeed.boards.dto.BoardResponseDto;
import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.boards.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardResponseDto save(String title, String contents) {

        User findUser = userRepository.findUserByUsername(username);

        Board board = new Board(title, contents);
        board.setUser(findUser);

        board = boardRepository.save(board);
        return new BoardResponseDto(board.getId(), board.getTitle(), board.getContents(),
            findUser.getUsername());
    }


}
