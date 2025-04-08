package com.example.newsfeed.login.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

  public void login(String email, String password, HttpSession session,
      HttpServletResponse response) {

    session.setAttribute("user", email);

    Cookie cookie = new Cookie("SESSIONID", session.getId());
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    response.addCookie(cookie);
  }
}
