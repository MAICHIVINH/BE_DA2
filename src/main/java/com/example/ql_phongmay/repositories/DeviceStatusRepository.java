package com.example.ql_phongmay.repositories;

import com.example.ql_phongmay.entities.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Integer> {
    List<DeviceStatus> findByDeletedFalse();
}
