package com.example.ql_phongmay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceStatusResponse {
    private Integer deviceStatusId;
    private String deviceStatusName;
    private Boolean isDeleted;
}
