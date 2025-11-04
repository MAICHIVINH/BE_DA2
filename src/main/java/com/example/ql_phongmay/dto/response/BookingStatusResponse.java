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
public class BookingStatusResponse {
    private Integer bookingStatusId;
    private String bookingStatusName;
    private Boolean isDeleted;
}
