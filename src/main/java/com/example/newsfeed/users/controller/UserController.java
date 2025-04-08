package com.example.newsfeed.users.controller;

import com.example.newsfeed.users.dto.UserFindResponseDto;
import com.example.newsfeed.users.service.UserService;
import com.example.newsfeed.users.dto.UserSaveRequestDto;
import com.example.newsfeed.users.dto.UserSaveResponseDto;
import com.example.newsfeed.users.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return userService.find(userId);
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
        return userService.save(requestDto);
    }

}
