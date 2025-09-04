package com.smoff.smoff.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Connection {
    private Integer connectionId;
    private Integer challengerId;
    private Integer supporterId;
    private String status;
    private LocalDateTime createDt;
}
