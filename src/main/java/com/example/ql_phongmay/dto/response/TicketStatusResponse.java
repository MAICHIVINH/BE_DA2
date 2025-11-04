package com.example.ql_phongmay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketStatusResponse {
    private Integer ticketStatusId;
    private String ticketStatusName;
    private Boolean isDeleted;
}
