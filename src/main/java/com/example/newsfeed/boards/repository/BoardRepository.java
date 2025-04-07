package com.example.newsfeed.boards.repository;


import com.example.newsfeed.boards.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
