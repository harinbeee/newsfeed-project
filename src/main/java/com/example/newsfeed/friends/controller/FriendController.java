package com.example.newsfeed.friends.controller;

import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
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
     * 팔로우 저장 컨트롤러
     *
     * @param requestDto > toUserId, 내가 팔로우 한 사람 가져옴
     * @return 팔로우 유저 ID, 와 팔로잉 유저 ID, 식별자 ID , 응답코드
     */
    @PostMapping
    public ResponseEntity<FriendSaveResponseDto> save(
        @RequestBody FriendSaveRequestDto requestDto,
        HttpServletRequest request
    ) {

        HttpSession session = request.getSession(false);

        Long fromUserId = (Long) session.getAttribute("user"); // 로그인 한 유저의 아이디

        if (fromUserId.equals(requestDto.getToUserId())) { // 같은 유저를 팔로우 하는지 체크
            throw new BusinessException(ExceptionCode.NOT_VALID_ERROR);
        }

        FriendSaveResponseDto responseDto = friendService.save(
            requestDto.getToUserId(),
            fromUserId
        );

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 날 팔로우 하고 있는 사람들 조회
     *
     * @return FriendFindResponseDto 테이블 id, toUserId, fromUserId, 200 응답코드
     */
    @GetMapping("/toUserId")
    public ResponseEntity<List<FriendFindResponseDto>> findByToUserId(
        HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);

        Long toUserId = (Long) session.getAttribute("user"); // 로그인 한 유저의 아이디

        List<FriendFindResponseDto> responseDtoList = friendService.findByToUserId(
            toUserId); // 날 팔로우 하는 사람 리스트
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    /**
     * 내가 팔로우한 사람들 조회
     *
     * @return FriendFindResponseDto 테이블 id, toUserId, fromUserId, 200 응답코드
     */
    @GetMapping("/fromUserId")
    public ResponseEntity<List<FriendFindResponseDto>> findByIdFromUserId(
        HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);

        Long fromUserId = (Long) session.getAttribute("user"); // 로그인 한 유저의 아이디

        List<FriendFindResponseDto> responseDtoList = friendService.findByFromUserId(
            fromUserId); // 내가 팔로우 한 사람 리스트
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    /**
     * 팔로우 취소
     *
     * @param toUserId 팔로우 취소 할 사람 아이디
     * @return 상태코드 200
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
