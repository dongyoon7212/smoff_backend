package com.smoff.smoff.mapper;

import com.smoff.smoff.entity.PatienceLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PatienceLogMapper {
    int addPatienceLog(Integer challengerId);
    Optional<PatienceLog> getPatienceLog(Integer patienceLogId, Integer challengerId);
    List<PatienceLog> getPatienceLogList(Integer challengerId);
}
