package com.example.ql_phongmay.services;

import com.example.ql_phongmay.dto.request.TicketRequest;
import com.example.ql_phongmay.dto.response.TicketResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TicketService {
//    List<TicketResponse> getAllTicket();
//    Page<TicketResponse> getTicketPaging(Pageable pageable);
//    TicketResponse getTicketById(Integer id);
      TicketResponse createTicket(TicketRequest request, MultipartFile file);

//    TicketResponse updateTicket(Integer id, TicketRequest request, MultipartFile file);
//    void deleteTicket(Integer id);          // xóa mềm
//    void deleteTicketForever(Integer id);   // xóa cứng
//    List<TicketResponse> searchTicketByTitle(String keyword);
}
