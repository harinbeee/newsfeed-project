package com.example.newsfeed.login.controller;


import com.example.newsfeed.login.dto.LoginRequestDto;
import com.example.newsfeed.login.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    /**
     * @param requestDto 이메일과 비밀번호 요청
     * @param session    세션에 userid 입력
     * @param response   쿠키에 세션id 저장
     * @return 응답코드 200 성공, 400 접근금지, 400 비밀번호 미일치
     */
    @PostMapping
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequestDto requestDto,
        HttpSession session, HttpServletResponse response) {
        loginService.login(requestDto.getEmail(), requestDto.getPassword(), session, response);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
