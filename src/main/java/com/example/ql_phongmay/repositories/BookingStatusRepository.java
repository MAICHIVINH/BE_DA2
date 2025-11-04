package com.example.ql_phongmay.repositories;

import com.example.ql_phongmay.entities.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingStatusRepository extends JpaRepository<BookingStatus, Integer> {
    List<BookingStatus> findByDeletedFalse();
}
