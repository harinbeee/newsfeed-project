package com.example.newsfeed.users.service;

import com.example.newsfeed.users.dto.UpdateUserProfileRequestDto;
import com.example.newsfeed.users.dto.UpdateUserProfileResponseDto;
import com.example.newsfeed.users.dto.UserFindResponseDto;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * User 프로필 userId 값으로  조회 메소드
     *
     * @param userId 조회 할 유저 아이디
     * @return ResponseEntity 응답데이터와 200 코드
     */
    @Override
    public ResponseEntity<UserFindResponseDto> find(Long userId) {

        User user = userRepository.findByIdElseThrow(userId); // 해당 user id 에 맞는 데이터 있으면 가져오고 없으면 오류

        return new ResponseEntity<>(
            UserFindResponseDto.toDto(user),
            HttpStatus.OK); // 데이터 응답 dto 로 변환 후 리턴
    }

    /**
     * 유저 프로필 수정 메소드
     *
     * @param userId     유저 식별자 ID
     * @param requestDto 클라이언트 요청 수정 데이터가 담겨있는 DTO 객체
     * @return 수정이 완료된 유저 정보가 담긴 응답 DTO 객체
     */
    @Override
    @Transactional
    public UpdateUserProfileResponseDto update(
        Long userId,
        UpdateUserProfileRequestDto requestDto) {

        User findUser = userRepository.findByIdElseThrow(userId);

        // 별명, 전화번호, 프로필사진, 설명은 null이 될 수 있으므로 선택적으로 데이터 설정
        Optional.ofNullable(requestDto.getNickname()).ifPresent(findUser::setNickname);
        Optional.ofNullable(requestDto.getPhone()).ifPresent(findUser::setPhone);
        Optional.ofNullable(requestDto.getProfilePicture()).ifPresent(findUser::setProfilePicture);
        Optional.ofNullable(requestDto.getDescription()).ifPresent(findUser::setDescription);

        return UpdateUserProfileResponseDto.toDto(findUser);

    }


}
