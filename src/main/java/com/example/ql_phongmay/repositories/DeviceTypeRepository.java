package com.example.ql_phongmay.repositories;

import com.example.ql_phongmay.entities.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, Integer> {
    List<DeviceType> findByDeletedFalse();
}
