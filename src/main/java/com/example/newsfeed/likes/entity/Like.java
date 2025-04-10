package com.example.newsfeed.likes.entity;

import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.boards.entity.Comment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(nullable = false)
    private Long userId;

    public Like(Long userId, Board board) {
        this.userId = userId;
        this.board = board;
    }

    public Like() {

    }
}
