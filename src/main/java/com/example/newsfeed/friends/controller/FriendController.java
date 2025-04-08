package com.example.newsfeed.friends.controller;

import com.example.newsfeed.friends.dto.FriendFindResponseDto;
import com.example.newsfeed.friends.dto.FriendSaveRequestDto;
import com.example.newsfeed.friends.dto.FriendSaveResponseDto;
import com.example.newsfeed.friends.service.FriendService;
import jakarta.validation.constraints.Min;
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
     * @param requestDto toUserId, fromUserId 두 개 받음
     * @return 팔로우 유저 ID, 와 팔로잉 유저 ID, 식별자 ID , 응답코드
     */
    @PostMapping
    public ResponseEntity<FriendSaveResponseDto> save(
        @RequestBody FriendSaveRequestDto requestDto
    ) {
        FriendSaveResponseDto responseDto = friendService.save(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{toUserId}")
    public ResponseEntity<FriendFindResponseDto> findByToUserId(
        @PathVariable @Min(1) Long toUserId
    ) {
        FriendFindResponseDto responseDto = friendService.findByToUserId(toUserId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{fromUserId}")
    public ResponseEntity<FriendFindResponseDto> findByIdFromUserId(
        @PathVariable @Min(1) Long fromUserId
    ) {
        FriendFindResponseDto responseDto = friendService.findByIdFromUserId(fromUserId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{toUserId}/{fromUserId}")
    public ResponseEntity<String> delete(
        @PathVariable @Min(1) Long toUserId,
        @PathVariable @Min(1) Long fromUserId
    ) {
        friendService.delete(toUserId, fromUserId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
