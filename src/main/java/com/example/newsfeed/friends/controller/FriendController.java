package com.example.newsfeed.friends.controller;

import com.example.newsfeed.common.response.ApiResponse;
import com.example.newsfeed.friends.dto.FriendFindResponseDto;
import com.example.newsfeed.friends.dto.FriendSaveRequestDto;
import com.example.newsfeed.friends.dto.FriendSaveResponseDto;
import com.example.newsfeed.friends.service.FriendService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
@Validated
public class FriendController {

    private final FriendService friendService;

    /**
     * 팔로우 저장 요청 컨트롤러
     *
     * @param requestDto 팔로우 요청 정보가 담겨있는 {@link FriendSaveRequestDto} 객체
     * @return 팔로우 정보가 담겨있는 {@link FriendSaveResponseDto} 객체
     */
    @PostMapping
    public ApiResponse<FriendSaveResponseDto> save(
        @RequestBody FriendSaveRequestDto requestDto,
        HttpServletRequest request
    ) {

        HttpSession session = request.getSession(false);
        Long fromUserId = (Long) session.getAttribute("user"); // 로그인 한 유저의 아이디

        return ApiResponse.ok(friendService.save(requestDto, fromUserId));

    }

    /**
     * 나를 팔로우하는 유저 조회 컨트롤러
     *
     * @param request 로그인 정보가 담겨있는 {@link HttpServletRequest} 객체
     * @return 나를 팔로우하는 유저 정보가 담긴 {@link FriendFindResponseDto} 객체 리스트
     */
    @GetMapping("/toUserId")
    public ApiResponse<List<FriendFindResponseDto>> findByToUserId(
        HttpServletRequest request
    ) {

        HttpSession session = request.getSession(false);
        Long toUserId = (Long) session.getAttribute("user"); // 로그인 한 유저의 아이디

        return ApiResponse.ok(friendService.findByToUserId(toUserId));

    }

    /**
     * 내가 팔로우하는 유저 조회 컨트롤러
     *
     * @param request 로그인 정보가 담겨있는 {@link HttpServletRequest} 객체
     * @return 내가를 팔로우하는 유저 정보가 담긴 {@link FriendFindResponseDto} 객체 리스트
     */
    @GetMapping("/fromUserId")
    public ApiResponse<List<FriendFindResponseDto>> findByIdFromUserId(
        HttpServletRequest request
    ) {

        HttpSession session = request.getSession(false);
        Long fromUserId = (Long) session.getAttribute("user"); // 로그인 한 유저의 아이디

        return ApiResponse.ok(friendService.findByFromUserId(fromUserId));

    }

    /**
     * 팔로우 취소 요청 컨트롤러
     *
     * @param toUserId 팔로우를 취소할 유저 식별자
     * @param request  로그인 정보가 담겨있는 {@link HttpServletRequest} 객체
     * @return 성공 시 200 OK
     */
    @DeleteMapping("/{toUserId}")
    public ApiResponse<String> delete(
        @PathVariable @Min(1) Long toUserId,
        HttpServletRequest request
    ) {

        HttpSession session = request.getSession(false);
        Long fromUserId = (Long) session.getAttribute("user"); // 로그인 한 유저의 아이디

        friendService.delete(toUserId, fromUserId);

        return ApiResponse.ok();

    }

}
