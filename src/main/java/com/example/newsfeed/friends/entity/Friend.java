package com.example.newsfeed.friends.entity;

import com.example.newsfeed.common.entity.IsDelete;
import com.example.newsfeed.users.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "friends")
@NoArgsConstructor
public class Friend extends IsDelete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "to_user_id")
    private User toUser; //친구 요청 받은 유저 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "from_user_id")
    private User fromUser; // 친구 요청한 유저 아이디

    public Friend(User toUser, User fromUser) {
        this.toUser = toUser;
        this.fromUser = fromUser;
    }

}
