package com.example.ql_phongmay.repositories;

import com.example.ql_phongmay.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}
