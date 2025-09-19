package com.smoff.smoff.dto.account;

import com.smoff.smoff.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordReqDto {
    private Integer userId;
    private String oldPassword;
    private String newPassword;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .password(newPassword)
                .build();
    }
}
