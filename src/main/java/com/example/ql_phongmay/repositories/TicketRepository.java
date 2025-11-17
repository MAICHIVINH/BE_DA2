package com.example.ql_phongmay.repositories;

import com.example.ql_phongmay.entities.Account;
import com.example.ql_phongmay.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByDeletedFalse();
    Page<Ticket> findByDeletedFalse(Pageable pageable);
    Optional<Ticket> findByTicketIdAndDeletedFalse(Integer id);
    List<Ticket> findByTicketTitleContainingIgnoreCase(String keyword);
    Optional<Ticket> findByTicketTitleAndDeletedFalse(String accountUserName);
}
