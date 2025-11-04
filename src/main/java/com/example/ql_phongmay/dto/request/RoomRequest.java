package com.example.ql_phongmay.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomRequest {
    private String roomCode;
    private String roomName;
    private String roomLocation;
    private Integer roomCapacity;
    private String roomDescription;
    private Integer roomStatusId;
}
