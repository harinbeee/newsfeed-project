package com.example.newsfeed.auth.service;

import com.example.newsfeed.auth.dto.LoginRequestDto;
import com.example.newsfeed.auth.dto.UserSaveRequestDto;
import com.example.newsfeed.auth.dto.UserSaveResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    /**
     * 유저 회원가입 기능
     *
     * @param requestDto 회원가입 요청 정보가 담겨있는 {@link UserSaveRequestDto} 객체
     * @return 가입된 유저 정보가 담긴 {@link UserSaveResponseDto} 객체
     */
    UserSaveResponseDto save(UserSaveRequestDto requestDto);

    /**
     * 로그인 기능
     *
     * @param requestDto 로그인 요청 정보가 담겨있는 {@link LoginRequestDto} 객체
     * @param session    현재 요청에 대한 {@link HttpSession} 객체
     * @param response   로그인 성공 시 세션 쿠키를 추가할 {@link HttpServletResponse} 객체
     */
    void login(LoginRequestDto requestDto, HttpSession session, HttpServletResponse response);

    /**
     * 로그아웃 기능
     *
     * @param request  로그아웃 요청이 포함된 {@link HttpServletRequest} 객체
     * @param response 로그아웃 처리 후 쿠키 제거를 위한 {@link HttpServletResponse} 객체
     */
    void logout(HttpServletRequest request, HttpServletResponse response);

}
