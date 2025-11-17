package com.example.ql_phongmay.repositories;

import com.example.ql_phongmay.entities.Ticket;
import com.example.ql_phongmay.entities.TicketImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketImageRepository extends JpaRepository<TicketImage, Integer> {
    List<TicketImage> findByTicketTicketId(Integer ticketId);
    List<TicketImage> findByDeletedFalse();
    void deleteByTicket_TicketId(Integer ticketId);
    List<TicketImage> findByTicketAndMain(Ticket ticket, boolean isMain);
}
