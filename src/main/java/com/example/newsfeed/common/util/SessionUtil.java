package com.example.newsfeed.common.util;

import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    public static Long getUserId(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            throw new BusinessException(ExceptionCode.NOT_LOGIN_ERROR);
        }

        Long userId = (Long) session.getAttribute("user");

        if (userId == null) {
            throw new BusinessException(ExceptionCode.NOT_LOGIN_ERROR);
        }

        return userId;

    }

}
