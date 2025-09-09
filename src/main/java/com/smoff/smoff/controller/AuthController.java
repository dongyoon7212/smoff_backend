package com.smoff.smoff.controller;

import com.smoff.smoff.dto.auth.SigninReqDto;
import com.smoff.smoff.dto.auth.SignupReqDto;
import com.smoff.smoff.dto.auth.SoftDeleteUserReqDto;
import com.smoff.smoff.security.model.PrincipalUser;
import com.smoff.smoff.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/principal")
    public ResponseEntity<?> getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
        return ResponseEntity.ok(principalUser);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupReqDto signupReqDto) {
        return ResponseEntity.ok(authService.signup(signupReqDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninReqDto signinReqDto) {
        return ResponseEntity.ok(authService.signin(signinReqDto));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> softDeleteUser(@RequestBody SoftDeleteUserReqDto softDeleteUserReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(authService.softDeleteUser(softDeleteUserReqDto, principalUser));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(authService.getUserByEmail(email));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(authService.getUserByUsername(username));
    }

    @GetMapping("/role/{email}")
    public ResponseEntity<?> getCheckRoleByEmail(@PathVariable String email) {
        return ResponseEntity.ok(authService.getCheckRoleByEmail(email));
    }
}
