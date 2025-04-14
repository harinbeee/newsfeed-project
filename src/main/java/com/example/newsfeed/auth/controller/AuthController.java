package com.example.newsfeed.auth.controller;


import com.example.newsfeed.auth.dto.LoginRequestDto;
import com.example.newsfeed.auth.dto.UserSaveRequestDto;
import com.example.newsfeed.auth.dto.UserSaveResponseDto;
import com.example.newsfeed.auth.service.AuthService;
import com.example.newsfeed.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 유저 회원가입 메소드
     *
     * @param requestDto 회원가입 요청 정보가 담겨있는 {@link UserSaveRequestDto} 객체
     * @return 생성된 유저 정보가 담겨있는 {@link UserSaveResponseDto} 객체
     */
    @PostMapping("/signup")
    public ApiResponse<UserSaveResponseDto> signUp(
        @RequestBody @Valid UserSaveRequestDto requestDto
    ) {
        return ApiResponse.ok(authService.save(requestDto));
    }

    /**
     * 로그인 요청 컨트롤러
     *
     * @param requestDto 로그인 요청 정보가 담겨있는 {@link LoginRequestDto} 객체
     * @param request    세션에 저장된 유저 정보
     * @param response   쿠키에 세션 id 저장
     * @return 성공 시 200 OK
     */
    @PostMapping("/login")
    public ApiResponse<Void> login(
        @RequestBody @Valid LoginRequestDto requestDto,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        authService.login(requestDto, request.getSession(), response);
        return ApiResponse.ok();
    }

    /**
     * 로그아웃 요청 컨트롤러
     *
     * @param request  세션에 저장된 userid 요청
     * @param response 쿠키를 만료
     * @return 성공 시 200 OK
     */
    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest request, HttpServletResponse response) {

        authService.logout(request, response);

        return ApiResponse.ok();

    }


}
