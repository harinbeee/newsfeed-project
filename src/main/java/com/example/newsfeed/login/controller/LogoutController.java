package com.example.newsfeed.login.controller;


import com.example.newsfeed.login.service.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
@RequiredArgsConstructor
public class LogoutController {

  private final LogoutService logoutService;

  @PostMapping
  public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {

    return logoutService.logout(request, response);
  }
}
