package com.smoff.smoff.service;

import com.smoff.smoff.dto.ApiRespDto;
import com.smoff.smoff.dto.account.ChangePasswordReqDto;
import com.smoff.smoff.dto.account.ChangeUsernameReqDto;
import com.smoff.smoff.entity.User;
import com.smoff.smoff.repository.UserRepository;
import com.smoff.smoff.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApiRespDto<?> changePassword(ChangePasswordReqDto changePasswordReqDto, PrincipalUser principalUser) {
        if (!Objects.equals(changePasswordReqDto.getUserId(), principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 요청입니다.", null);
        }

        Optional<User> userIdOptionalUser = userRepository.getUserByUserId(changePasswordReqDto.getUserId());

        if (userIdOptionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "회원 정보가 존재하지 않습니다.", null);
        }

        User user = userIdOptionalUser.get();

        if (!bCryptPasswordEncoder.matches(changePasswordReqDto.getPassword(), user.getPassword())) {
            return new ApiRespDto<>("failed", "아이디 또는 비밀번호가 일치하지 않습니다.", null);
        }

        int result = userRepository.changePassword(changePasswordReqDto.toEntity());

        if (result != 1) {
            return new ApiRespDto<>("failed", "문제가 발생했습니다 다시 시도해주세요.", null);
        }

        return new ApiRespDto<>("success", "비밀번호가 변경되었습니다.\n다시 로그인 해주세요.", null);
    }

    public ApiRespDto<?> changeUsername(ChangeUsernameReqDto changeUsernameReqDto, PrincipalUser principalUser) {
        if (!Objects.equals(changeUsernameReqDto.getUserId(), principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 요청입니다.", null);
        }

        Optional<User> userIdOptionalUser = userRepository.getUserByUserId(changeUsernameReqDto.getUserId());

        if (userIdOptionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "회원 정보가 존재하지 않습니다.", null);
        }

        int result = userRepository.changeUsername(changeUsernameReqDto.toEntity());

        if (result != 1) {
            return new ApiRespDto<>("failed", "문제가 발생했습니다 다시 시도해주세요.", null);
        }

        return new ApiRespDto<>("success", "사용자 이름이 변경되었습니다.", null);
    }
}
