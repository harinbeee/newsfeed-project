package com.example.newsfeed.login.controller;


import com.example.newsfeed.login.service.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
@RequiredArgsConstructor
public class LogoutController {

    private final LogoutService logoutService;

    /**
     * @param request  세션에 저장된 userid 요청
     * @param response 쿠키를 만료
     * @return 응답정보 200 성공, 401 미로그인
     */
    @PostMapping
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {

        logoutService.logout(request, response);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
