package com.smoff.smoff.repository;

import com.smoff.smoff.entity.Connection;
import com.smoff.smoff.mapper.ConnectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ConnectionRepository {

    @Autowired
    private ConnectionMapper connectionMapper;

    public int addConnection(Connection connection) {
        return connectionMapper.addConnection(connection);
    }

    public Optional<Connection> getConnection(Integer challengerId, Integer supporterId) {
        return connectionMapper.getConnection(challengerId, supporterId);
    }

    public List<Connection> getConnectionListByChallengerId (Integer challengerId) {
        return connectionMapper.getConnectionListByChallengerId(challengerId);
    }

    public List<Connection> getConnectionListBySupporterId(Integer supporterId) {
        return connectionMapper.getConnectionListBySupporterId(supporterId);
    }

    public int updateConnection(Connection connection) {
        return connectionMapper.updateConnection(connection);
    }

    public int removeConnection(Integer challengerId, Integer supporterId) {
        return connectionMapper.removeConnection(challengerId, supporterId);
    }
}
