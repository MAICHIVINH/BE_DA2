package com.example.ql_phongmay.repositories;

import com.example.ql_phongmay.entities.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomStatusRepository extends JpaRepository<RoomStatus, Integer> {
    List<RoomStatus> findByDeletedFalse();
}
