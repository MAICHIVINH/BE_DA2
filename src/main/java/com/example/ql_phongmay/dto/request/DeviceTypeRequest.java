package com.example.ql_phongmay.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceTypeRequest {
    private String deviceTypeName;
    private String deviceTypeDescription;
}
