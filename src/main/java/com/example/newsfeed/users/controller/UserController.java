package com.example.newsfeed.users.controller;

import com.example.newsfeed.users.dto.UpdatePasswordRequestDto;
import com.example.newsfeed.users.dto.UpdateUserProfileRequestDto;
import com.example.newsfeed.users.dto.UpdateUserProfileResponseDto;
import com.example.newsfeed.users.dto.UserDeleteRequsetDto;
import com.example.newsfeed.users.dto.UserFindResponseDto;
import com.example.newsfeed.users.dto.UserSaveRequestDto;
import com.example.newsfeed.users.dto.UserSaveResponseDto;
import com.example.newsfeed.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
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
        @PathVariable @Min(value = 1) Long userId
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
        @RequestBody UpdateUserProfileRequestDto requestDto
    ) {

        UpdateUserProfileResponseDto updatedUser = userService.update(userId, requestDto);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }


    /**
     * 유저 회원가입 메소드 추후 컨트롤러 이동 변경 > ex) AuthController
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

    /**
     * @param requsetDto 입력한 password 요청
     * @param session    로그인 된 세션id 조회
     * @param request    세션에 저장된 userid 요청
     * @param response   쿠키를 만료
     * @return 응답코드 200 성공, 401 미로그인, 400 비밀번호 미일치
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> isDeleted(@RequestBody UserDeleteRequsetDto requsetDto,
        HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        userService.isDeleted(requsetDto, session, request, response);
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
        @RequestBody UpdatePasswordRequestDto requestDto
    ) {

        userService.updatePassword(userId, requestDto);

        return new ResponseEntity<>(HttpStatus.OK);

    }


}
