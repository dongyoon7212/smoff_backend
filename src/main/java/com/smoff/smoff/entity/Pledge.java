package com.smoff.smoff.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pledge {
    private Integer pledgeId;
    private Integer challengerId;
    private String signatureImg;
    private LocalDateTime createDt;
}
