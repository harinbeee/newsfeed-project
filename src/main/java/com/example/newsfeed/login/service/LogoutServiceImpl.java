package com.example.newsfeed.login.service;

import com.example.newsfeed.login.session.LogoutSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

  private final LogoutSession logoutSession;

  public void logout(HttpServletRequest request, HttpServletResponse response) {
    logoutSession.logout(request, response);
  }
}
