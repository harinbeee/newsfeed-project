package com.example.newsfeed.login.service;

import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    @Override
    public void login(String email, String password, HttpSession session,
        HttpServletResponse response) {

        User findUser = userRepository.findByEmailElseThrow(email);

<<<<<<< HEAD
        if (findUser.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "탈퇴한 회원은 로그인 하실 수 없습니다.");
        }

        if (findUser.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "비밀번호를 찾을 수 없습니다.");
        }

=======
>>>>>>> 37a6a745df7f8c653e7c1b9378c63e6c91bec608
        if (findUser.getPassword().equals(password)) {
            session.setAttribute("user", findUser.getId());

            Cookie cookie = new Cookie("SESSIONID", session.getId());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
        } else {
            throw new BusinessException(ExceptionCode.PASSWORD_INVALID);
        }
    }
}
