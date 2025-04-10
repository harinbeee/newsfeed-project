package com.example.newsfeed.friends.entity;

import com.example.newsfeed.common.entity.IsDelete;
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
import org.hibernate.annotations.Where;

@Entity
@Getter
@Table(name = "friends")
@NoArgsConstructor
@Where(clause = "is_deleted = false")
public class Friend extends IsDelete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "to_user_id")
    private User toUser; //팔로우 유저가 팔로잉한 유저 아이디

    @ManyToOne
    @JoinColumn(nullable = false, name = "from_user_id")
    private User fromUser; // 팔로우한 유저 아이디

    public Friend(User toUser, User fromUser) {
        this.toUser = toUser;
        this.fromUser = fromUser;
    }

}
