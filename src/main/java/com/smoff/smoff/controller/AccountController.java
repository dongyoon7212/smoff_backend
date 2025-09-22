package com.smoff.smoff.controller;

import com.smoff.smoff.dto.account.ChangePasswordReqDto;
import com.smoff.smoff.dto.account.ChangeProfileImgReqDto;
import com.smoff.smoff.dto.account.ChangeUsernameReqDto;
import com.smoff.smoff.security.model.PrincipalUser;
import com.smoff.smoff.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/change/password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordReqDto changePasswordReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(accountService.changePassword(changePasswordReqDto, principalUser));
    }

    @PostMapping("/change/username")
    public ResponseEntity<?> changeUsername(@RequestBody ChangeUsernameReqDto changeUsernameReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(accountService.changeUsername(changeUsernameReqDto, principalUser));
    }

    @PostMapping("/change/profileimg")
    public ResponseEntity<?> changeProfileImg(@RequestBody ChangeProfileImgReqDto changeProfileImgReqDto, @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(accountService.changeProfileImg(changeProfileImgReqDto, principalUser));
    }

}
