package com.smoff.smoff.controller;

import com.smoff.smoff.dto.PatienceLog.AddPatienceLogReqDto;
import com.smoff.smoff.security.model.PrincipalUser;
import com.smoff.smoff.service.PatienceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patience")
public class PatienceLogController {

    @Autowired
    private PatienceLogService patienceLogService;

    @PostMapping("/add")
    public ResponseEntity<?> addPatienceLog(@RequestBody AddPatienceLogReqDto addPatienceLogReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(patienceLogService.addPatienceLog(addPatienceLogReqDto, principalUser));
    }

    @GetMapping("/log")
    public ResponseEntity<?> getPatienceLog(@RequestParam Integer patienceLogId, @RequestParam Integer challengerId, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(patienceLogService.getPatienceLog(patienceLogId, challengerId, principalUser));
    }

    @GetMapping("/list/{challengerId}")
    public ResponseEntity<?> getPatienceLogList(@PathVariable Integer challengerId, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(patienceLogService.getPatienceLogList(challengerId, principalUser));
    }
}
