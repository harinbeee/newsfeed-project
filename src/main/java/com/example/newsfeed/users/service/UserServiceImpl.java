package com.example.newsfeed.users.service;

import com.example.newsfeed.login.service.LogoutService;
import com.example.newsfeed.users.dto.UpdatePasswordRequestDto;
import com.example.newsfeed.users.dto.UpdateUserProfileRequestDto;
import com.example.newsfeed.users.dto.UpdateUserProfileResponseDto;
import com.example.newsfeed.users.dto.UserDeleteRequsetDto;
import com.example.newsfeed.users.dto.UserFindResponseDto;
import com.example.newsfeed.users.dto.UserSaveRequestDto;
import com.example.newsfeed.users.dto.UserSaveResponseDto;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LogoutService logoutService;

    /**
     * User 프로필 userId 값으로  조회 메소드
     *
     * @param userId 조회 할 유저 아이디
     * @return ResponseEntity 응답데이터와 200 코드
     */
    @Override
    public ResponseEntity<UserFindResponseDto> find(Long userId) {

        User user = userRepository.findByIdElseThrow(userId); // 해당 user id 에 맞는 데이터 있으면 가져오고 없으면 오류

        return new ResponseEntity<>(UserFindResponseDto.toDto(user),
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
    public UpdateUserProfileResponseDto update(Long userId,
        UpdateUserProfileRequestDto requestDto) {

        User findUser = userRepository.findByIdElseThrow(userId);

        // 별명, 전화번호, 프로필사진, 설명은 null이 될 수 있으므로 선택적으로 데이터 설정
        Optional.ofNullable(requestDto.getNickname()).ifPresent(findUser::setNickname);
        Optional.ofNullable(requestDto.getPhone()).ifPresent(findUser::setPhone);
        Optional.ofNullable(requestDto.getProfilePicture()).ifPresent(findUser::setProfilePicture);
        Optional.ofNullable(requestDto.getDescription()).ifPresent(findUser::setDescription);

        return UpdateUserProfileResponseDto.toDto(findUser);

    }


    /**
     * 유저 회원가입 기능
     *
     * @param requestDto 가입정보
     * @return UserSaveResponseDto 와 응답코드
     */
    @Transactional
    @Override
    public ResponseEntity<UserSaveResponseDto> save(UserSaveRequestDto requestDto) {

        User user = new User(requestDto.getEmail(), requestDto.getPassword(),
            requestDto.getUsername(), requestDto.getNickname(), requestDto.getPhone(),
            requestDto.getProfilePicture(), requestDto.getDescription());

        return new ResponseEntity<>(UserSaveResponseDto.toDto(userRepository.save(user)),
            HttpStatus.OK);

    }

    /**
     * 이메일 중복체크
     *
     * @param email 유저가 입력한 이메일
     */
    @Override
    public void findByEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "중복 된 아이디 입니다");
        }
    }


    @Override
    @Transactional
    public void isDeleted(UserDeleteRequsetDto requsetDto, HttpSession session,
        HttpServletRequest request, HttpServletResponse response) {

        Long sessionUserId = (Long) session.getAttribute("user");
        User user = userRepository.findByIdElseThrow(sessionUserId);
        String password = user.getPassword();

        // 미로그인 처리
        if (sessionUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 하지 않았습니다.");
        }

        // 비밀번호 체크
        if (password.equals(requsetDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        user.setDeleted(true);
        userRepository.save(user);

        // 로그아웃 실행
        logoutService.logout(request, response);
    }

    /**
     * @param userId     유저 식별자 ID
     * @param requestDto 클라이언트 요청 정보가 담겨있는 요청 DTO 객체
     */
    @Transactional
    @Override
    public void updatePassword(Long userId, UpdatePasswordRequestDto requestDto) {

        if (requestDto.getNewPassword().equals(requestDto.getOldPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "기존 비밀번호와 새로운 비밀번호가 일치합니다.");
        }

        User user = userRepository.findByIdElseThrow(userId);
        user.updatePassword(requestDto.getNewPassword());

    }


}
