package com.smoff.smoff.service;

import com.smoff.smoff.dto.ApiRespDto;
import com.smoff.smoff.dto.connection.AddConnectionReqDto;
import com.smoff.smoff.dto.connection.RemoveConnectionReqDto;
import com.smoff.smoff.dto.connection.UpdateConnectionReqDto;
import com.smoff.smoff.entity.Connection;
import com.smoff.smoff.entity.User;
import com.smoff.smoff.repository.ConnectionRepository;
import com.smoff.smoff.repository.UserRepository;
import com.smoff.smoff.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public ApiRespDto<?> getConnectionListByChallengerId(Integer challengerId, PrincipalUser principalUser) {
        if (!Objects.equals(challengerId, principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 요청입니다.", null);
        }

        Optional<User> userIdOptionalUser = userRepository.getUserByUserId(challengerId);

        if (userIdOptionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "회원 정보가 존재하지 않습니다.", null);
        }

        List<Connection> connectionList = connectionRepository.getConnectionListByChallengerId(challengerId);

        return new ApiRespDto<>("success", "해당 챌린저를 응원하는 서포터를 조회하였습니다.", connectionList);
    }

    public ApiRespDto<?> getConnectionListBySupporterId(Integer supporterId, PrincipalUser principalUser) {
        if (!Objects.equals(supporterId, principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 요청입니다.", null);
        }

        Optional<User> userIdOptionalUser = userRepository.getUserByUserId(supporterId);

        if (userIdOptionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "회원 정보가 존재하지 않습니다.", null);
        }

        List<Connection> connectionList = connectionRepository.getConnectionListBySupporterId(supporterId);

        return new ApiRespDto<>("success", "해당 서포터가 응원하는 챌린저를 조회하였습니다.", connectionList);
    }

    public ApiRespDto<?> updateConnection(UpdateConnectionReqDto updateConnectionReqDto, PrincipalUser principalUser) {
        if (!Objects.equals(updateConnectionReqDto.getChallengerId(), principalUser.getUserId()) || !Objects.equals(updateConnectionReqDto.getSupporterId(), principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 요청입니다.", null);
        }

        Optional<User> challengerIdOptionalUser = userRepository.getUserByUserId(updateConnectionReqDto.getChallengerId());
        Optional<User> supporterIdOptionalUser = userRepository.getUserByUserId(updateConnectionReqDto.getSupporterId());

        if (challengerIdOptionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "해당 챌린저가 존재하지 않습니다.", null);
        }
        if (supporterIdOptionalUser.isEmpty()) {
            return new ApiRespDto<>("failed", "해당 서포터가 존재하지 않습니다.", null);
        }

        Optional<Connection> optionalConnection = connectionRepository.getConnection(updateConnectionReqDto.getChallengerId(), updateConnectionReqDto.getSupporterId());

        if (optionalConnection.isEmpty()) {
            return new ApiRespDto<>("failed", "연결된 관계가 아닙니다.", null);
        }

        int result = connectionRepository.updateConnection(updateConnectionReqDto.toEntity());

        if (result != 1) {
            return new ApiRespDto<>("failed", "문제가 발생했습니다 다시 시도해주세요.", null);
        }

        return new ApiRespDto<>("success", "성공적으로 수정되었습니다.", null);
    }

    public ApiRespDto<?> removeConnection(RemoveConnectionReqDto removeConnectionReqDto, PrincipalUser principalUser) {
        if (!Objects.equals(removeConnectionReqDto.getChallengerId(), principalUser.getUserId()) || !Objects.equals(removeConnectionReqDto.getSupporterId(), principalUser.getUserId())) {
            return new ApiRespDto<>("failed", "잘못된 요청입니다.", null);
        }

        Optional<Connection> optionalConnection = connectionRepository.getConnection(removeConnectionReqDto.getChallengerId(), removeConnectionReqDto.getSupporterId());

        if (optionalConnection.isEmpty()) {
            return new ApiRespDto<>("failed", "연결된 관계가 아닙니다.", null);
        }

        int result = connectionRepository.removeConnection(removeConnectionReqDto.getChallengerId(), removeConnectionReqDto.getSupporterId());

        if (result != 1) {
            return new ApiRespDto<>("failed", "문제가 발생했습니다 다시 시도해주세요.", null);
        }

        return new ApiRespDto<>("success", "성공적으로 삭제되었습니다.", null);
    }

}
