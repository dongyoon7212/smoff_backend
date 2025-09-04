package com.smoff.smoff.controller;

import com.smoff.smoff.dto.connection.AddConnectionReqDto;
import com.smoff.smoff.dto.connection.RemoveConnectionReqDto;
import com.smoff.smoff.dto.connection.UpdateConnectionReqDto;
import com.smoff.smoff.security.model.PrincipalUser;
import com.smoff.smoff.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/connection")
public class ConnectionController {

    @Autowired
    private ConnectionService connectionService;

    @PostMapping("/add")
    public ResponseEntity<?> addConnection(@RequestBody AddConnectionReqDto addConnectionReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(connectionService.addConnection(addConnectionReqDto, principalUser));
    }

    @GetMapping("/supporters/{challengerId}")
    public ResponseEntity<?> getConnectionListByChallengerId(@PathVariable Integer challengerId, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(connectionService.getConnectionListByChallengerId(challengerId, principalUser));
    }

    @GetMapping("/challengers/{supporterId}")
    public ResponseEntity<?> getConnectionListBySupporterId(@PathVariable Integer supporterId, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(connectionService.getConnectionListBySupporterId(supporterId, principalUser));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateConnection(@RequestBody UpdateConnectionReqDto updateConnectionReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(connectionService.updateConnection(updateConnectionReqDto, principalUser));
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeConnection(@RequestBody RemoveConnectionReqDto removeConnectionReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(connectionService.removeConnection(removeConnectionReqDto, principalUser));
    }
}
