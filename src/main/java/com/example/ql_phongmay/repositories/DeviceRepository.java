package com.example.ql_phongmay.repositories;

import com.example.ql_phongmay.entities.Booking;
import com.example.ql_phongmay.entities.BookingStatus;
import com.example.ql_phongmay.entities.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
    List<Device> findByDeletedFalse();
    Page<Device> findByDeletedFalse(Pageable pageable);
    Optional<Device> findByDeviceIdAndDeletedFalse(Integer id);
}
