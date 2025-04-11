package com.example.newsfeed.users.service;

import com.example.newsfeed.auth.service.AuthService;
import com.example.newsfeed.boards.repository.BoardRepository;
import com.example.newsfeed.boards.repository.CommentRepository;
import com.example.newsfeed.common.exception.BusinessException;
import com.example.newsfeed.common.exception.ExceptionCode;
import com.example.newsfeed.common.util.PasswordEncoder;
import com.example.newsfeed.friends.repository.FriendRepository;
import com.example.newsfeed.friends.repository.FriendRequestRepository;
import com.example.newsfeed.likes.repository.LikeRepository;
import com.example.newsfeed.users.dto.UpdatePasswordRequestDto;
import com.example.newsfeed.users.dto.UpdateUserProfileRequestDto;
import com.example.newsfeed.users.dto.UpdateUserProfileResponseDto;
import com.example.newsfeed.users.dto.UserDeleteRequsetDto;
import com.example.newsfeed.users.dto.UserFindResponseDto;
import com.example.newsfeed.users.entity.User;
import com.example.newsfeed.users.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final BoardRepository boardRepository;
    private final FriendRepository friendRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

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
    public UpdateUserProfileResponseDto update(Long userId,
        UpdateUserProfileRequestDto requestDto) {

        User findUser = userRepository.findByIdElseThrow(userId);

        // 별명, 전화번호, 프로필사진, 설명은 null 이 될 수 있으므로 선택적으로 데이터 설정
        Optional.ofNullable(requestDto.getNickname()).ifPresent(findUser::setNickname);
        Optional.ofNullable(requestDto.getPhone()).ifPresent(findUser::setPhone);
        Optional.ofNullable(requestDto.getProfilePicture()).ifPresent(findUser::setProfilePicture);
        Optional.ofNullable(requestDto.getDescription()).ifPresent(findUser::setDescription);

        return UpdateUserProfileResponseDto.toDto(findUser);

    }

    @Override
    @Transactional
    public void isDeleted(UserDeleteRequsetDto requestDto, Long userId, HttpServletRequest request,
        HttpServletResponse response) {

        User user = userRepository.findByIdElseThrow(userId);
        String password = user.getPassword();

        // 비밀번호 체크
        if (!passwordEncoder.matches(requestDto.getPassword(), password)) {
            throw new BusinessException(ExceptionCode.PASSWORD_INVALID);
        }

        // comment 에서 댓글 있으면  탈퇴회원의 댓글을 숨김
        commentRepository.findByUserId(userId).ifPresent(comments -> {
            comments.forEach(comment ->
                likeRepository.deleteLikeByBoardBoardIdAndCommentCommentId(
                    comment.getBoard().getBoardId(),
                    comment.getCommentId()
                )
            );
            commentRepository.deleteCommentByUserId(userId);
        });

        // board 에서 게시글 있으면 탈퇴회원의 게시판 숨김
        boardRepository.findByUserId(userId).ifPresent(boards -> {
            boards.forEach(board -> likeRepository.deleteLikeByBoardBoardId(board.getBoardId()));
            boardRepository.deleteBoardByUserId(userId);
        });

        // friends 에서 친구 있으면 탈퇴회원의 친구 숨김
        friendRepository.findByFromUserId(userId)
            .ifPresent(friend -> friendRepository.deleteFriendByUserId(userId));

        // 친구 요청 삭제
        friendRequestRepository.findByFromUserId(userId)
            .ifPresent(fr -> friendRequestRepository.deleteFriendByUserId(userId));

        // 회원 정보에 탈퇴 입력 @SQLDelete 기능사용(user entity)
        userRepository.delete(user);

        // 로그아웃 실행
        authService.logout(request, response);
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

        User user = userRepository.findByIdElseThrow(userId);

        // 비밀번호 체크
        if (!passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword())) {
            throw new BusinessException(ExceptionCode.PASSWORD_INVALID);
        }

        // 기존 비밀번호와 새로운 비밀번호 똑같은지 비교
        if (requestDto.getNewPassword().equals(requestDto.getOldPassword())) {
            throw new BusinessException(ExceptionCode.PASSWORD_NOT_CHANGED);
        }

        user.updatePassword(requestDto.getNewPassword());

    }


}
