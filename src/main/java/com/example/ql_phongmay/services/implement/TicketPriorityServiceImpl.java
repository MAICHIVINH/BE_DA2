package com.example.ql_phongmay.services.implement;

import com.example.ql_phongmay.dto.request.TicketPriorityRequest;
import com.example.ql_phongmay.dto.response.TicketPriorityResponse;
import com.example.ql_phongmay.entities.TicketPriority;
import com.example.ql_phongmay.repositories.TicketPriorityRepository;
import com.example.ql_phongmay.services.TicketPriorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketPriorityServiceImpl implements TicketPriorityService {
    private final TicketPriorityRepository ticketPriorityRepository;

    //Entity -> Response
    private TicketPriorityResponse mapToResponse(TicketPriority ticketPriority) {
        return TicketPriorityResponse.builder()
                .priorityId(ticketPriority.getPriorityId())
                .priorityName(ticketPriority.getPriorityName())
                .isDeleted(ticketPriority.getDeleted())
                .build();
    }

    //Request -> Entity
    private TicketPriority mapToEntity(TicketPriorityRequest request) {
        return TicketPriority.builder()
                .priorityName(request.getPriorityName())
                .deleted(false)
                .build();
    }

    @Override
    public List<TicketPriorityResponse> getAllTicketPriority() {
        return ticketPriorityRepository.findByDeletedFalse()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<TicketPriorityResponse> getTicketPriorityPaging(Pageable pageable) {
        return ticketPriorityRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public Optional<TicketPriorityResponse> getTicketPriorityById(Integer id) {
        return ticketPriorityRepository.findById(id)
                .map(this::mapToResponse);
    }

    @Override
    public TicketPriorityResponse createTicketPriority(TicketPriorityRequest ticketPriorityRequest) {
        TicketPriority ticketPriority = mapToEntity(ticketPriorityRequest);
        return mapToResponse(ticketPriorityRepository.save(ticketPriority));
    }

    @Override
    public TicketPriorityResponse updateTicketPriority(Integer id, TicketPriorityRequest ticketPriorityRequest) {
        TicketPriority existing = ticketPriorityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket Priority not found with id: " + id));

        //cập nhật từng field
        existing.setPriorityName(ticketPriorityRequest.getPriorityName());
        return mapToResponse(ticketPriorityRepository.save(existing));
    }

    @Override
    public void deleteTicketPriority(Integer id) {
        TicketPriority ticketPriority = ticketPriorityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket Priority not found"));

        ticketPriority.setDeleted(true); // đánh dấu là đã xóa
        ticketPriorityRepository.save(ticketPriority);
    }

    @Override
    public void deleteTicketPriorityForever(Integer id) {
        if(!ticketPriorityRepository.existsById(id)) {
            throw new RuntimeException("Ticket Priority not found with id: " + id);
        }
        ticketPriorityRepository.deleteById(id);
    }
}
