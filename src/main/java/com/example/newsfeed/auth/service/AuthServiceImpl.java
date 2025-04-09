package com.example.newsfeed.auth.service;

import com.example.newsfeed.auth.dto.UserSaveRequestDto;
import com.example.newsfeed.auth.dto.UserSaveResponseDto;
import com.example.newsfeed.common.encoder.PasswordEncoder;
import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
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
     * @param requestDto 가입정보
     * @return UserSaveResponseDto 와 응답코드
     */
    @Transactional
    @Override
    public UserSaveResponseDto save(UserSaveRequestDto requestDto) {
        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(requestDto.getEmail(), encodedPassword, requestDto.getUsername(),
            requestDto.getNickname(), requestDto.getPhone(), requestDto.getProfilePicture(),
            requestDto.getDescription());

        return UserSaveResponseDto.toDto(userRepository.save(user));

    }

    @Override
    public void login(String email, String password, HttpSession session,
        HttpServletResponse response) {

        User findUser = userRepository.findByEmailElseThrow(email);
        Long sessionUserId = (Long) session.getAttribute("user");

        if (sessionUserId != null) {
            throw new BusinessException(ExceptionCode.ALREADY_LOGIN);
        }

        if (findUser.isDeleted()) {
            throw new BusinessException(ExceptionCode.LOGIN_FORBIDDEN);
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
