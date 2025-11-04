package com.example.ql_phongmay.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket_statuses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_status_id")
    private Integer ticketStatusId;

    @Column(name = "status_name", nullable = false, unique = true, length = 50)
    private String ticketStatusName;

    @Column(name = "is_deleted")
    private Boolean deleted;
}
