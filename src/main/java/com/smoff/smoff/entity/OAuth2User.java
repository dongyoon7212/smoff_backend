package com.smoff.smoff.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2User {
    private Integer oAuth2UserId;
    private Integer userId;
    private String provider;
    private String providerUserId;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;
}
