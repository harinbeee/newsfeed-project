package com.example.newsfeed.likes.entity;

import com.example.newsfeed.boards.entity.Board;
import com.example.newsfeed.users.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

//    @ManyToOne
//    @JoinColumn(name = "comment_id")
//    private Comment comment;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
