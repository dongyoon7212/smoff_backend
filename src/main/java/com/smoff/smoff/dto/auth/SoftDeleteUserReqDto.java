package com.smoff.smoff.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SoftDeleteUserReqDto {
    private Integer userId;
    private String password;
}
