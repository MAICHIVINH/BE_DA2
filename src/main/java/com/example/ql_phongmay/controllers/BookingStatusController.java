package com.example.ql_phongmay.controllers;

import com.example.ql_phongmay.dto.request.BookingStatusRequest;
import com.example.ql_phongmay.dto.request.RoomStatusRequest;
import com.example.ql_phongmay.dto.response.ApiResponse;
import com.example.ql_phongmay.dto.response.BookingStatusResponse;
import com.example.ql_phongmay.dto.response.RoomStatusResponse;
import com.example.ql_phongmay.services.BookingStatusService;
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
@RequestMapping("/api/booking-status")
@RequiredArgsConstructor
public class BookingStatusController {
    private final BookingStatusService bookingStatusService;

    // Lấy tất cả trạng thái phòng
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<BookingStatusResponse>>> getAllBookingtatus() {
        List<BookingStatusResponse> data = bookingStatusService.getAllBookingStatus();
        return ResponseEntity.ok(
                ApiResponse.<List<BookingStatusResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get all booking status successfully")
                        .data(data)
                        .build()
        );
    }

    // Lấy trạng thái phòng có phân trang
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<BookingStatusResponse>>> getPagingBookingStatus(
            @RequestParam int page,
            @RequestParam int size
    ) {
        Page<BookingStatusResponse> data = bookingStatusService.getBookingStatusPaging(PageRequest.of(page, size));
        return ResponseEntity.ok(
                ApiResponse.<Page<BookingStatusResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get booking status by page successfully")
                        .data(data)
                        .build()
        );
    }

    // Lấy trạng thái phòng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingStatusResponse>> getBookingStatusById(@PathVariable Integer id) {
        BookingStatusResponse data = bookingStatusService.getBookingStatusById(id)
                .orElseThrow(() -> new RuntimeException("Booking status not found with id: " + id));

        return ResponseEntity.ok(
                ApiResponse.<BookingStatusResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get booking status successfully")
                        .data(data)
                        .build()
        );
    }

    // Tạo mới trạng thái phòng
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<BookingStatusResponse>> createBookingStatus(
            @RequestBody BookingStatusRequest bookingStatusRequest
    ) {
        BookingStatusResponse data = bookingStatusService.createBookingStatus(bookingStatusRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<BookingStatusResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Booking status created successfully")
                        .data(data)
                        .build()
        );
    }

    // Cập nhật trạng thái phòng
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<BookingStatusResponse>> updateBookingStatus(
            @PathVariable Integer id,
            @RequestBody BookingStatusRequest bookingStatusRequest
    ) {
        BookingStatusResponse data = bookingStatusService.updateBookingStatus(id, bookingStatusRequest);
        return ResponseEntity.ok(
                ApiResponse.<BookingStatusResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Booking status updated successfully")
                        .data(data)
                        .build()
        );
    }

    // Xóa mềm trạng thái phòng
    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBookingStatus(@PathVariable Integer id) {
        bookingStatusService.deleteBookingStatus(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Room status soft deleted successfully")
                        .data(null)
                        .build()
        );
    }

    // Xóa cứng trạng thái phòng
    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBookingStatusForever(@PathVariable Integer id) {
        bookingStatusService.deleteBookingStatusForever(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Room status hard deleted successfully")
                        .data(null)
                        .build()
        );
    }
}
