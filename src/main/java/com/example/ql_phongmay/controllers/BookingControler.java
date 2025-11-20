package com.example.ql_phongmay.controllers;

import com.example.ql_phongmay.dto.request.BookingRequest;
import com.example.ql_phongmay.dto.response.ApiResponse;
import com.example.ql_phongmay.dto.response.BookingResponse;
import com.example.ql_phongmay.services.BookingService;
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
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingControler {
    private final BookingService bookingService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getAllBooking() {
        return ResponseEntity.ok(
                ApiResponse.<List<BookingResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Lấy danh sách đặt phòng thành công")
                        .data(bookingService.getAllBooking())
                        .build()
        );
    }

    // Phân trang account
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<BookingResponse>>> getBookingPaging(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(
                ApiResponse.<Page<BookingResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Lấy danh sách phân trang thành công")
                        .data(bookingService.getBookingPaging(PageRequest.of(page, size)))
                        .build()
        );
    }

    // Tạo mới trạng thái phòng
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(
            @RequestBody BookingRequest bookingRequest
    ) {
        BookingResponse data = bookingService.createBooking(bookingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<BookingResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Booking created successfully")
                        .data(data)
                        .build()
        );
    }

    // Cập nhật trạng thái phòng
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<BookingResponse>> updateBooking(
            @PathVariable Integer id,
            @RequestBody BookingRequest bookingRequest
    ) {
        BookingResponse data = bookingService.updateBooking(id, bookingRequest);
        return ResponseEntity.ok(
                ApiResponse.<BookingResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Booking updated successfully")
                        .data(data)
                        .build()
        );
    }

    // Xóa mềm
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBooking(@PathVariable Integer id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Booking has been successfully soft deleted")
                        .data(null)
                        .build()
        );
    }

    // Xóa khỏi database
    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBookingForever(@PathVariable Integer id) {
        bookingService.deleteBookingForever(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Booking has been permanently deleted")
                        .data(null)
                        .build()
        );
    }

    // Admin duyệt đặt phòng
    @PutMapping("/approve/{id}")
    public ResponseEntity<ApiResponse<BookingResponse>> approveBooking(@PathVariable Integer id) {
        BookingResponse data = bookingService.approveBooking(id);
        return ResponseEntity.ok(
                ApiResponse.<BookingResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Booking has been successfully approved")
                        .data(data)
                        .build()
        );
    }

    // Người dùng hủy đặt phòng
    @PutMapping("/cancel/{id}")
    public ResponseEntity<ApiResponse<BookingResponse>> cancelBooking(@PathVariable Integer id) {
        BookingResponse data = bookingService.cancelBooking(id);
        return ResponseEntity.ok(
                ApiResponse.<BookingResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Booking has been successfully canceled")
                        .data(data)
                        .build()
        );
    }

    @GetMapping("/page-by-user")
    public ResponseEntity<ApiResponse<Page<BookingResponse>>> getBookingPagingByUser(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam Integer userId
    ) {
        return ResponseEntity.ok(
                ApiResponse.<Page<BookingResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Lấy danh sách booking phân trang theo user thành công")
                        .data(bookingService.getBookingPagingByUser(
                                userId, PageRequest.of(page, size)))
                        .build()
        );
    }
}
