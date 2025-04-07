package com.example.newsfeed.users.repository;

import com.example.newsfeed.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
