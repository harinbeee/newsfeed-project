package com.example.newsfeed.login.service;

import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    @Override
    public void login(String email, String password, HttpSession session,
        HttpServletResponse response) {

        User findEmail = userRepository.findByEmailElseThrow(email);

        if (findEmail.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "비밀번호를 찾을 수 없습니다.");
        }

        if (findEmail.getPassword().equals(password)) {
            session.setAttribute("user", findEmail.getId());

            Cookie cookie = new Cookie("SESSIONID", session.getId());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }
    }
}
