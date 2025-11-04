package com.example.ql_phongmay.controllers;

import com.example.ql_phongmay.dto.request.DeviceTypeRequest;
import com.example.ql_phongmay.dto.request.RoleRequest;
import com.example.ql_phongmay.dto.response.ApiResponse;
import com.example.ql_phongmay.dto.response.DeviceTypeResponse;
import com.example.ql_phongmay.dto.response.RoleResponse;
import com.example.ql_phongmay.services.DeviceTypeService;
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
@RequestMapping("/api/device-type")
@RequiredArgsConstructor
public class DeviceTypeController {
    private final DeviceTypeService deviceTypeService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<DeviceTypeResponse>>> getAllDeviceType() {
        List<DeviceTypeResponse> data = deviceTypeService.getAllDeviceType();
        return ResponseEntity.ok(
                ApiResponse.<List<DeviceTypeResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get all device types successfully")
                        .data(data)
                        .build()
        );
    }

    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<DeviceTypeResponse>>> getPagingDeviceType(
            @RequestParam int page,
            @RequestParam int size
    ) {
        Page<DeviceTypeResponse> data = deviceTypeService.getDeviceTypePaging(PageRequest.of(page, size));
        return ResponseEntity.ok(
                ApiResponse.<Page<DeviceTypeResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get device types by page successfully")
                        .data(data)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DeviceTypeResponse>> getDeviceTypeById(@PathVariable Integer id) {
        DeviceTypeResponse data = deviceTypeService.getDeviceTypeById(id)
                .orElseThrow(() -> new RuntimeException("DeviceType not found with id: " + id));

        return ResponseEntity.ok(
                ApiResponse.<DeviceTypeResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get device type successfully")
                        .data(data)
                        .build()
        );
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse<DeviceTypeResponse>> createDeviceType(@RequestBody DeviceTypeRequest deviceTypeRequest) {
        DeviceTypeResponse data = deviceTypeService.createDeviceType(deviceTypeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<DeviceTypeResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("DeviceType created successfully")
                        .data(data)
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<DeviceTypeResponse>> updateDeviceType(
            @PathVariable Integer id,
            @RequestBody DeviceTypeRequest deviceTypeRequest
    ) {
        DeviceTypeResponse data = deviceTypeService.updateDeviceType(id, deviceTypeRequest);
        return ResponseEntity.ok(
                ApiResponse.<DeviceTypeResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("DeviceType updated successfully")
                        .data(data)
                        .build()
        );
    }

    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDeviceType(@PathVariable Integer id) {
        deviceTypeService.deleteDeviceType(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("DeviceType soft deleted successfully")
                        .data(null)
                        .build()
        );
    }

    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDeviceTypeForever(@PathVariable Integer id) {
        deviceTypeService.deleteDeviceTypeForever(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("DeviceType hard deleted successfully")
                        .data(null)
                        .build()
        );
    }
}
