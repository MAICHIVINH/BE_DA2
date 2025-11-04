package com.example.ql_phongmay.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private Integer bookingId;
    private String userName;
    private String roomName;
    private String bookingStatusName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String purpose;
    private LocalDateTime bookingCreateAt;
    private Boolean isDeleted;
}
