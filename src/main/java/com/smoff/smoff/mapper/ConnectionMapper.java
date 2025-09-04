package com.smoff.smoff.mapper;

import com.smoff.smoff.entity.Connection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ConnectionMapper {
    int addConnection(Connection connection);
    Optional<Connection> getConnection(Integer challengerId, Integer supporterId);
    List<Connection> getConnectionListByChallengerId(Integer challengerId);
    List<Connection> getConnectionListBySupporterId(Integer supporterId);
    int updateConnection(Connection connection);
    int removeConnection(Integer challengerId, Integer supporterId);
}
