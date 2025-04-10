package com.example.newsfeed.friends.entity;

import com.example.newsfeed.users.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "friend_requsts")
@NoArgsConstructor
public class FriendRequest { // 친구 요청 저장 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "to_user_id")
    private User toUser; // 친구 요청을 받은 사람

    @ManyToOne
    @JoinColumn(nullable = false, name = "from_user_id")
    private User fromUser; // 친구 요청을 보낸 사람

    public FriendRequest(User toUser, User fromUser) {
        this.toUser = toUser;
        this.fromUser = fromUser;
    }
}
