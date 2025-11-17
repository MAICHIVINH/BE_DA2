package com.example.ql_phongmay.services.implement;

import com.example.ql_phongmay.dto.request.TicketRequest;
import com.example.ql_phongmay.dto.response.TicketResponse;
import com.example.ql_phongmay.entities.*;
import com.example.ql_phongmay.repositories.*;
import com.example.ql_phongmay.services.TicketService;
import com.example.ql_phongmay.utils.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    private final TicketImageRepository ticketImageRepository;

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
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + request.getAccountId()));
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + request.getRoomId()));
        Device device = deviceRepository.findById(request.getDeviceId())
                .orElseThrow(() -> new RuntimeException("Device not found with id: " + request.getDeviceId()));
        TicketPriority ticketPriority = ticketPriorityRepository.findById(request.getPriorityId())
                .orElseThrow(() -> new RuntimeException("TicketPriority not found with id: " + request.getPriorityId()));
        TicketStatus ticketStatus = ticketStatusRepository.findById(request.getTicketStatusId())
                .orElseThrow(() -> new RuntimeException("TicketStatus not found with id: " + request.getTicketStatusId()));

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
    public TicketResponse createTicket(TicketRequest request, List<MultipartFile> images) throws Exception {

        // 1. Map request -> entity
        Ticket ticket = mapToEntity(request);

        // 2. Lưu ticket trước
        ticket = ticketRepository.save(ticket);

        // 3. Nếu có ảnh thì upload
        if (images != null && !images.isEmpty()) {

            boolean isFirst = true;

            for (MultipartFile file : images) {

                // Upload lên Cloudinary
                String url = cloudinaryService.uploadFile(file);

                // Tạo TicketImage
                TicketImage ticketImage = TicketImage.builder()
                        .ticket(ticket)
                        .imageUrl(url)
                        .main(isFirst) // ảnh đầu tiên là ảnh chính
                        .deleted(false)
                        .build();

                ticketImageRepository.save(ticketImage);

                isFirst = false; // chỉ ảnh đầu tiên là ảnh chính
            }
        }

        return mapToResponse(ticket);
    }

}
