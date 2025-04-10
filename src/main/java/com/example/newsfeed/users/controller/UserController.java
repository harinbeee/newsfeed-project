package com.example.newsfeed.users.controller;

import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.users.dto.UpdatePasswordRequestDto;
import com.example.newsfeed.users.dto.UpdateUserProfileRequestDto;
import com.example.newsfeed.users.dto.UpdateUserProfileResponseDto;
import com.example.newsfeed.users.dto.UserDeleteRequsetDto;
import com.example.newsfeed.users.dto.UserFindResponseDto;
import com.example.newsfeed.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 유저 프로필 조회 컨트롤러
     *
     * @param userId 조회 할 유저 ID
     * @return 조회 한 유저 데이터 와 응답코드 성공 200 실패 401
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserFindResponseDto> find(
        @PathVariable Long userId
    ) {
        UserFindResponseDto responseDto = userService.find(userId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 유저 프로필 수정 요청 컨트롤러
     *
     * @param userId     유저 식별자 id
     * @param requestDto 수정할 데이터가 담겨있는 {@link UpdateUserProfileRequestDto} 객체
     * @return 수정된 정보가 담겨있는 응답 객체
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<UpdateUserProfileResponseDto> update(
        @PathVariable Long userId,
        @RequestBody UpdateUserProfileRequestDto requestDto,
        HttpSession session
    ) {
        Long loginId = (Long) session.getAttribute("user");
        // 로그인한 유저와 수정하려는 유저의 id 비교
        if (!userId.equals(loginId)) {
            throw new BusinessException(ExceptionCode.USER_ACCESS_DENIED);
        }

        UpdateUserProfileResponseDto updatedUser = userService.update(userId, requestDto);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }

    /**
     * @param requestDto 입력한 password 요청
     * @param userId     로그인 한 id
     * @param request    세션에 저장된 userid 요청
     * @param response   쿠키를 만료
     * @return 응답코드 200 성공, 401 미로그인, 400 비밀번호 미일치
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> isDeleted(
        @RequestBody UserDeleteRequsetDto requestDto,
        @PathVariable Long userId,
        HttpServletRequest request,
        HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        Long sessionUserId = (Long) session.getAttribute("user");
        // 로그인한 유저와 삭제하려는 유저의 id 비교
        if (!userId.equals(sessionUserId)) {
            throw new BusinessException(ExceptionCode.USER_ACCESS_DENIED);
        }

        userService.isDeleted(requestDto, userId, request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 비밀번호 수정 요청 컨트롤러
     *
     * @param userId     유저 식별자 ID
     * @param requestDto 클라이언트 요청 정보가 담겨있는 DTO 객체
     * @return 응답코드 200 성공
     */
    @PatchMapping("/{userId}/update-password")
    public ResponseEntity<Void> updatePassword(
        @PathVariable Long userId,
        @RequestBody UpdatePasswordRequestDto requestDto,
        HttpSession session
    ) {

        Long sessionUserId = (Long) session.getAttribute("user");
        // 로그인한 유저와 삭제하려는 유저의 id 비교
        if (!userId.equals(sessionUserId)) {
            throw new BusinessException(ExceptionCode.USER_ACCESS_DENIED);
        }

        userService.updatePassword(userId, requestDto);

        return new ResponseEntity<>(HttpStatus.OK);

    }


}
