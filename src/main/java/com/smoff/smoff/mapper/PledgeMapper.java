package com.smoff.smoff.mapper;

import com.smoff.smoff.entity.Pledge;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface PledgeMapper {
    int addPledge(Pledge pledge);
    Optional<Pledge> getPledgeByChallengerId(Integer challengerId);
}
