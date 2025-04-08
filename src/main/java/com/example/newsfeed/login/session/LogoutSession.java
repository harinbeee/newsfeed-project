package com.example.newsfeed.login.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutSession {

  public void logout(HttpServletRequest request, HttpServletResponse response) {

    HttpSession session = request.getSession(false);

    if (session != null) {
      session.invalidate();
    }

    Cookie cookie = new Cookie("SESSIONID", null);
    cookie.setMaxAge(0);
    cookie.setPath("/");
    response.addCookie(cookie);
  }
}
