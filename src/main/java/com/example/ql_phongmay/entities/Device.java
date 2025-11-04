package com.example.ql_phongmay.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "devices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Integer deviceId;

    @Column(name = "asset_tag", unique = true)
    private String assetTag;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "model")
    private String model;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_type_id", nullable = false)
    private DeviceType deviceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "assigned_machine_slot")
    private String assignedMachineSlot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_status_id")
    private DeviceStatus deviceStatus;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "warranty_end")
    private LocalDate warrantyEnd;

    @Column(name = "notes")
    private String note;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime deviceCreateAt;

    @Column(name = "is_deleted")
    private Boolean deleted;

    @PrePersist
    protected void onCreate() {
        this.deviceCreateAt = LocalDateTime.now();
        if (this.deleted == null) {
            this.deleted = false;
        }
    }
}
