package com.example.newsfeed.likes.controller;

import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.likes.dto.LikeSaveRequestDto;
import com.example.newsfeed.likes.dto.LikeSaveResponseDto;
import com.example.newsfeed.likes.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<LikeSaveResponseDto> save(
        @RequestBody @Valid LikeSaveRequestDto requestDto,
        @SessionAttribute("user") Long loginUserId
    ) {
        // 로그인한 유저와 requestDto 로 받은 id 값 비교
        if (!loginUserId.equals(requestDto.getUserId())) {
            throw new BusinessException(ExceptionCode.USER_ACCESS_DENIED);
        }

        LikeSaveResponseDto responseDto = likeService.save(requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


}
