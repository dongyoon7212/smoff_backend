package com.smoff.smoff.dto.pledge;

import com.smoff.smoff.entity.Pledge;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddPledgeReqDto {
    private Integer challengerId;
    private String signatureImg;

    public Pledge toEntity() {
        return Pledge.builder()
                .challengerId(challengerId)
                .signatureImg(signatureImg)
                .build();
    }
}
