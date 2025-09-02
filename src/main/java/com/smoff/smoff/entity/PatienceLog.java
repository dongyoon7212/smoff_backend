package com.smoff.smoff.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatienceLog {
    private Integer PatienceLogId;
    private Integer challengerId;
    private Integer amountSave;
    private LocalDateTime createDt;
}
