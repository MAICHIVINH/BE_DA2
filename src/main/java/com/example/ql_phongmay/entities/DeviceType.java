package com.example.ql_phongmay.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "device_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_type_id ")
    private Integer deviceTypeId ;

    @Column(name = "device_type_name")
    private String deviceTypeName ;

    @Column(name = "description")
    private String deviceTypeDescription;

    @Column(name = "is_deleted")
    private Boolean deleted;
}
