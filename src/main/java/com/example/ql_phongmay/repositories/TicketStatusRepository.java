package com.example.ql_phongmay.repositories;

import com.example.ql_phongmay.entities.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketStatusRepository extends JpaRepository<TicketStatus, Integer> {
    List<TicketStatus> findByDeletedFalse();
}
