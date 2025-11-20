package com.example.ql_phongmay.services.implement;

import com.example.ql_phongmay.dto.request.DeviceRequest;
import com.example.ql_phongmay.dto.request.TicketRequest;
import com.example.ql_phongmay.dto.response.BookingResponse;
import com.example.ql_phongmay.dto.response.DeviceResponse;
import com.example.ql_phongmay.dto.response.TicketResponse;
import com.example.ql_phongmay.entities.*;
import com.example.ql_phongmay.repositories.*;
import com.example.ql_phongmay.services.TicketService;
import com.example.ql_phongmay.utils.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final CloudinaryService cloudinaryService;
    private final TicketStatusRepository ticketStatusRepository;
    private final TicketPriorityRepository ticketPriorityRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final DeviceRepository deviceRepository;
    private final RoomRepository roomRepository;

    // mapping entity -> response
    private TicketResponse mapToResponse(Ticket ticket) {
        return TicketResponse.builder()
                .ticketId(ticket.getTicketId())
                .ticketTitle(ticket.getTicketTitle())
                .ticketDescription(ticket.getTicketDescription())
                .userFullName(ticket.getUser() != null ? ticket.getUser().getUserFullName() : null)
                .fullName(ticket.getAccount() != null ? ticket.getAccount().getFullName() : null)
                .assetTag(ticket.getDevice() != null ? ticket.getDevice().getAssetTag() : null)
                .roomCode(ticket.getRoom() != null ? ticket.getRoom().getRoomCode() : null)
                .imageUrl(ticket.getImageUrl())
                .priorityName(ticket.getTicketPriority() != null ? ticket.getTicketPriority().getPriorityName() : null)
                .ticketStatusName(ticket.getTicketStatus() != null ? ticket.getTicketStatus().getTicketStatusName() : null)
                .ticketCreateAt(ticket.getTicketCreateAt())
                .ticketUpdatedAt(ticket.getTicketUpdatedAt())
                .isDeleted(ticket.getDeleted())
                .build();
    }

    // mapping request -> entity
    private Ticket mapToEntity(TicketRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));
        Account account = null;
        if (request.getAccountId() != null) {
            account = accountRepository.findById(request.getAccountId())
                    .orElseThrow(() -> new RuntimeException("Account not found with id: " + request.getAccountId()));
        }

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + request.getRoomId()));
        Device device = deviceRepository.findById(request.getDeviceId())
                .orElseThrow(() -> new RuntimeException("Device not found with id: " + request.getDeviceId()));
        TicketPriority ticketPriority = ticketPriorityRepository.findById(request.getPriorityId())
                .orElseThrow(() -> new RuntimeException("TicketPriority not found with id: " + request.getPriorityId()));
        Integer statusId = (request.getTicketStatusId() != null)
                ? request.getTicketStatusId()
                : 1;
        TicketStatus ticketStatus = ticketStatusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("TicketStatus not found with id: " + statusId));

        return Ticket.builder()
                .ticketTitle(request.getTicketTitle())
                .ticketDescription(request.getTicketDescription())
                .user(user)
                .account(account)
                .device(device)
                .room(room)
                .ticketStatus(ticketStatus)
                .ticketPriority(ticketPriority)
                .deleted(false)
                .build();
    }

    @Override
    public List<TicketResponse> getAllTicket() {
        return ticketRepository.findByDeletedFalse()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<TicketResponse> getTicketPaging(Pageable pageable) {
        return ticketRepository.findByDeletedFalse(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public TicketResponse getTicketById(Integer id) {
        Ticket ticket = ticketRepository.findByTicketIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
        return mapToResponse(ticket);
    }

    @Override
    public TicketResponse createTicket(TicketRequest request, MultipartFile file) {
        Ticket ticket = mapToEntity(request);
        if (file != null && !file.isEmpty()) {
            try {
                String imageUrl = cloudinaryService.uploadFile(file);
                ticket.setImageUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Upload image failed: " + e.getMessage(), e);
            }
        }
        return mapToResponse(ticketRepository.save(ticket));
    }

//    @Override
//    public TicketResponse updateTicket(Integer id, TicketRequest request, MultipartFile file) {
//
//        Ticket existing = ticketRepository.findByTicketIdAndDeletedFalse(id)
//                .orElseThrow(() -> new RuntimeException("Device not found with id: " + id));
//        Room room = roomRepository.findById(request.getRoomId())
//                .orElseThrow(() -> new RuntimeException("Room not found with id: " + request.getRoomId()));
//        DeviceStatus deviceStatus = deviceStatusRepository.findById(request.getDeviceStatusId())
//                .orElseThrow(()-> new RuntimeException("Device status not found with id: " + request.getDeviceStatusId()));
//
//        existing.setAssetTag(request.getAssetTag());
//        existing.setSerialNumber(request.getSerialNumber());
//        existing.setModel(request.getModel());
//        existing.setDeviceType(deviceType);
//        existing.setRoom(room);
//        existing.setAssignedMachineSlot(request.getAssignedMachineSlot());
//        existing.setDeviceStatus(deviceStatus);
//        existing.setPurchaseDate(request.getPurchaseDate());
//        existing.setWarrantyEnd(request.getWarrantyEnd());
//        existing.setNote(request.getNote());
//
//        if (file != null && !file.isEmpty()) {
//            try {
//                String imageUrl = cloudinaryService.uploadFile(file);
//                existing.setImageUrl(imageUrl);
//            } catch (IOException e) {
//                throw new RuntimeException("Upload image failed: " + e.getMessage(), e);
//            }
//        }
//
//        return mapToResponse(ticketRepository.save(existing));
//    }

    @Override
    public void deleteTicket(Integer id) {
        Ticket ticket = ticketRepository.findByTicketIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
        ticket.setDeleted(true);
        ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicketForever(Integer id) {
        if (!ticketRepository.existsById(id)) {
            throw new RuntimeException("Ticket not found with id: " + id);
        }
        ticketRepository.deleteById(id);
    }

    @Override
    public Page<TicketResponse> getTicketPagingByUser(Integer userId, Pageable pageable) {
        return ticketRepository.findByUser_UserIdAndDeletedFalse(userId, pageable)
                .map(this::mapToResponse);
    }

}
