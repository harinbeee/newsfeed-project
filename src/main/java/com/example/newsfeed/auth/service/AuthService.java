package com.example.newsfeed.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    void login(String email, String password, HttpSession session, HttpServletResponse response);

    void logout(HttpServletRequest request, HttpServletResponse response);
}
