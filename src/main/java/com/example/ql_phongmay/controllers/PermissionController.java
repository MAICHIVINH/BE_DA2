package com.example.ql_phongmay.controllers;

import com.example.ql_phongmay.dto.request.PermissionRequest;
import com.example.ql_phongmay.dto.response.ApiResponse;
import com.example.ql_phongmay.dto.response.PermissionResponse;
import com.example.ql_phongmay.services.PermissionService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/permission")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> getAllPermissions() {
        List<PermissionResponse> data = permissionService.getAllPermissions();
        return ResponseEntity.ok(
                ApiResponse.<List<PermissionResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get all permissions successfully")
                        .data(data)
                        .build()
        );
    }

//    @GetMapping("/page")
//    public ResponseEntity<ApiResponse<Page<PermissionResponse>>> getPagingPermissions(
//            @RequestParam int page,
//            @RequestParam int size
//    ) {
//        Page<PermissionResponse> data = permissionService.getPermissionPaging(PageRequest.of(page, size));
//        return ResponseEntity.ok(
//                ApiResponse.<Page<PermissionResponse>>builder()
//                        .status(HttpStatus.OK.value())
//                        .message("Get permissions by page successfully")
//                        .data(data)
//                        .build()
//        );
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ApiResponse<PermissionResponse>> getPermissionById(@PathVariable Integer id) {
//        PermissionResponse data = permissionService.getPermissionById(id)
//                .orElseThrow(() -> new RuntimeException("Permission not found with id: " + id));
//
//        return ResponseEntity.ok(
//                ApiResponse.<PermissionResponse>builder()
//                        .status(HttpStatus.OK.value())
//                        .message("Get permission successfully")
//                        .data(data)
//                        .build()
//        );
//    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<PermissionResponse>> createPermission(@RequestBody PermissionRequest request) {
        PermissionResponse data = permissionService.createPermission(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<PermissionResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Permission created successfully")
                        .data(data)
                        .build()
        );
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<ApiResponse<PermissionResponse>> updatePermission(
//            @PathVariable Integer id,
//            @RequestBody PermissionRequest request
//    ) {
//        PermissionResponse data = permissionService.updatePermission(id, request);
//        return ResponseEntity.ok(
//                ApiResponse.<PermissionResponse>builder()
//                        .status(HttpStatus.OK.value())
//                        .message("Permission updated successfully")
//                        .data(data)
//                        .build()
//        );
//    }

    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePermission(@PathVariable Integer id) {
        permissionService.deletePermission(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Permission soft deleted successfully")
                        .data(null)
                        .build()
        );
    }

    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePermissionForever(@PathVariable Integer id) {
        permissionService.deletePermissionForever(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Permission hard deleted successfully")
                        .data(null)
                        .build()
        );
    }
}
