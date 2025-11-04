package com.example.ql_phongmay.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "device_statuses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_status_id")
    private Integer deviceStatusId;

    @Column(name = "status_name")
    private String deviceStatusName;

    @Column(name = "is_deleted")
    private Boolean deleted;
}
