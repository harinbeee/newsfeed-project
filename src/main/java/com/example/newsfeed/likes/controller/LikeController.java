package com.example.newsfeed.likes.controller;

import static com.example.newsfeed.common.util.SessionUtil.getUserId;

import com.example.newsfeed.common.response.ApiResponse;
import com.example.newsfeed.likes.dto.LikeSaveRequestDto;
import com.example.newsfeed.likes.dto.LikeSaveResponseDto;
import com.example.newsfeed.likes.service.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    /**
     * 좋아요 저장 요청 컨트롤러
     *
     * @param requestDto 좋아요 요청 정보가 담긴 {@link LikeSaveRequestDto} 객체
     * @param
     * @return 좋아요 정보가 담긴 {@link LikeSaveResponseDto} 객체
     */
    @PostMapping
    public ApiResponse<LikeSaveResponseDto> save(
        @RequestBody @Valid LikeSaveRequestDto requestDto,
        HttpServletRequest request
    ) {
        return ApiResponse.created(likeService.save(requestDto, getUserId(request)));
    }
}
