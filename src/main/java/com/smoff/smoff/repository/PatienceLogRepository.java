package com.smoff.smoff.repository;

import com.smoff.smoff.entity.PatienceLog;
import com.smoff.smoff.mapper.PatienceLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PatienceLogRepository {

    @Autowired
    private PatienceLogMapper patienceLogMapper;

    public int addPatienceLog(Integer challengerId) {
        return patienceLogMapper.addPatienceLog(challengerId);
    }

    public Optional<PatienceLog> getPatienceLog(Integer patienceLogId, Integer challengerId) {
        return patienceLogMapper.getPatienceLog(patienceLogId, challengerId);
    }

    public List<PatienceLog> getPatienceLogList(Integer challengerId) {
        return patienceLogMapper.getPatienceLogList(challengerId);
    }
}
