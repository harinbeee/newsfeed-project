package com.example.newsfeed.login.service;

import com.example.newsfeed.login.session.LoginSession;
import com.example.newsfeed.users.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

  private final UserRepository userRepository;
  private final LoginSession loginSession;

  public void login(String email, String password, HttpSession session,
      HttpServletResponse response) {

    // 코드 작성

    loginSession.login(email, session, response);
  }
}
