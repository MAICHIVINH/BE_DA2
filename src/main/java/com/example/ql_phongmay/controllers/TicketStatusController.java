package com.example.ql_phongmay.controllers;

import com.example.ql_phongmay.dto.request.TicketStatusRequest;
import com.example.ql_phongmay.dto.response.ApiResponse;
import com.example.ql_phongmay.dto.response.TicketStatusResponse;
import com.example.ql_phongmay.services.TicketStatusService;
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
@RequestMapping("/api/ticket-status")
@RequiredArgsConstructor
public class TicketStatusController {
    private final TicketStatusService ticketStatusService;

    // Lấy tất cả trạng thái phòng
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<TicketStatusResponse>>> getAllTicketStatus() {
        List<TicketStatusResponse> data = ticketStatusService.getAllTicketStatus();
        return ResponseEntity.ok(
                ApiResponse.<List<TicketStatusResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get all ticket status successfully")
                        .data(data)
                        .build()
        );
    }

    // Lấy trạng thái phòng có phân trang
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<TicketStatusResponse>>> getPagingTicketStatus(
            @RequestParam int page,
            @RequestParam int size
    ) {
        Page<TicketStatusResponse> data = ticketStatusService.getTicketStatusPaging(PageRequest.of(page, size));
        return ResponseEntity.ok(
                ApiResponse.<Page<TicketStatusResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get ticket status by page successfully")
                        .data(data)
                        .build()
        );
    }

    // Lấy trạng thái phòng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TicketStatusResponse>> getTicketStatusById(@PathVariable Integer id) {
        TicketStatusResponse data = ticketStatusService.getTicketStatusById(id)
                .orElseThrow(() -> new RuntimeException("Ticket status not found with id: " + id));

        return ResponseEntity.ok(
                ApiResponse.<TicketStatusResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get ticket status successfully")
                        .data(data)
                        .build()
        );
    }

    // Tạo mới trạng thái phòng
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<TicketStatusResponse>> createTicketStatus(
            @RequestBody TicketStatusRequest ticketStatusRequest
            ) {
        TicketStatusResponse data = ticketStatusService.createTicketStatus(ticketStatusRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<TicketStatusResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Ticket status created successfully")
                        .data(data)
                        .build()
        );
    }

    // Cập nhật trạng thái phòng
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<TicketStatusResponse>> updateTicketStatus(
            @PathVariable Integer id,
            @RequestBody TicketStatusRequest ticketStatusRequest
    ) {
        TicketStatusResponse data = ticketStatusService.updateTicketStatus(id, ticketStatusRequest);
        return ResponseEntity.ok(
                ApiResponse.<TicketStatusResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Ticket status updated successfully")
                        .data(data)
                        .build()
        );
    }

    // Xóa mềm trạng thái phòng
    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTicketStatus(@PathVariable Integer id) {
        ticketStatusService.deleteTicketStatus(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Ticket status soft deleted successfully")
                        .data(null)
                        .build()
        );
    }

    // Xóa cứng trạng thái phòng
    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTicketStatusForever(@PathVariable Integer id) {
        ticketStatusService.deleteTicketStatusForever(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Ticket status hard deleted successfully")
                        .data(null)
                        .build()
        );
    }
}
