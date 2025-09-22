package com.smoff.smoff.dto.account;

import com.smoff.smoff.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeProfileImgReqDto {
    private Integer userId;
    private String profileImg;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .profileImg(profileImg)
                .build();
    }
}
