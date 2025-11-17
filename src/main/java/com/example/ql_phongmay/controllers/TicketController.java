package com.example.ql_phongmay.controllers;

import com.example.ql_phongmay.dto.request.TicketRequest;
import com.example.ql_phongmay.dto.response.TicketResponse;
import com.example.ql_phongmay.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(
        origins = {
                "http://localhost:5173",
                "http://localhost:5174",
                "http://localhost:5175",
                "http://localhost:5176",
                "http://localhost:5177",
                "http://localhost:5178",
                "http://localhost:5179"
        },
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = "*"
)
@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

//    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<TicketResponse> createTicket(
//            @RequestPart("ticket") TicketRequest request,
//            @RequestPart(value = "images", required = false) List<MultipartFile> images
//    ) throws Exception {
//
//        TicketResponse response = ticketService.createTicket(request, images);
//        return ResponseEntity.ok(response);
//    }
@PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
public ResponseEntity<TicketResponse> createTicket(
        @RequestPart("ticket") TicketRequest request,
        @RequestPart(value = "images", required = false) List<MultipartFile> images
) throws Exception {
    return ResponseEntity.ok(ticketService.createTicket(request, images));
}


}
