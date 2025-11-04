package com.example.ql_phongmay.repositories;

import com.example.ql_phongmay.entities.Account;
import com.example.ql_phongmay.entities.Booking;
import com.example.ql_phongmay.entities.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByDeletedFalse();
    Page<Booking> findByDeletedFalse(Pageable pageable);
    Optional<Booking> findByBookingIdAndDeletedFalse(Integer id);
    List<Booking> findByBookingStatus(BookingStatus bookingStatus);
}
