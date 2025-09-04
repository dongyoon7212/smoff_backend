package com.smoff.smoff.controller;

import com.smoff.smoff.dto.pledge.AddPledgeReqDto;
import com.smoff.smoff.security.model.PrincipalUser;
import com.smoff.smoff.service.PledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pledge")
public class PledgeController {

    @Autowired
    private PledgeService pledgeService;

    @PostMapping("/add")
    public ResponseEntity<?> addPledge(@RequestBody AddPledgeReqDto addPledgeReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(pledgeService.addPledge(addPledgeReqDto, principalUser));
    }

    @GetMapping("/{challengeId}")
    public ResponseEntity<?> getPledgeByChallengerId(@PathVariable Integer challengerId, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(pledgeService.getPledgeByChallengerId(challengerId, principalUser));
    }
}
