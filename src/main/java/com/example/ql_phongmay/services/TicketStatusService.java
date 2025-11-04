package com.example.ql_phongmay.services;

import com.example.ql_phongmay.dto.request.TicketStatusRequest;
import com.example.ql_phongmay.dto.response.TicketStatusResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TicketStatusService {
    List<TicketStatusResponse> getAllTicketStatus();
    Page<TicketStatusResponse> getTicketStatusPaging(Pageable pageable);
    Optional<TicketStatusResponse> getTicketStatusById(Integer id);
    TicketStatusResponse createTicketStatus(TicketStatusRequest ticketStatusRequest);
    TicketStatusResponse updateTicketStatus(Integer id, TicketStatusRequest ticketStatusRequest);
    void deleteTicketStatus(Integer id);
    void deleteTicketStatusForever(Integer id);
}
