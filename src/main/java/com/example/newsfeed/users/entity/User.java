package com.example.newsfeed.users.entity;

import com.example.newsfeed.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Table(name = "users")
@Where(clause = "is_deleted = false")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Setter
    @Column(nullable = false)
    private String nickname;

    @Setter
    @Column
    private String phone;

    @Setter
    @Column
    private String profilePicture;

    @Setter
    @Column
    private String description;


    public User() {

    }

    public User(String email, String password, String username, String nickname, String phone,
        String profilePicture, String description) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.phone = phone;
        this.profilePicture = profilePicture;
        this.description = description;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

}
