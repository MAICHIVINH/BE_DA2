package com.example.ql_phongmay.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponse {
    private Integer roomId;
    private String roomCode;
    private String roomName;
    private String roomLocation;
    private Integer roomCapacity;
    private String roomDescription;
    private String roomStatusName;
    private Boolean isDeleted;
}
