package com.example.ql_phongmay.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "room_code", unique = true)
    private String roomCode;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "room_location")
    private String roomLocation;

    @Column(name = "capacity")
    private Integer roomCapacity;

    @Column(name = "description")
    private String roomDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_status_id", nullable = false)
    private RoomStatus roomStatus;

    @Column(name = "is_deleted")
    private Boolean deleted;
}
