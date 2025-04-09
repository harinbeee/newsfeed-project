package com.example.newsfeed.users.service;

import com.example.newsfeed.common.encoder.PasswordEncoder;
import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.common.exception.UserAccessDeniedException;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LogoutService logoutService;

    /**
     * User 프로필 userId 값으로  조회 메소드
     *
     * @param userId 조회 할 유저 아이디
     * @return ResponseEntity 응답데이터와 200 코드
     */
    @Override
    public UserFindResponseDto find(Long userId) {

        User user = userRepository.findByIdElseThrow(userId); // 해당 user id 에 맞는 데이터 있으면 가져오고 없으면 오류

        return UserFindResponseDto.toDto(user); // 데이터 응답 dto 로 변환 후 리턴
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
    public UpdateUserProfileResponseDto update(Long userId, Long loginId,
        UpdateUserProfileRequestDto requestDto) {
        // 로그인한 유저와 수정하려는 유저의 id 비교
        if (!userId.equals(loginId)) {
            throw new UserAccessDeniedException(ExceptionCode.USER_ACCESS_DENIED);
        }

        User findUser = userRepository.findByIdElseThrow(userId);

        // 별명, 전화번호, 프로필사진, 설명은 null 이 될 수 있으므로 선택적으로 데이터 설정
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
    public UserSaveResponseDto save(UserSaveRequestDto requestDto) {
        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(requestDto.getEmail(), encodedPassword,
            requestDto.getUsername(), requestDto.getNickname(), requestDto.getPhone(),
            requestDto.getProfilePicture(), requestDto.getDescription());

        return UserSaveResponseDto.toDto(userRepository.save(user));

    }

    /**
     * 이메일 중복체크
     *
     * @param email 유저가 입력한 이메일
     */
    @Override
    public void findByEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            if (userRepository.findByEmail(email).get().isDeleted()) {
                // 회원가입시 탈퇴된 회원 예외처리
                throw new BusinessException(ExceptionCode.SIGNUP_FORBIDDEN);
            } else {
                // 회원가입시 중복된 회원 예외처리
                throw new BusinessException(ExceptionCode.EMAIL_ALREADY_USED);
            }
        }
    }


    @Override
    @Transactional
    public void isDeleted(UserDeleteRequsetDto requestDto, Long userId,
        HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        Long sessionUserId = (Long) session.getAttribute("user");

        // 로그인한 유저와 삭제하려는 유저의 id 비교
        if (!userId.equals(sessionUserId)) {
            throw new UserAccessDeniedException(ExceptionCode.USER_ACCESS_DENIED);
        }

        User user = userRepository.findByIdElseThrow(sessionUserId);
        String password = user.getPassword();

        // 비밀번호 체크
        if (!passwordEncoder.matches(requestDto.getPassword(), password)) {
            throw new BusinessException(ExceptionCode.PASSWORD_INVALID);
        }

        // 회원 정보에 탈퇴 입력
        user.setDeleted(true);
        userRepository.save(user);

        // 로그아웃 실행
        logoutService.logout(request, response);
    }

    /**
     * 비밀번호 수정 메소드
     *
     * @param userId     유저 식별자 ID
     * @param requestDto 클라이언트 요청 정보가 담겨있는 요청 DTO 객체
     */
    @Transactional
    @Override
    public void updatePassword(Long userId, UpdatePasswordRequestDto requestDto) {

        if (requestDto.getNewPassword().equals(requestDto.getOldPassword())) {
            throw new BusinessException(ExceptionCode.PASSWORD_NOT_CHANGED);
        }

        User user = userRepository.findByIdElseThrow(userId);
        user.updatePassword(requestDto.getNewPassword());

    }


}
