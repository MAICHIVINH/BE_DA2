package com.example.ql_phongmay.controllers;

import com.example.ql_phongmay.dto.request.DeviceRequest;
import com.example.ql_phongmay.dto.response.*;
import com.example.ql_phongmay.services.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/device")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;
    private final ObjectMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<DeviceResponse>>> getAllDevice() {
        return ResponseEntity.ok(
                ApiResponse.<List<DeviceResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Lấy danh sách thiết bị thành công")
                        .data(deviceService.getAllDevice())
                        .build()
        );
    }

    // Phân trang device
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<DeviceResponse>>> getDevicePaging(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(
                ApiResponse.<Page<DeviceResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Lấy danh sách phân trang thành công")
                        .data(deviceService.getDevicePaging(PageRequest.of(page, size)))
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DeviceResponse>> getDeviceById(@PathVariable Integer id) {
        DeviceResponse data = deviceService.getDeviceById(id);

        return ResponseEntity.ok(
                ApiResponse.<DeviceResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get device successfully")
                        .data(data)
                        .build()
        );
    }

    // Tạo mới trạng thái phòng
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<DeviceResponse>> createDevice(
            @RequestPart("device") String deviceJson,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); //thêm dòng này
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); //tránh lỗi định dạng ngày

            DeviceRequest request = mapper.readValue(deviceJson, DeviceRequest.class);

            DeviceResponse saved = deviceService.createDevice(request, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.<DeviceResponse>builder()
                            .status(HttpStatus.CREATED.value())
                            .message("Thêm thiết bị thành công")
                            .data(saved)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiResponse.<DeviceResponse>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Lỗi khi thêm thiết bị: " + e.getMessage())
                            .data(null)
                            .build()
            );
        }
    }

    // Cập nhật thông tin thiết bị
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<DeviceResponse>> updateDevice(
            @PathVariable Integer id,
            @RequestPart("device") String deviceJson,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); //thêm dòng này
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); //tránh lỗi định dạng ngày

            DeviceRequest request = mapper.readValue(deviceJson, DeviceRequest.class);

            DeviceResponse updated = deviceService.updateDevice(id, request, file);
            return ResponseEntity.ok(
                    ApiResponse.<DeviceResponse>builder()
                            .status(HttpStatus.OK.value())
                            .message("Cập nhật thông tin thiết bị thành công")
                            .data(updated)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiResponse.<DeviceResponse>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("Lỗi khi cập nhật thông tin thiết bị: " + e.getMessage())
                            .data(null)
                            .build()
            );
        }
    }

    // Xóa mềm
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDevice(@PathVariable Integer id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Đã xóa thành công booking (soft delete)")
                        .data(null)
                        .build()
        );
    }

    // Xóa khỏi database
    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDeviceForever(@PathVariable Integer id) {
        deviceService.deleteDeviceForever(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Đã xóa vĩnh viễn booking")
                        .data(null)
                        .build()
        );
    }

}
