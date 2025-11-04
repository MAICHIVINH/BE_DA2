package com.example.ql_phongmay.controllers;

import com.example.ql_phongmay.dto.request.RoomRequest;
import com.example.ql_phongmay.dto.response.ApiResponse;
import com.example.ql_phongmay.dto.response.RoomResponse;
import com.example.ql_phongmay.services.RoomService;
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
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    // Lấy tất cả phòng
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<RoomResponse>>> getAllRooms() {
        List<RoomResponse> data = roomService.getAllRoom();
        return ResponseEntity.ok(
                ApiResponse.<List<RoomResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get all rooms successfully")
                        .data(data)
                        .build()
        );
    }

    // Lấy phòng có phân trang
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<RoomResponse>>> getPagingRooms(
            @RequestParam int page,
            @RequestParam int size
    ) {
        Page<RoomResponse> data = roomService.getRoomPaging(PageRequest.of(page, size));
        return ResponseEntity.ok(
                ApiResponse.<Page<RoomResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get rooms by page successfully")
                        .data(data)
                        .build()
        );
    }

    // Lấy phòng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoomResponse>> getRoomById(@PathVariable Integer id) {
        RoomResponse data = roomService.getRoomById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));

        return ResponseEntity.ok(
                ApiResponse.<RoomResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get room successfully")
                        .data(data)
                        .build()
        );
    }

    // Tạo mới phòng
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<RoomResponse>> createRoom(@RequestBody RoomRequest roomRequest) {
        RoomResponse data = roomService.createRoom(roomRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<RoomResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Room created successfully")
                        .data(data)
                        .build()
        );
    }

    // Cập nhật phòng
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<RoomResponse>> updateRoom(
            @PathVariable Integer id,
            @RequestBody RoomRequest roomRequest
    ) {
        RoomResponse data = roomService.updateRoom(id, roomRequest);
        return ResponseEntity.ok(
                ApiResponse.<RoomResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Room updated successfully")
                        .data(data)
                        .build()
        );
    }

    // Xóa mềm phòng
    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRoom(@PathVariable Integer id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Room soft deleted successfully")
                        .data(null)
                        .build()
        );
    }

    // Xóa cứng phòng
    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRoomForever(@PathVariable Integer id) {
        roomService.deleteRoomForever(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Room hard deleted successfully")
                        .data(null)
                        .build()
        );
    }

    // Tìm kiếm phòng theo tên
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<RoomResponse>>> searchRooms(@RequestParam String keyword) {
        List<RoomResponse> data = roomService.searchRoomByRoomName(keyword);
        return ResponseEntity.ok(
                ApiResponse.<List<RoomResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Search rooms successfully")
                        .data(data)
                        .build()
        );
    }
}
