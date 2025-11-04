package com.example.ql_phongmay.controllers;

import com.example.ql_phongmay.dto.request.RolePermissionRequest;
import com.example.ql_phongmay.dto.response.ApiResponse;
import com.example.ql_phongmay.dto.response.RolePermissionResponse;
import com.example.ql_phongmay.services.RolePermissionService;
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
@RequestMapping("/api/role-permission")
@RequiredArgsConstructor
public class RolePermissionController {
    private final RolePermissionService rolePermissionService;

    // Gán quyền cho vai trò
    @PostMapping("/assign")
    public ResponseEntity<ApiResponse<RolePermissionResponse>> assignPermission(
            @RequestBody RolePermissionRequest request
    ) {
        RolePermissionResponse data = rolePermissionService.assignPermission(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<RolePermissionResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Permission assigned to role successfully")
                        .data(data)
                        .build()
        );
    }

    // Lấy danh sách quyền theo vai trò
    @GetMapping("/by-role/{roleId}")
    public ResponseEntity<ApiResponse<List<RolePermissionResponse>>> getPermissionsByRole(
            @PathVariable Integer roleId
    ) {
        List<RolePermissionResponse> data = rolePermissionService.getPermissionsByRole(roleId);
        return ResponseEntity.ok(
                ApiResponse.<List<RolePermissionResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get permissions by role successfully")
                        .data(data)
                        .build()
        );
    }

    // Xóa quyền khỏi vai trò
//    @DeleteMapping("/remove")
//    public ResponseEntity<ApiResponse<Void>> removePermissionFromRole(
//            @RequestBody RolePermissionRequest request
//    ) {
//        rolePermissionService.removePermission(request);
//        return ResponseEntity.ok(
//                ApiResponse.<Void>builder()
//                        .status(HttpStatus.OK.value())
//                        .message("Permission removed from role successfully")
//                        .data(null)
//                        .build()
//        );
//    }
}
