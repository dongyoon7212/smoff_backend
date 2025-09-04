package com.smoff.smoff.dto.connection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoveConnectionReqDto {
    private Integer challengerId;
    private Integer supporterId;
}
