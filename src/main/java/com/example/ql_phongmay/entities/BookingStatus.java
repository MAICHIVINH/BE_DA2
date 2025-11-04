package com.example.ql_phongmay.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booking_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_status_id")
    private Integer bookingStatusId;

    @Column(name = "status_name", nullable = false, unique = true, length = 50)
    private String bookingStatusName;

    @Column(name = "is_deleted")
    private Boolean deleted;
}
