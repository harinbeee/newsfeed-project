package com.example.newsfeed.users.service;

import com.example.newsfeed.users.dto.UserFindResponseDto;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  /**
   * User 프로필 userId 값으로  조회 메소드
   *
   * @param userId 조회 할 유저 아이디
   * @return ResponseEntity 응답데이터와 200 코드
   */
  @Override
  public ResponseEntity<UserFindResponseDto> find(Long userId) {

    User user = userRepository.findByIdElseThrow(userId); // 해당 user id 에 맞는 데이터 있으면 가져오고 없으면 오류

    return new ResponseEntity<>(
        UserFindResponseDto.toDto(user),
        HttpStatus.OK); // 데이터 응답 dto 로 변환 후 리턴
  }

  @Override
  public void isDeleted(HttpSession session) {

    Long sessionUserId = (Long) session.getAttribute("user");
    User user = userRepository.findByIdElseThrow(sessionUserId);


  }
}
