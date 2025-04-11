package com.example.newsfeed.users.entity;

import com.example.newsfeed.auth.dto.UserSaveRequestDto;
import com.example.newsfeed.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Table(name = "users")
@Where(clause = "is_deleted = false")
@NoArgsConstructor
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

    public User(
        String email, String password, String username, String nickname,
        String phone, String profilePicture, String description
    ) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.phone = phone;
        this.profilePicture = profilePicture;
        this.description = description;
    }

    public static User of(String encodedPassword, UserSaveRequestDto dto) {
        return new User(
            dto.getEmail(),
            encodedPassword,
            dto.getUsername(),
            dto.getNickname(),
            dto.getPhone(),
            dto.getProfilePicture(),
            dto.getDescription()
        );
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

}
