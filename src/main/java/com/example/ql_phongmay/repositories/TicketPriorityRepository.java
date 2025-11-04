package com.example.ql_phongmay.repositories;

import com.example.ql_phongmay.entities.TicketPriority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketPriorityRepository extends JpaRepository<TicketPriority, Integer> {
    List<TicketPriority> findByDeletedFalse();
}
