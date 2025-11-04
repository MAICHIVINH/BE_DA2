package com.example.ql_phongmay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceResponse {
    private Integer deviceId;
    private String assetTag;
    private String serialNumber;
    private String model;
    private String deviceTypeName;
    private String imageUrl;
    private String roomName;
    private String assignedMachineSlot;
    private String deviceStatusName;
    private LocalDate purchaseDate;
    private LocalDate warrantyEnd;
    private String note;
    private LocalDateTime deviceCreateAt;
    private Boolean isDeleted;
}
