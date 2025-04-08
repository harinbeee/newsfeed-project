package com.example.newsfeed.login.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public interface LogoutService {

  void logout(HttpServletRequest request, HttpServletResponse response);
}
