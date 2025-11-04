package com.example.ql_phongmay.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ticket_priorities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketPriority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priority_id")
    private Integer priorityId;

    @Column(name = "priority_name", unique = true)
    private String priorityName;

    @Column(name = "is_deleted")
    private Boolean deleted;
}
