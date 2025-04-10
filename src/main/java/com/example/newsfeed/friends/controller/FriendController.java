package com.example.newsfeed.friends.controller;

import com.example.newsfeed.friends.dto.FriendFindResponseDto;
import com.example.newsfeed.friends.dto.FriendSaveRequestDto;
import com.example.newsfeed.friends.dto.FriendSaveResponseDto;
import com.example.newsfeed.friends.service.FriendService;
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
    public ResponseEntity<FriendSaveResponseDto> save(
        @RequestBody FriendSaveRequestDto requestDto,
        HttpServletRequest request
    ) {

        HttpSession session = request.getSession(false);

        Long toUserId = (Long) session.getAttribute("user"); // 로그인 한 유저의 아이디

        FriendSaveResponseDto responseDto = friendService.accept(requestDto, toUserId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    /**
     * 내 친구 목록 조회 컨트롤러
     *
     * @param request 로그인 정보가 담겨있는 {@link HttpServletRequest} 객체
     * @return 내 친구 목록이 담긴 {@link FriendFindResponseDto} 객체 리스트
     */
    @GetMapping("/userId")
    public ResponseEntity<List<FriendFindResponseDto>> findFriendListByFromUserId(
        HttpServletRequest request
    ) {

        HttpSession session = request.getSession(false);

        Long fromUserId = (Long) session.getAttribute("user"); // 로그인 한 유저의 아이디

        List<FriendFindResponseDto> responseDtoList = friendService.findFriendListByFromUserId(
            fromUserId); // 내 친구 리스트
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);

    }

    /**
     * 친구 삭제 요청 컨트롤러
     *
     * @param toUserId 친구 삭제할 유저 식별자
     * @param request  로그인 정보가 담겨있는 {@link HttpServletRequest} 객체
     * @return 성공 시 200 OK
     */
    @DeleteMapping("/{toUserId}")
    public ResponseEntity<String> delete(
        @PathVariable @Min(1) Long toUserId,
        HttpServletRequest request
    ) {

        HttpSession session = request.getSession(false);

        Long fromUserId = (Long) session.getAttribute("user"); // 로그인 한 유저의 아이디

        friendService.delete(toUserId, fromUserId);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
