package com.example.newsfeed.boards.entity;

import com.example.newsfeed.boards.dto.BoardRequestDto;
import com.example.newsfeed.common.entity.BaseEntity;
import com.example.newsfeed.users.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "boards")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String contents;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 연관관계 설정, 게시글 저장시 댓글도 같이 처리
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Board(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

}
