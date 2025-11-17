package com.example.ql_phongmay.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketRequest {
    private String ticketTitle;
    private String ticketDescription;
    private Integer userId;
    private Integer accountId;
    private Integer deviceId;
    private Integer roomId;
    private Integer priorityId;
    private Integer ticketStatusId;
}
