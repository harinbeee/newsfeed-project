package com.example.newsfeed.boards.service;

import com.example.newsfeed.boards.dto.BoardPageResponseDto;
import com.example.newsfeed.boards.dto.BoardResponseDto;
import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.boards.repository.BoardRepository;
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

    @Override
    public BoardResponseDto save(String title, String contents) {

        User findUser = userRepository.findUserByUsername(username);

        Board board = new Board(title, contents);
        board.setUser(findUser);

        Board board = boardRepository.save(board);
        return new BoardResponseDto(board.getId(), board.getTitle(), board.getContents(),
            findUser.getUsername());

    }

    @Transactional(readOnly = true)
    @Override
    public Page<BoardPageResponseDto> findAll(int page, int size, boolean isFriendBoard) {

        int adjustedPage = (page > 0) ? page - 1 : 0;

        PageRequest pageable = PageRequest.of(adjustedPage, size,
            Sort.by("updatedAt").descending());

        // isFriendBoard true 일 때 친구의 게시글이 우선순위
        if (isFriendBoard == true) {
            boardRepository.findAllByFriendPriority(pageable);
        }

        Page<Board> boardPage = boardRepository.findAll(pageable);

        return boardPage.map(BoardPageResponseDto::new);
    }

    @Override
    public void delete(Long boardId) {

        Board findBoard = boardRepository.findByIdOrElseThrow(boardId);

        boardRepository.delete(findBoard);
    }


}
