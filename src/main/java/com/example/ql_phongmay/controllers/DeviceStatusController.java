package com.example.ql_phongmay.controllers;

import com.example.ql_phongmay.dto.request.DeviceStatusRequest;
import com.example.ql_phongmay.dto.request.DeviceTypeRequest;
import com.example.ql_phongmay.dto.response.ApiResponse;
import com.example.ql_phongmay.dto.response.DeviceStatusResponse;
import com.example.ql_phongmay.dto.response.DeviceTypeResponse;
import com.example.ql_phongmay.services.DeviceStatusService;
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
@RequestMapping("/api/device-status")
@RequiredArgsConstructor
public class DeviceStatusController {
    private final DeviceStatusService deviceStatusService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<DeviceStatusResponse>>> getAllDeviceStatus() {
        List<DeviceStatusResponse> data = deviceStatusService.getAllDeviceStatus();
        return ResponseEntity.ok(
                ApiResponse.<List<DeviceStatusResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get all device status successfully")
                        .data(data)
                        .build()
        );
    }

    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<DeviceStatusResponse>>> getPagingDeviceType(
            @RequestParam int page,
            @RequestParam int size
    ) {
        Page<DeviceStatusResponse> data = deviceStatusService.getDeviceStatusPaging(PageRequest.of(page, size));
        return ResponseEntity.ok(
                ApiResponse.<Page<DeviceStatusResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get device status by page successfully")
                        .data(data)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DeviceStatusResponse>> getDeviceTypeById(@PathVariable Integer id) {
        DeviceStatusResponse data = deviceStatusService.getDeviceStatusById(id)
                .orElseThrow(() -> new RuntimeException("Device status not found with id: " + id));

        return ResponseEntity.ok(
                ApiResponse.<DeviceStatusResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get device status successfully")
                        .data(data)
                        .build()
        );
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<DeviceStatusResponse>> createDeviceType(@RequestBody DeviceStatusRequest deviceStatusRequest) {
        DeviceStatusResponse data = deviceStatusService.createDeviceStatus(deviceStatusRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<DeviceStatusResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Device status created successfully")
                        .data(data)
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<DeviceStatusResponse>> updateDeviceType(
            @PathVariable Integer id,
            @RequestBody DeviceStatusRequest deviceStatusRequest
    ) {
        DeviceStatusResponse data = deviceStatusService.updateDeviceStatus(id, deviceStatusRequest);
        return ResponseEntity.ok(
                ApiResponse.<DeviceStatusResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Device status updated successfully")
                        .data(data)
                        .build()
        );
    }

    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDeviceType(@PathVariable Integer id) {
        deviceStatusService.deleteDeviceStatus(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Device status soft deleted successfully")
                        .data(null)
                        .build()
        );
    }

    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDeviceTypeForever(@PathVariable Integer id) {
        deviceStatusService.deleteDeviceStatusForever(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Device status hard deleted successfully")
                        .data(null)
                        .build()
        );
    }
}
