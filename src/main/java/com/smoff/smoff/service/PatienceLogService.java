package com.smoff.smoff.service;

import com.smoff.smoff.dto.ApiRespDto;
import com.smoff.smoff.dto.PatienceLog.AddPatienceLogReqDto;
import com.smoff.smoff.entity.PatienceLog;
import com.smoff.smoff.entity.User;
import com.smoff.smoff.repository.PatienceLogRepository;
import com.smoff.smoff.repository.UserRepository;
import com.smoff.smoff.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PatienceLogService {

    @Autowired
    private PatienceLogRepository patienceLogRepository;

    @Autowired
    private UserRepository userRepository;

    public ApiRespDto<?> addPatienceLog(AddPatienceLogReqDto addPatienceLogReqDto, PrincipalUser principalUser) {
        if (!Objects.equals(addPatienceLogReqDto.getChallengerId(), principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 요청입니다.", null);
        }

        Optional<User> userIdOptionalUser = userRepository.getUserByUserId(addPatienceLogReqDto.getChallengerId());

        if (userIdOptionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "회원 정보가 존재하지 않습니다.", null);
        }

        int result = patienceLogRepository.addPatienceLog(addPatienceLogReqDto.getChallengerId());

        if (result != 1) {
            return new ApiRespDto<>("failed", "문제가 발생했습니다 다시 시도해주세요.", null);
        }

        return new ApiRespDto<>("success", "참기 기록이 추가되었습니다.", null);
    }

    public ApiRespDto<?> getPatienceLog(Integer patienceLogId, Integer challengerId, PrincipalUser principalUser) {
        if (!Objects.equals(challengerId, principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 요청입니다.", null);
        }

        Optional<User> userIdOptionalUser = userRepository.getUserByUserId(challengerId);

        if (userIdOptionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "회원 정보가 존재하지 않습니다.", null);
        }

        Optional<PatienceLog> optionalPatienceLog = patienceLogRepository.getPatienceLog(patienceLogId, challengerId);

        if (optionalPatienceLog.isEmpty()) {
            return new ApiRespDto<>("failed", "해당 기록이 존재하지 않습니다.", null);
        }

        return new ApiRespDto<>("success", "해당 기록을 조회하였습니다.", optionalPatienceLog.get());
    }

    public ApiRespDto<?> getPatienceLogList(Integer challengerId, PrincipalUser principalUser) {
        if (!Objects.equals(challengerId, principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 요청입니다.", null);
        }

        Optional<User> userIdOptionalUser = userRepository.getUserByUserId(challengerId);

        if (userIdOptionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "회원 정보가 존재하지 않습니다.", null);
        }

        List<PatienceLog> patienceLogList = patienceLogRepository.getPatienceLogList(challengerId);

        return new ApiRespDto<>("success", "해당 챌린저의 기록을 조회하였습니다.",  patienceLogList);
    }
}
