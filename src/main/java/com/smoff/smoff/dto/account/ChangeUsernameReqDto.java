package com.smoff.smoff.dto.account;

import com.smoff.smoff.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeUsernameReqDto {
    private Integer userId;
    private String username;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .username(username)
                .build();
    }
}
