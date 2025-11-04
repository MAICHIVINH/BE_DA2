package com.example.ql_phongmay.services.implement;

import com.example.ql_phongmay.dto.request.TicketStatusRequest;
import com.example.ql_phongmay.dto.response.TicketStatusResponse;
import com.example.ql_phongmay.entities.TicketStatus;
import com.example.ql_phongmay.repositories.TicketStatusRepository;
import com.example.ql_phongmay.services.TicketStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketStatusServiceImpl implements TicketStatusService {
    private final TicketStatusRepository ticketStatusRepository;

    //Entity -> Response
    private TicketStatusResponse mapToResponse(TicketStatus ticketStatus) {
        return TicketStatusResponse.builder()
                .ticketStatusId(ticketStatus.getTicketStatusId())
                .ticketStatusName(ticketStatus.getTicketStatusName())
                .isDeleted(ticketStatus.getDeleted())
                .build();
    }

    //Request -> Entity
    private TicketStatus mapToEntity(TicketStatusRequest request) {
        return TicketStatus.builder()
                .ticketStatusName(request.getTicketStatusName())
                .deleted(false)
                .build();
    }

    @Override
    public List<TicketStatusResponse> getAllTicketStatus() {
        return ticketStatusRepository.findByDeletedFalse()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<TicketStatusResponse> getTicketStatusPaging(Pageable pageable) {
        return ticketStatusRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public Optional<TicketStatusResponse> getTicketStatusById(Integer id) {
        return ticketStatusRepository.findById(id)
                .map(this::mapToResponse);
    }

    @Override
    public TicketStatusResponse createTicketStatus(TicketStatusRequest ticketStatusRequest) {
        TicketStatus ticketStatus = mapToEntity(ticketStatusRequest);
        return mapToResponse(ticketStatusRepository.save(ticketStatus));
    }

    @Override
    public TicketStatusResponse updateTicketStatus(Integer id, TicketStatusRequest ticketStatusRequest) {
        TicketStatus existing = ticketStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket Status not found with id: " + id));

        //cập nhật từng field
        existing.setTicketStatusName(ticketStatusRequest.getTicketStatusName());
        return mapToResponse(ticketStatusRepository.save(existing));
    }

    @Override
    public void deleteTicketStatus(Integer id) {
        TicketStatus ticketStatus = ticketStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket Status not found"));

        ticketStatus.setDeleted(true); // đánh dấu là đã xóa
        ticketStatusRepository.save(ticketStatus);
    }

    @Override
    public void deleteTicketStatusForever(Integer id) {
        if(!ticketStatusRepository.existsById(id)) {
            throw new RuntimeException("Ticket Status not found with id: " + id);
        }
        ticketStatusRepository.deleteById(id);
    }
}
