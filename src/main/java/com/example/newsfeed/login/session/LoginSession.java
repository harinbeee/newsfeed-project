package com.example.newsfeed.login.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginSession {

  public void login(String name, HttpSession session, HttpServletResponse response) {
    session.setAttribute("user", name);

    Cookie cookie = new Cookie("SESSIONID", session.getId());
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    response.addCookie(cookie);
  }
}
