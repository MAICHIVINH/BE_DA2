package com.example.ql_phongmay.services;

import com.example.ql_phongmay.dto.request.TicketPriorityRequest;
import com.example.ql_phongmay.dto.response.TicketPriorityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TicketPriorityService {
    List<TicketPriorityResponse> getAllTicketPriority();
    Page<TicketPriorityResponse> getTicketPriorityPaging(Pageable pageable);
    Optional<TicketPriorityResponse> getTicketPriorityById(Integer id);
    TicketPriorityResponse createTicketPriority(TicketPriorityRequest ticketPriorityRequest);
    TicketPriorityResponse updateTicketPriority(Integer id, TicketPriorityRequest ticketPriorityRequest);
    void deleteTicketPriority(Integer id);
    void deleteTicketPriorityForever(Integer id);
}
