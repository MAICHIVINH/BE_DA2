package com.example.ql_phongmay.controllers;

import com.example.ql_phongmay.dto.request.RoleRequest;
import com.example.ql_phongmay.dto.response.ApiResponse;
import com.example.ql_phongmay.dto.response.RoleResponse;
import com.example.ql_phongmay.services.RoleService;
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
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    // Lấy tất cả roles
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles() {
        List<RoleResponse> data = roleService.getAllRole();
        return ResponseEntity.ok(
                ApiResponse.<List<RoleResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get all roles successfully")
                        .data(data)
                        .build()
        );
    }

    // Lấy danh sách roles có phân trang
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<RoleResponse>>> getPagingRoles(
            @RequestParam int page,
            @RequestParam int size
    ) {
        Page<RoleResponse> data = roleService.getRolePaging(PageRequest.of(page, size));
        return ResponseEntity.ok(
                ApiResponse.<Page<RoleResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get roles by page successfully")
                        .data(data)
                        .build()
        );
    }

    // Lấy role theo id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleById(@PathVariable Integer id) {
        RoleResponse data = roleService.getRoleById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));

        return ResponseEntity.ok(
                ApiResponse.<RoleResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Get role successfully")
                        .data(data)
                        .build()
        );
    }

    // Tạo mới role
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(@RequestBody RoleRequest roleRequest) {
        RoleResponse data = roleService.createRole(roleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<RoleResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Role created successfully")
                        .data(data)
                        .build()
        );
    }

    // Cập nhật role
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole(
            @PathVariable Integer id,
            @RequestBody RoleRequest roleRequest
    ) {
        RoleResponse data = roleService.updateRole(id, roleRequest);
        return ResponseEntity.ok(
                ApiResponse.<RoleResponse>builder()
                        .status(HttpStatus.OK.value())
                        .message("Role updated successfully")
                        .data(data)
                        .build()
        );
    }

    // Xóa mềm role
    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Role soft deleted successfully")
                        .data(null)
                        .build()
        );
    }

    // Xóa cứng role
    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRoleForever(@PathVariable Integer id) {
        roleService.deleteRoleForever(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Role hard deleted successfully")
                        .data(null)
                        .build()
        );
    }
}
