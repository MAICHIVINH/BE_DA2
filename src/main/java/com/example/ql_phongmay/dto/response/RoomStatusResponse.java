package com.example.ql_phongmay.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomStatusResponse {
    private Integer roomStatusId;
    private String roomStatusName;
    private Boolean isDeleted;
}
