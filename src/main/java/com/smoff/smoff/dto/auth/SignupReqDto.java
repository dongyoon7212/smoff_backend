package com.smoff.smoff.dto.auth;

import com.smoff.smoff.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor
public class SignupReqDto {
    private String email;
    private String password;
    private String username;
    private Integer age;
    private String roleType;
    private String profileImg;

    public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return User.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .username(username)
                .age(age)
                .roleType(roleType)
                .profileImg(profileImg)
                .build();
    }
}
