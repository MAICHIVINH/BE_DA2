package com.example.ql_phongmay.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceRequest {
    private String assetTag;
    private String serialNumber;
    private String model;
    private Integer deviceTypeId;
    private Integer roomId;
    private String assignedMachineSlot;
    private Integer deviceStatusId;
    private LocalDate purchaseDate;
    private LocalDate warrantyEnd;
    private String note;
}
