package com.smoff.smoff.service;

import com.smoff.smoff.dto.ApiRespDto;
import com.smoff.smoff.dto.auth.SigninReqDto;
import com.smoff.smoff.dto.auth.SignupReqDto;
import com.smoff.smoff.dto.auth.SoftDeleteUserReqDto;
import com.smoff.smoff.entity.User;
import com.smoff.smoff.entity.UserRole;
import com.smoff.smoff.repository.UserRepository;
import com.smoff.smoff.repository.UserRoleRepository;
import com.smoff.smoff.security.jwt.JwtUtils;
import com.smoff.smoff.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> signup(SignupReqDto signupReqDto) {
        //이메일 중복확인
        Optional<User> emailOptionalUser = userRepository.getUserByEmail(signupReqDto.getEmail());
        if (emailOptionalUser.isPresent()) {
            return new ApiRespDto<>("failed", "이미 존재하는 이메일 입니다.", null);
        }

        //사용자이름 중복확인
        Optional<User> usernameOptionalUser = userRepository.getUserByUsername(signupReqDto.getUsername());
        if (usernameOptionalUser.isPresent()) {
            return new ApiRespDto<>("failed", "이미 존재하는 사용자 이름 입니다.", null);
        }

        try {
            // 사용자 정보 추가
            Optional<User> optionalUser = userRepository.addUser(signupReqDto.toEntity(bCryptPasswordEncoder));

            if (optionalUser.isEmpty()) {
                throw new RuntimeException("회원 정보 추가에 실패했습니다.");
            }

            User user = optionalUser.get();

            // 사용자 역할(Role) 추가
            UserRole userRole = UserRole.builder()
                    .userId(user.getUserId())
                    .roleId(3)
                    .build();

            int addUserRoleResult = userRoleRepository.addUserRole(userRole);
            if (addUserRoleResult != 1) {
                throw new RuntimeException("사용자 역할 추가에 실패했습니다.");
            }

            return new ApiRespDto<>("success", "이메일 인증이 완료되면 회원가입이 완료됩니다.", user);

        } catch (Exception e) {
            return new ApiRespDto<>("failed", "회원가입 중 오류가 발생했습니다: " + e.getMessage(), null);
        }
    }

    public ApiRespDto<?> signin(SigninReqDto signinReqDto) {
        Optional<User> emailOptionalUser = userRepository.getUserByEmail(signinReqDto.getEmail());
        if (emailOptionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "아이디 또는 비밀번호가 일치하지 않습니다.", null);
        }

        User user = emailOptionalUser.get();

        LocalDateTime deleteDt = user.getDeleteDt();

        if (deleteDt != null) {
            return new ApiRespDto<>("failed", "탈퇴 예정인 계정입니다. 복구하시겠습니까?", null);
        }

        if (!bCryptPasswordEncoder.matches(signinReqDto.getPassword(), user.getPassword())) {
            return new ApiRespDto<>("failed", "아이디 또는 비밀번호가 일치하지 않습니다.", null);
        }

        String accessToken = jwtUtils.generateAccessToken(user.getUserId().toString());
        return new ApiRespDto<>("success", "로그인이 성공적으로 완료되었습니다.", accessToken);
    }

    public ApiRespDto<?> softDeleteUser(SoftDeleteUserReqDto softDeleteUserReqDto, PrincipalUser principalUser) {
        if (!Objects.equals(softDeleteUserReqDto.getUserId(), principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 요청입니다.", null);
        }

        Optional<User> userIdOptionalUser = userRepository.getUserByUserId(softDeleteUserReqDto.getUserId());
        if (userIdOptionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "회원 정보가 존재하지 않습니다.", null);
        }

        User user = userIdOptionalUser.get();

        LocalDateTime deleteDt = user.getDeleteDt();

        if (deleteDt != null) {
            return new ApiRespDto<>("failed", "이미 탈퇴 예정인 계정입니다.", null);
        }

        if (!bCryptPasswordEncoder.matches(softDeleteUserReqDto.getPassword(), user.getPassword())) {
            return new ApiRespDto<>("failed", "아이디 또는 비밀번호가 일치하지 않습니다.", null);
        }

        int result = userRepository.softDeleteUser(user.getUserId());

        if (result != 1) {
            return new ApiRespDto<>("failed", "문제가 발생했습니다 다시 시도해주세요.", null);
        }

        return new ApiRespDto<>("success", "탈퇴 처리가 완료되었습니다. 30일 뒤 영구삭제 될 예정입니다.", null);
    }

    public ApiRespDto<?> getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.getUserByEmail(email);

        if (optionalUser.isPresent()) {
            return new ApiRespDto<>("failed", "해당 이메일로 이미 가입 되어있습니다.", null);
        }

        return new ApiRespDto<>("success", "사용 가능한 이메일 입니다.", null);
    }

    public ApiRespDto<?> getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.getUserByUsername(username);

        if (optionalUser.isPresent()) {
            return new ApiRespDto<>("failed", "이미 사용중인 사용자 이름 입니다.", null);
        }

        return new ApiRespDto<>("success", "사용 가능한 사용자 이름 입니다.", null);
    }
}




















