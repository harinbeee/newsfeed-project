package com.example.newsfeed.auth.controller;


import com.example.newsfeed.auth.dto.LoginRequestDto;
import com.example.newsfeed.auth.service.AuthService;
import com.example.newsfeed.users.dto.UserSaveRequestDto;
import com.example.newsfeed.users.dto.UserSaveResponseDto;
import com.example.newsfeed.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    /**
     * @param requestDto 이메일과 비밀번호 요청
     * @param session    세션에 userid 입력
     * @param response   쿠키에 세션id 저장
     * @return 응답코드 200 성공, 400 접근금지, 400 비밀번호 미일치
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequestDto requestDto,
        HttpSession session, HttpServletResponse response) {
        authService.login(requestDto.getEmail(), requestDto.getPassword(), session, response);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param request  세션에 저장된 userid 요청
     * @param response 쿠키를 만료
     * @return 응답정보 200 성공, 401 미로그인
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {

        authService.logout(request, response);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 유저 회원가입 메소드
     *
     * @param requestDto { “email”: String, “password”: String, “username”: String, “nickname”:
     *                   String, “phone”: String, “profile_picture”: String, “description”: String,
     *                   }
     * @return UserSaveResponseDto, 응답코드 200 성공 401 에러
     */
    @PostMapping("/signup")
    public ResponseEntity<UserSaveResponseDto> signUp(
        @RequestBody @Valid UserSaveRequestDto requestDto
    ) {

        userService.findByEmail(requestDto.getEmail()); // 입력한 이메일 이미 있는지 체크 중복이면 Exception

        UserSaveResponseDto responseDto = userService.save(requestDto); // 중복 없으면 가입

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

}
