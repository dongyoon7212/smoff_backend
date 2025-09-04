package com.smoff.smoff.service;

import com.smoff.smoff.dto.ApiRespDto;
import com.smoff.smoff.dto.pledge.AddPledgeReqDto;
import com.smoff.smoff.entity.Pledge;
import com.smoff.smoff.entity.User;
import com.smoff.smoff.repository.PledgeRepository;
import com.smoff.smoff.repository.UserRepository;
import com.smoff.smoff.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PledgeService {

    @Autowired
    private PledgeRepository pledgeRepository;

    @Autowired
    private UserRepository userRepository;

    public ApiRespDto<?> addPledge(AddPledgeReqDto addPledgeReqDto, PrincipalUser principalUser) {
        if (!Objects.equals(addPledgeReqDto.getChallengerId(), principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 요청입니다.", null);
        }

        Optional<User> userIdOptionalUser = userRepository.getUserByUserId(addPledgeReqDto.getChallengerId());

        if (userIdOptionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "회원 정보가 존재하지 않습니다.", null);
        }

        Optional<Pledge> pledgeOptional = pledgeRepository.getPledgeByChallengerId(addPledgeReqDto.getChallengerId());

        if (pledgeOptional.isPresent()) {
            return new ApiRespDto<>("failed", "이미 서약서가 존재합니다.", null);
        }

        int result = pledgeRepository.addPledge(addPledgeReqDto.toEntity());

        if (result != 1) {
            return new ApiRespDto<>("failed", "문제가 발생했습니다 다시 시도해주세요.", null);
        }

        return new ApiRespDto<>("success", "서약서가 저장되었습니다.", null);
    }

    public ApiRespDto<?> getPledgeByChallengerId(Integer challengerId, PrincipalUser principalUser) {
        if (!Objects.equals(challengerId, principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 요청입니다.", null);
        }

        Optional<User> userIdOptionalUser = userRepository.getUserByUserId(challengerId);

        if (userIdOptionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "회원 정보가 존재하지 않습니다.", null);
        }

        Optional<Pledge> optionalPledge = pledgeRepository.getPledgeByChallengerId(challengerId);

        if (optionalPledge.isEmpty()) {
            return new ApiRespDto<>("failed", "서약서가 존재하지 않습니다.", null);
        }

        return new ApiRespDto<>("success", "서약서를 조회하였습니다.", null);
    }
}
