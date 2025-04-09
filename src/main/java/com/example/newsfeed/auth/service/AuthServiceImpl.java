package com.example.newsfeed.auth.service;

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

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void login(String email, String password, HttpSession session,
        HttpServletResponse response) {

        User findUser = userRepository.findByEmailElseThrow(email);

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
