package com.example.newsfeed.friends.controller;

import com.example.newsfeed.friends.dto.FriendAcceptRequestDto;
import com.example.newsfeed.friends.dto.FriendAcceptResponseDto;
import com.example.newsfeed.friends.dto.FriendFindResponseDto;
import com.example.newsfeed.friends.dto.FriendRequestFindResponseDto;
import com.example.newsfeed.friends.dto.FriendSaveRequestDto;
import com.example.newsfeed.friends.dto.FriendSaveResponseDto;
import com.example.newsfeed.friends.service.FriendRequestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follows/requests")
@RequiredArgsConstructor
@Validated
public class FriendRequestController {

    private final FriendRequestService requestService;


    /**
     * 친구요청  저장 컨트롤러
     *
     * @param requestDto 친구 요청 정보가 담겨있는 {@link FriendSaveRequestDto} 객체
     * @return 친구 요청 정보가 담겨있는 {@link FriendSaveResponseDto} 객체
     */
    @PostMapping
    public ResponseEntity<FriendAcceptResponseDto> save(
        @RequestBody FriendAcceptRequestDto requestDto,
        HttpServletRequest request
    ) {

        HttpSession session = request.getSession(false);

        Long fromUserId = (Long) session.getAttribute("user"); // 로그인 한 유저의 아이디

        FriendAcceptResponseDto responseDto = requestService.save(requestDto, fromUserId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    /**
     * 나에게 친구 요청 보낸 유저 조회 컨트롤러
     *
     * @param request 로그인 정보가 담겨있는 {@link HttpServletRequest} 객체
     * @return 나에게 친구 요청 보낸 유저 정보가 담긴 {@link FriendFindResponseDto} 객체 리스트
     */
    @GetMapping("/toUserId")
    public ResponseEntity<List<FriendRequestFindResponseDto>> findByToUserId(
        HttpServletRequest request
    ) {

        HttpSession session = request.getSession(false);

        Long toUserId = (Long) session.getAttribute("user"); // 로그인 한 유저의 아이디

        List<FriendRequestFindResponseDto> responseDtoList = requestService.findByToUserId(
            toUserId); // 날 팔로우 하는 사람 리스트

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);

    }

    /**
     * 내가 친구 요청 보낸 유저 조회 컨트롤러
     *
     * @param request 로그인 정보가 담겨있는 {@link HttpServletRequest} 객체
     * @return 내가 친구 요청 보낸 유저 정보가 담긴 {@link FriendFindResponseDto} 객체 리스트
     */
    @GetMapping("/fromUserId")
    public ResponseEntity<List<FriendRequestFindResponseDto>> findByIdFromUserId(
        HttpServletRequest request
    ) {

        HttpSession session = request.getSession(false);

        Long fromUserId = (Long) session.getAttribute("user"); // 로그인 한 유저의 아이디

        List<FriendRequestFindResponseDto> responseDtoList = requestService.findByFromUserId(
            fromUserId); // 내가 팔로우 한 사람 리스트
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);

    }

    /**
     * 친구 요청 거절 컨트롤러
     *
     * @param fromUserId 요청 거절 할 유저 식별자
     * @param request    로그인 정보가 담겨있는 {@link HttpServletRequest} 객체
     * @return 성공 시 200 OK
     */
    @DeleteMapping("/{fromUserId}")
    public ResponseEntity<String> reject(
        @PathVariable @Min(1) Long fromUserId,
        HttpServletRequest request
    ) {

        HttpSession session = request.getSession(false);

        Long toUserId = (Long) session.getAttribute("user"); // 로그인 한 유저의 아이디

        requestService.reject(fromUserId, toUserId);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
