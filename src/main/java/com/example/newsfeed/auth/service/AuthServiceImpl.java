package com.example.newsfeed.auth.service;

import com.example.newsfeed.auth.dto.LoginRequestDto;
import com.example.newsfeed.auth.dto.UserSaveRequestDto;
import com.example.newsfeed.auth.dto.UserSaveResponseDto;
import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.common.util.PasswordEncoder;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 회원가입 기능
     *
     * @param requestDto 회원가입 요청 정보가 담겨있는 {@link UserSaveRequestDto} 객체
     * @return 가입된 유저 정보가 담긴 {@link UserSaveResponseDto} 객체
     */
    @Transactional
    @Override
    public UserSaveResponseDto save(UserSaveRequestDto requestDto) {
        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 이미 가입 후 탈퇴된 이메일이거나 중복된 이메일 사용 시
        userRepository.findAllByEmailIncludingDeleted(requestDto.getEmail())
            .ifPresent(user -> {
                if (user.isDeleted()) {
                    throw new BusinessException(ExceptionCode.SIGNUP_FORBIDDEN);
                }
                throw new BusinessException(ExceptionCode.EMAIL_ALREADY_USED);
            });

        User user = new User(
            requestDto.getEmail(),
            encodedPassword,
            requestDto.getUsername(),
            requestDto.getNickname(),
            requestDto.getPhone(),
            requestDto.getProfilePicture(),
            requestDto.getDescription()
        );

        return UserSaveResponseDto.toDto(userRepository.save(user));

    }

    /**
     * 로그인 기능
     *
     * @param requestDto 로그인 요청 정보가 담겨있는 {@link LoginRequestDto} 객체
     * @param session    현재 요청에 대한 {@link HttpSession} 객체
     * @param response   로그인 성공 시 세션 쿠키를 추가할 {@link HttpServletResponse} 객체
     */
    @Override
    public void login(
        LoginRequestDto requestDto,
        HttpSession session,
        HttpServletResponse response
    ) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        // 회원 탈퇴시
        userRepository.findAllByEmailIncludingDeleted(email)
            .ifPresent(user -> {
                if (user.isDeleted()) {
                    throw new BusinessException(ExceptionCode.LOGIN_FORBIDDEN);
                }
            });

        User findUser = userRepository.findByEmailElseThrow(email);
        Long sessionUserId = (Long) session.getAttribute("user");

        if (sessionUserId != null) {
            throw new BusinessException(ExceptionCode.ALREADY_LOGIN);
        }

        if (passwordEncoder.matches(password, findUser.getPassword())) {
            session.setAttribute("user", findUser.getId());

            Cookie cookie = new Cookie("SESSIONID", session.getId());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
        } else {
            throw new BusinessException(ExceptionCode.PASSWORD_INVALID);
        }

    }

    /**
     * 로그아웃 기능
     *
     * @param request  로그아웃 요청이 포함된 {@link HttpServletRequest} 객체
     * @param response 로그아웃 처리 후 쿠키 제거를 위한 {@link HttpServletResponse} 객체
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();

            Cookie cookie = new Cookie("SESSIONID", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        } else {
            throw new BusinessException(ExceptionCode.NOT_LOGIN_ERROR);
        }

    }

}
