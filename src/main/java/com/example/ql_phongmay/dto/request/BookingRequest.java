package com.example.ql_phongmay.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {
    private Integer userId;
    private Integer roomId;
    private Integer bookingStatusId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String purpose;
}
