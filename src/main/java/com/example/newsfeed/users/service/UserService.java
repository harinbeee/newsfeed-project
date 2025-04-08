package com.example.newsfeed.users.service;

import com.example.newsfeed.users.dto.UserFindResponseDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

public interface UserService {

  ResponseEntity<UserFindResponseDto> find(Long userId);

  void isDeleted(HttpSession session);
}
