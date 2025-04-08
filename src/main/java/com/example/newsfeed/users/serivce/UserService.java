package com.example.newsfeed.users.serivce;

import com.example.newsfeed.users.dto.UserFindResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<UserFindResponseDto> find(Long userId);
}
