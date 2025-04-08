package com.example.newsfeed.users.controller;

import com.example.newsfeed.users.dto.UserFindResponseDto;
import com.example.newsfeed.users.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

  private final UserService userService;

  /**
   * 유저 프로필 조회 컨트롤러
   *
   * @param userId 조회 할 유저 ID
   * @return 조회 한 유저 데이터 와 응답코드 성공 200 실패 401
   */
  @GetMapping("/{userId}")
  public ResponseEntity<UserFindResponseDto> find(
      @PathVariable @Min(value = 1) Long userId
  ) {
    return userService.find(userId);
  }


  @PatchMapping("/{userId")
  public ResponseEntity<Void> isDeleted(HttpSession session) {
    userService.isDeleted(session);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
