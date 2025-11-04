package com.example.ql_phongmay.controllers;

import com.example.ql_phongmay.dto.request.TicketPriorityRequest;
import com.example.ql_phongmay.dto.response.ApiResponse;
import com.example.ql_phongmay.dto.response.TicketPriorityResponse;
import com.example.ql_phongmay.services.TicketPriorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/ticket-priority")
@RequiredArgsConstructor
public class TicketPriorityController {
    private final TicketPriorityService ticketPriorityService;

    // Lấy tất cả trạng thái phòng
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<TicketPriorityResponse>>> getAllTicketPriority() {
        List<TicketPriorityResponse> data = ticketPriorityService.getAllTicketPriority();
        return ResponseEntity.ok(
                ApiResponse.<List<TicketPriorityResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get all ticket priority successfully")
                        .data(data)
                        .build()
        );
    }

    // Lấy trạng thái phòng có phân trang
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<TicketPriorityResponse>>> getPagingTicketPriority(
            @RequestParam int page,
            @RequestParam int size
    ) {
        Page<TicketPriorityResponse> data = ticketPriorityService.getTicketPriorityPaging(PageRequest.of(page, size));
        return ResponseEntity.ok(
                ApiResponse.<Page<TicketPriorityResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get ticket priority by page successfully")
                        .data(data)
                        .build()
        );
    }

    // Lấy trạng thái phòng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TicketPriorityResponse>> getTicketPriorityById(@PathVariable Integer id) {
        TicketPriorityResponse data = ticketPriorityService.getTicketPriorityById(id)
                .orElseThrow(() -> new RuntimeException("Ticket priority not found with id: " + id));

        return ResponseEntity.ok(
                ApiResponse.<TicketPriorityResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get ticket priority successfully")
                        .data(data)
                        .build()
        );
    }

    // Tạo mới trạng thái phòng
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<TicketPriorityResponse>> createTicketPriority(
            @RequestBody TicketPriorityRequest ticketPriorityRequest
            ) {
        TicketPriorityResponse data = ticketPriorityService.createTicketPriority(ticketPriorityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<TicketPriorityResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Ticket priority created successfully")
                        .data(data)
                        .build()
        );
    }

    // Cập nhật trạng thái phòng
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<TicketPriorityResponse>> updateTicketPriority(
            @PathVariable Integer id,
            @RequestBody TicketPriorityRequest ticketPriorityRequest
    ) {
        TicketPriorityResponse data = ticketPriorityService.updateTicketPriority(id, ticketPriorityRequest);
        return ResponseEntity.ok(
                ApiResponse.<TicketPriorityResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Ticket priority updated successfully")
                        .data(data)
                        .build()
        );
    }

    // Xóa mềm trạng thái phòng
    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTicketPriority(@PathVariable Integer id) {
        ticketPriorityService.deleteTicketPriority(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Ticket priority soft deleted successfully")
                        .data(null)
                        .build()
        );
    }

    // Xóa cứng trạng thái phòng
    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTicketPriorityForever(@PathVariable Integer id) {
        ticketPriorityService.deleteTicketPriorityForever(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Ticket priority hard deleted successfully")
                        .data(null)
                        .build()
        );
    }
}
