package com.smoff.smoff.repository;

import com.smoff.smoff.entity.Pledge;
import com.smoff.smoff.mapper.PledgeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PledgeRepository {

    @Autowired
    private PledgeMapper pledgeMapper;

    public int addPledge(Pledge pledge) {
        return pledgeMapper.addPledge(pledge);
    }

    public Optional<Pledge> getPledgeByChallengerId(Integer challengerId) {
        return pledgeMapper.getPledgeByChallengerId(challengerId);
    }
}
