package com.smoff.smoff.service;

import com.smoff.smoff.dto.ApiRespDto;
import com.smoff.smoff.dto.connection.AddConnectionReqDto;
import com.smoff.smoff.entity.Connection;
import com.smoff.smoff.entity.User;
import com.smoff.smoff.repository.ConnectionRepository;
import com.smoff.smoff.repository.UserRepository;
import com.smoff.smoff.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private UserRepository userRepository;

    public ApiRespDto<?> addConnection(AddConnectionReqDto addConnectionReqDto, PrincipalUser principalUser) {
        if (!Objects.equals(addConnectionReqDto.getChallengerId(), principalUser.getUserId()) || !Objects.equals(addConnectionReqDto.getSupporterId(), principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 요청입니다.", null);
        }

        Optional<User> challengerIdOptionalUser = userRepository.getUserByUserId(addConnectionReqDto.getChallengerId());
        Optional<User> supporterIdOptionalUser = userRepository.getUserByUserId(addConnectionReqDto.getSupporterId());

        if (challengerIdOptionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "해당 챌린저가 존재하지 않습니다.", null);
        }
        if (supporterIdOptionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "해당 서포터가 존재하지 않습니다.", null);
        }

        Optional<Connection> optionalConnection = connectionRepository.getConnection(addConnectionReqDto.getChallengerId(), addConnectionReqDto.getSupporterId());

        if (optionalConnection.isPresent()) {
            return new ApiRespDto<>("failed", "이미 연결된 관계입니다.", null);
        }

        int result = connectionRepository.addConnection(addConnectionReqDto.toEntity());

        if (result != 1) {
            return new ApiRespDto<>("failed", "문제가 발생했습니다 다시 시도해주세요.", null);
        }

        return new ApiRespDto<>("success", "성공적으로 연결되었습니다.", null);
    }


}
