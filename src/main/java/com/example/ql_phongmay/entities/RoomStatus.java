package com.example.ql_phongmay.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "room_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_status_id")
    private Integer roomStatusId;

    @Column(name = "room_status_name", nullable = false, unique = true, length = 50)
    private String roomStatusName;

    @Column(name = "is_deleted")
    private Boolean deleted;
}
