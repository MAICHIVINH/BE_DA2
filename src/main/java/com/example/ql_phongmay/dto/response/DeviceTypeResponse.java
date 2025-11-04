package com.example.ql_phongmay.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceTypeResponse {
    private Integer deviceTypeId;
    private String deviceTypeName;
    private String deviceTypeDescription;
    private Boolean isDeleted;
}
