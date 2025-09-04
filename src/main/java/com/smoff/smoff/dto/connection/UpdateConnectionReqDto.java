package com.smoff.smoff.dto.connection;

import com.smoff.smoff.entity.Connection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateConnectionReqDto {
    private Integer challengerId;
    private Integer supporterId;
    private String status;

    public Connection toEntity() {
        return Connection.builder()
                .challengerId(challengerId)
                .supporterId(supporterId)
                .status(status)
                .build();
    }
}
