package com.example.newsfeed.login.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

  public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {

    HttpSession session = request.getSession(false);

    if (session != null) {
      session.invalidate();

      Cookie cookie = new Cookie("SESSIONID", null);
      cookie.setMaxAge(0);
      cookie.setPath("/");
      response.addCookie(cookie);

      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }
}
