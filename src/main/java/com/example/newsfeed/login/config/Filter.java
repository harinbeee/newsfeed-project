package com.example.newsfeed.login.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.util.PatternMatchUtils;

public class Filter implements jakarta.servlet.Filter {

  private static final String[] WHITE_LIST = {"/signup", "/login"};

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String requestURI = httpRequest.getRequestURI();

    if (!isWhiteList(requestURI)) {
      HttpSession session = httpRequest.getSession(false);

      if (session == null || session.getAttribute("user") == null) {
        // 로그인 페이지 이동 처리
        throw new RuntimeException("로그인 해주세요.");
      }
    }
    filterChain.doFilter(request, response);
  }

  private boolean isWhiteList(String requestURI) {
    return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
  }

}
