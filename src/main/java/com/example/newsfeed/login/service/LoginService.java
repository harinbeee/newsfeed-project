package com.example.newsfeed.login.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

  void login(String email, String password, HttpSession session, HttpServletResponse response);
}
