package com.example.newsfeed.likes.repository;

import com.example.newsfeed.likes.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LikeRepository extends JpaRepository<Like, Long> {

}


