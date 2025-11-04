package com.example.ql_phongmay.controllers;

import com.example.ql_phongmay.dto.request.RoomStatusRequest;
import com.example.ql_phongmay.dto.response.ApiResponse;
import com.example.ql_phongmay.dto.response.RoomStatusResponse;
import com.example.ql_phongmay.services.RoomStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
@RequestMapping("/api/room-status")
@RequiredArgsConstructor
public class RoomStatusController {
    private final RoomStatusService roomStatusService;

    // Lấy tất cả trạng thái phòng
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<RoomStatusResponse>>> getAllRoomStatus() {
        List<RoomStatusResponse> data = roomStatusService.getAllRoomStatus();
        return ResponseEntity.ok(
                ApiResponse.<List<RoomStatusResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get all room status successfully")
                        .data(data)
                        .build()
        );
    }

    // Lấy trạng thái phòng có phân trang
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<RoomStatusResponse>>> getPagingRoomStatus(
            @RequestParam int page,
            @RequestParam int size
    ) {
        Page<RoomStatusResponse> data = roomStatusService.getRoomStatusPaging(PageRequest.of(page, size));
        return ResponseEntity.ok(
                ApiResponse.<Page<RoomStatusResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get room status by page successfully")
                        .data(data)
                        .build()
        );
    }

    // Lấy trạng thái phòng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoomStatusResponse>> getRoomStatusById(@PathVariable Integer id) {
        RoomStatusResponse data = roomStatusService.getRoomStatusById(id)
                .orElseThrow(() -> new RuntimeException("Room status not found with id: " + id));

        return ResponseEntity.ok(
                ApiResponse.<RoomStatusResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get room status successfully")
                        .data(data)
                        .build()
        );
    }

    // Tạo mới trạng thái phòng
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<RoomStatusResponse>> createRoomStatus(
            @RequestBody RoomStatusRequest roomStatusRequest
    ) {
        RoomStatusResponse data = roomStatusService.createRoomStatus(roomStatusRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<RoomStatusResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Room status created successfully")
                        .data(data)
                        .build()
        );
    }

    // Cập nhật trạng thái phòng
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<RoomStatusResponse>> updateRoomStatus(
            @PathVariable Integer id,
            @RequestBody RoomStatusRequest roomStatusRequest
    ) {
        RoomStatusResponse data = roomStatusService.updateRoomStatus(id, roomStatusRequest);
        return ResponseEntity.ok(
                ApiResponse.<RoomStatusResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Room status updated successfully")
                        .data(data)
                        .build()
        );
    }

    // Xóa mềm trạng thái phòng
    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRoomStatus(@PathVariable Integer id) {
        roomStatusService.deleteRoomStatus(id);
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
    public ResponseEntity<ApiResponse<Void>> deleteRoomStatusForever(@PathVariable Integer id) {
        roomStatusService.deleteRoomStatusForever(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Room status hard deleted successfully")
                        .data(null)
                        .build()
        );
    }

//    // Tìm kiếm trạng thái phòng theo tên
//    @GetMapping("/search")
//    public ResponseEntity<ApiResponse<List<RoomStatusResponse>>> searchRoomStatus(@RequestParam String keyword) {
//        List<RoomStatusResponse> data = roomStatusService.searchRoomStatusByName(keyword);
//        return ResponseEntity.ok(
//                ApiResponse.<List<RoomStatusResponse>>builder()
//                        .status(HttpStatus.OK.value())
//                        .message("Search room status successfully")
//                        .data(data)
//                        .build()
//        );
//    }
}
