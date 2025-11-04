package com.example.ql_phongmay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {
    private Integer ticketId;
    private String ticketTitle;
    private String ticketDescription;
    private String userFullName;
    private String fullName;
    private String assetTag;
    private String roomCode;
    private String priorityName;
    private String ticketStatusName;
    private LocalDateTime ticketCreateAt;
    private LocalDateTime ticketUpdatedAt;
    private Boolean isDeleted;
}
