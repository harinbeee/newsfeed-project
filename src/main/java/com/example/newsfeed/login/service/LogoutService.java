package com.example.newsfeed.login.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LogoutService {

  ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response);
}
