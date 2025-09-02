package com.smoff.smoff.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Praise {
    private Integer praiseId;
    private Integer supporterId;
    private Integer patienceLogId;
    private String message;
    private LocalDateTime createDt;
}
